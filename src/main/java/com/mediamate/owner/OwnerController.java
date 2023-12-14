package com.mediamate.owner;

import com.mediamate.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    OwnerService ownerService;
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    @GetMapping()
    public List<Owner> displayOwners (){
        return ownerService.displayOwner();
    }

    @PatchMapping("settings")
    public void settings (@RequestBody User user) {
    }
}
