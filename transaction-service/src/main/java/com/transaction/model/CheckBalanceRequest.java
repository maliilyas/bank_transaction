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
@Schema(description = "The Request body for Check Balance.")
public class CheckBalanceRequest {

  @Schema(description = "The username of the customer.")
  private String username;

  @Schema(description = "The password of the customer.")
  private String pin;

  @Schema(description = "The iban of customer.")
  private String iban;
}
