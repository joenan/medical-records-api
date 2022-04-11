package com.nandom.medicalrecords.app.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private Object message;
  private String description;

  public ErrorMessage(int statusCode, Date timestamp, Object message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

}