package com.mediamate.service;

import com.mediamate.model.Owner;
import com.mediamate.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;

    public void addOwner (Owner owner){
        ownerRepository.save(owner);
    }
}
