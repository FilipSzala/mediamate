package com.mediamate.controller.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerRegistrationRequest {
    String email;
    String password;
}
