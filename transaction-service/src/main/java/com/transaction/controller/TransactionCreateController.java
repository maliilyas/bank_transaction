package com.transaction.controller;

import static com.google.common.base.Strings.isNullOrEmpty;
import static net.logstash.logback.argument.StructuredArguments.kv;

import com.transaction.db.DbUtil;
import com.transaction.db.TransactionManager;
import com.transaction.model.CreateTransactionRequest;
import com.transaction.model.CreateTransactionResponse;
import com.transaction.service.ValidationTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the transaction handling
 */
@Path("/create-transaction")
@Produces({"application/json"})
@Slf4j
public class TransactionCreateController {

  private final ValidationTransactionService validationTransactionService;

  @Inject
  public TransactionCreateController(final ValidationTransactionService validationTransactionService) {
    this.validationTransactionService = validationTransactionService;
  }

  @POST
  @Operation(summary = "Creates the Transaction. Only now and future Transactions are allowed. For simplicity, transactions are not scheduled.",
      tags = {"transaction"},
      operationId = "createTransaction",
      description = "Returns transaction creation message and time.",
      responses = {
          @ApiResponse(description = "Transaction's information", content = @Content(
              schema = @Schema(implementation = CreateTransactionResponse.class)
          )),
          @ApiResponse(responseCode = "400", description = "Bad Request."),
          @ApiResponse(responseCode = "404", description = "Customer Not found or wrong credential(s).")
      })
  public Response createTransaction(
      @RequestBody(description = "The request body for checking the balance", required = true) CreateTransactionRequest body) {
    log.info("Request for create transaction.", kv("request", body));

    final String isValidOrError = isValidRequestBody(body);
    if (!"true".equals(isValidOrError)) {
      return generateNon201Response("Bad Request: " + isValidOrError, 400);
    }
    if (!validationTransactionService
        .isValidCustomer(body.getUsername(), body.getPin(), body.getFromIban())) {
      return generateNon201Response("Customer not found or wrong credential(s).", 404);
    }

    if (!validationTransactionService.isValidDate(body.getTransactionDate())) {
      return generateNon201Response(
          "Transaction Date is wrong, now and future transactions are supported.", 200);
    }

    long transactionId = DbUtil.createTransaction(body);
    ExecutorService executorService = Executors.newCachedThreadPool();
    try {
      executorService.submit(() -> {
        TransactionManager.completeTransaction(body, transactionId);
      });

    } catch (Exception e) {
      // since the reponse has already been sent, no recovery is done.
      log.error("Error occurred during the transaction.", e);
    } finally {
      executorService.shutdown();
    }
    return generate201Response();
  }

  Response generate201Response() {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    CreateTransactionResponse transactionResponse = new CreateTransactionResponse();
    transactionResponse.setDate(simpleDateFormat.format(new Date()));
    transactionResponse.setMsg("Transaction has been created.");
    return Response.status(201)
        .entity(transactionResponse).build();
  }

  Response generateNon201Response(final String msg, final int httpCode) {
    return Response
        .status(httpCode)
        .entity(msg).build();
  }

  String isValidRequestBody(final CreateTransactionRequest requestBody) {
    if (requestBody == null) {
      return "Request Body is empty.";
    }

    if (isNullOrEmpty(requestBody.getUsername())) {
      return "Username can not be null or empty.";
    }

    if (isNullOrEmpty(requestBody.getPin())) {
      return "Password can not be null or empty.";
    }

    if (isNullOrEmpty(requestBody.getToIban()) || isNullOrEmpty(requestBody.getFromIban())) {
      return "Iban can not be null or empty.";
    }

    if (! validationTransactionService.isValidToIban(requestBody.getToIban()) ) {
      return "Not a valid German Receiver Iban.";
    }

    if (! validationTransactionService.isValidToIban(requestBody.getFromIban()) ) {
      return "Not a valid German Sender Iban.";
    }

    if (requestBody.getFromIban().equalsIgnoreCase(requestBody.getToIban())) {
      return "self transaction is not allowed.";
    }

    if (requestBody.getAmount() == null) {
      return "Amount of transaction can not be null.";
    }

    if (requestBody.getTransactionDate() == null) {
      return "Transaction Date can not be null.";
    }
    return "true";
  }
}
