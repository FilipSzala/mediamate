package com.mediamate.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoRequestList {
    private List<PhotoInformationRequest> photoInformationRequests;
}