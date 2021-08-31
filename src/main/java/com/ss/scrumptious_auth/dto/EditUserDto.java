  
package com.ss.scrumptious_auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserDto {
    // may add more fields later but this works for now

  @NotNull
  @NotBlank(message = "Email cannot be blank.")
  @Email(message = "Email must be valid.")
  private String email;

  @ToString.Exclude
  @NotNull
  @NotBlank(message = "Password cannot be blank.")
  private String password;

}
