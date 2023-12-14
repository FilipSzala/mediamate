package com.mediamate.owner;

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

    public List<Owner> displayOwner() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById (Long ownerId){
        return ownerRepository.findById(ownerId);
    }
}
