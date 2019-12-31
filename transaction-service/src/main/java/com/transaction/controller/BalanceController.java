package com.transaction.controller;

import static com.customer.portal.db.tables.CustomerAccount.CUSTOMER_ACCOUNT;
import static net.logstash.logback.argument.StructuredArguments.kv;

import com.google.common.base.Strings;
import com.transaction.db.DbHandler;
import com.transaction.model.CheckBalanceRequest;

import com.transaction.model.CheckBalanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.jooq.Record;

@Path("/checkbalance")
@Produces({"application/json"})
@Slf4j
public class BalanceController {

  @POST
  @Operation(summary = "Check the balance of a customer.",
      tags = {"balance"},
      description = "Returns an information of customer's balance if customer is found.",
      responses = {
          @ApiResponse(description = "Customer's balance information", content = @Content(
              schema = @Schema(implementation = CheckBalanceResponse.class)
          )),
          @ApiResponse(responseCode = "400", description = "Bad Request."),
          @ApiResponse(responseCode = "404", description = "Customer Not found or wrong credential(s).")
      })
  public Response checkbalance(
      @RequestBody(description = "The request body for checking the balance", required = true) CheckBalanceRequest body) {

    log.info("Request for check balance.", kv("request", body));
    if (!isValidRequestBody(body)) {
      return generateErrorResponse("Bad Request.", 400);
    }

    List<Record> records = DbHandler.getInstance().dbContext().select().from(CUSTOMER_ACCOUNT)
        .where(
            CUSTOMER_ACCOUNT.USER_NAME.eq(body.getUsername())
                .and(CUSTOMER_ACCOUNT.PASSWORD.eq(body.getPin()))
                .and(CUSTOMER_ACCOUNT.IBAN.eq(body.getIban()))).fetch();
    if (records.isEmpty()) {
      return generateErrorResponse("Customer credential(s) wrong or customer not found.", 404);
    }

    Record balanceRecord = records.get(0);
    Response response = generateOkResponse(balanceRecord);
    log.info("Response from Check balance.", kv("response", response));
    return response;

  }


  /**
   * Generating the Ok response.
   */
  Response generateOkResponse(final Record record) {
    CheckBalanceResponse checkBalanceResponse = new CheckBalanceResponse();
    checkBalanceResponse.setBalance(record.getValue(CUSTOMER_ACCOUNT.BALANCE));
    checkBalanceResponse.setUsername(record.getValue(CUSTOMER_ACCOUNT.USER_NAME));
    checkBalanceResponse.setCurrency_type(record.getValue(CUSTOMER_ACCOUNT.CURRENCY_TYPE));
    return Response.status(200)
        .entity(checkBalanceResponse).build();

  }

  Response generateErrorResponse(final String msg, final int httpCode) {
    return Response
        .status(httpCode)
        .entity(msg).build();
  }

  boolean isValidRequestBody(final CheckBalanceRequest requestBody) {
    if (requestBody == null) {
      return false;
    }

    if (Strings.isNullOrEmpty(requestBody.getUsername())) {
      return false;
    }

    if (Strings.isNullOrEmpty(requestBody.getPin())) {
      return false;
    }

    if (Strings.isNullOrEmpty(requestBody.getIban())) {
        return false;
    }

    if (! new IBANCheckDigit().isValid(requestBody.getIban())) {
      return false;
    }

    if (requestBody.getCustomerId() == null) {
      return false;
    }
    return true;
  }
}


