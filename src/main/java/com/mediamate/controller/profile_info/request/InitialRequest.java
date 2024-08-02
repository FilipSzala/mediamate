package com.mediamate.controller.profile_info.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor


public class InitialRequest {
    private ProfileInfo profileInfo;
    private List<RealEstateRequest> realEstates;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ProfileInfo {
        private String firstName;
        private String secondName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RealEstateRequest {
        private int realEstateNo;
        private String address;
        private List<Flat> flats;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Flat {
        private int flatNo;
        private RenterRequest renterRequest;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RenterRequest {
        private String name;
        private String contactNumber;
        private int numberOfResidance;
    }
}

