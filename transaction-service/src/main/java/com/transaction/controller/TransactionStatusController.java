package com.transaction.controller;

import static com.customer.portal.db.tables.CustomerTransaction.CUSTOMER_TRANSACTION;
import static net.logstash.logback.argument.StructuredArguments.kv;

import com.google.common.base.Strings;
import com.transaction.db.DbHandler;
import com.transaction.model.TransactionStatusRequest;
import com.transaction.model.TransactionStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;

@Path("/status-transaction")
@Produces({"application/json"})
@Slf4j
public class TransactionStatusController {

  @POST
  @Operation(summary = "Checks the statuses of the transactions.",
      tags = {"transaction"},
      operationId = "checkTransaction",
      description = "Returns transaction status(es).",
      responses = {
          @ApiResponse(description = "Transaction's information", content = @Content(
              schema = @Schema(implementation = TransactionStatusResponse.class)
          )),
          @ApiResponse(responseCode = "400", description = "Bad Request."),
          @ApiResponse(responseCode = "404", description = "Customer Not found.")
      })
  public Response transactionStatus(
      @RequestBody(description = "The request body for checking the transation's status(es)", required = true) TransactionStatusRequest body) {

    log.info("Check Transaction Status Request received.", kv("request", body));
    if (body == null || Strings.isNullOrEmpty(body.getIban())) {
      return Response.status(400).entity("Bad Request: Body or IBan is null").build();
    }
    List<Record> transactions = DbHandler.getInstance().dslContext().select().from(
        CUSTOMER_TRANSACTION)
        .where(CUSTOMER_TRANSACTION.CUSTOMER_FROM_IBAN.eq(body.getIban()))
        .fetch();
    if (transactions.isEmpty()) {
      return Response.status(404).entity("No transation found for provided customer's IBan.")
          .build();
    }
    List<TransactionStatusResponse> responses = new ArrayList(transactions.size());
    for (Record transaction : transactions) {
      TransactionStatusResponse response = new TransactionStatusResponse();
      response.setToIban(transaction.get(CUSTOMER_TRANSACTION.CUSTOMER_TO_IBAN));
      response.setId(transaction.get(CUSTOMER_TRANSACTION.ID));
      response.setMsg(transaction.get(CUSTOMER_TRANSACTION.TRANSACTION_MESSAGE));
      response.setTransactionStatus(transaction.get(CUSTOMER_TRANSACTION.TRANSACTION_STATUS));
      responses.add(response);
    }
    return Response.status(200).entity(responses).build();
  }

}
