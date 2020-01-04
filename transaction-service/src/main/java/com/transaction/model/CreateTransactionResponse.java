package com.transaction.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The Response body for Create Transaction Response.")
public class CreateTransactionResponse {

  @Schema(description = "The message about transaction.", required = true)
  private String msg;

  @Schema(description = "Date of creation for the transaction in yyyy-MM-dd format.", required = true)
  private String date;

}
