package com.transaction.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The Response body for Transaction Status Response.")
public class TransactionStatusResponse {

  @Schema(description = "The transaction id.", required = true)
  private Long id;

  @Schema(description = "The message explaining the transaction.", required = true)
  private String msg;

  @Schema(description = "The iban of sender.", required = true)
  private String toIban;

  @Schema(description = "The status of the transaction.", required = true)
  private String transactionStatus;
}
