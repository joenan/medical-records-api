package com.nandom.medicalrecords.app.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto {
  public int code;
  public String message;
  public Object data;

  public ApiResponseDto() {

  }
  public ApiResponseDto(String message) {
    this.message = message;
  }

}
