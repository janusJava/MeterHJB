package com.seafta.meterhj.domain.request.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
