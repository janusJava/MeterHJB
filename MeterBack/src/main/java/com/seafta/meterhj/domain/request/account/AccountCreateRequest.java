package com.seafta.meterhj.domain.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountCreateRequest {

    @Email
    @NotBlank
    @Schema(example = "test@gmail.com")
    private String email;

    @NotBlank
    @Schema(example = "password")
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    private String fullName;

}
