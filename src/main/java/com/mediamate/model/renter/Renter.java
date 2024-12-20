package com.mediamate.model.renter;

import com.mediamate.model.flat.Flat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity

public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rentersFullName;
    private int renterCount;
    private String phoneNumber;
    private LocalDate smsSentDate;
    @OneToOne
    @JoinColumn (
            name = "flat_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "renter_flat_fk"
            )
    )
    private Flat flat;
    public Renter() {
    }
    public Renter(String rentersFullName, int renterCount, String phoneNumber,LocalDate smsSentDate) {
        this.rentersFullName = rentersFullName;
        this.renterCount = renterCount;
        this.phoneNumber = phoneNumber;
        this.smsSentDate = smsSentDate;
    }


    public Long getId() {
        return id;
    }
    public String getRentersFullName() {
        return rentersFullName;
    }

    public void setRentersFullName(String rentersFullName) {
        this.rentersFullName = rentersFullName;
    }

    public int getRenterCount() {
        return renterCount;
    }

    public void setRenterCount(int renterCount) {
        this.renterCount = renterCount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public LocalDate getSmsSentDate() {
        return smsSentDate;
    }

    public void setSmsSentDate(LocalDate smsSentDate) {
        this.smsSentDate = smsSentDate;
    }
}
