package com.mediamate.service;

import com.mediamate.model.Owner;
import com.mediamate.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

    public List<Owner> displayOwner() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById (Long ownerId){
        return ownerRepository.findById(ownerId);
    }
    public Optional<Owner> findByName (String name){
        return ownerRepository.findByName(name);
    }
}
