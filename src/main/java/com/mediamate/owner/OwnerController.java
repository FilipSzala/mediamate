package com.mediamate.owner;

import com.mediamate.owner.Owner;
import com.mediamate.owner.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    OwnerService ownerService;
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("")
    public void createOwner (@RequestBody Owner owner){
        ownerService.addOwner(owner);
    }

    @GetMapping("")
    public List<Owner> displayOwners (){
        return ownerService.displayOwner();
    }

}
