package com.mediamate.initialSetup.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RenterRequest {
    Long flatId;
    String rentersFullName;
    int renterCount;
    String phoneNumber;
}
