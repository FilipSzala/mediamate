package com.mediamate.flat;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Renter {
    protected String rentersFullName;
    protected int renterCount;
    protected String phoneNumber;

    public Renter(String rentersFullName, int renterCount, String phoneNumber) {
        this.rentersFullName = rentersFullName;
        this.renterCount = renterCount;
        this.phoneNumber = phoneNumber;
    }

    public Renter() {
    }
}
