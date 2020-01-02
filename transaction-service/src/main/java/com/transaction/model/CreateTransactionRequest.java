package com.transaction.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The Request body for Creating the transaction.")
public class CreateTransactionRequest {

  @Schema(description = "The username of the customer creating the transaction.")
  private String username;

  @Schema(description = "The password of the customer creating the transaction.")
  private String pin;

  @Schema(description = "The iban of sender.")
  private String fromIban;

  @Schema(description = "The iban of receiver.")
  private String toIban;

  @Schema(description = "The amount for transaction.", minimum = "1")
  private Double amount;

  @Schema(description = "The transaction Message.")
  private String transactionMessage;

  @Schema(description = "The reference for the transaction.")
  private String transactionReference;

  @Schema(description = "The transaction date in format of YYYY-MM-DD. Only future transactions are supported.")
  private Date transactionDate;

}
