package com.transaction.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty(required = true)
  private  String username;

  @JsonProperty(required = true)
  private  String pin;

  @JsonProperty(required = true)
  private  String iban;

  @JsonProperty(required = true)
  private  Integer customerId;
}
