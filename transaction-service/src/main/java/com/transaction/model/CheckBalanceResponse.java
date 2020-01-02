package com.transaction.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The Response body for Check Balance.")
public class CheckBalanceResponse {

  @Schema(description = "The username of the customer.")
  private  String username;

  @Schema(description = "The remaining balance in the account of customer.")
  private  Double balance;

  @Schema(description = "The currency type ISO-4217 in three or less alphabets.")
  private  String currency_type;

}
