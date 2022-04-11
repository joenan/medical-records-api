package com.nandom.medicalrecords.app.payload.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;


@Data
public class SignupRequestDto {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotNull
  @Size(max = 50)
  @Email(message = "must be a valid well formed email address")
  private String email;

  private Set<String> role;

  @NotNull
  @Size(min = 3, max = 40)
  private String password;


}
