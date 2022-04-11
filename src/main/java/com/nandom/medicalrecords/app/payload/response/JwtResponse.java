package com.nandom.medicalrecords.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
  private Long id;
  private String username;
  private UUID loggedInstaffUuid;
  private String email;
  private List<String> roles;
  private String accessToken;
  private String type = "Bearer";
}
