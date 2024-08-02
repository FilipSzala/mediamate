package com.mediamate.controller.register;

import com.mediamate.model.renter.Renter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationRequest {
    String name;
    String password;
    Long renterId;
}
