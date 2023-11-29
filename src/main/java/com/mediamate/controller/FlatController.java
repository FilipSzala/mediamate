package com.mediamate.controller;

import com.mediamate.model.Flat;
import com.mediamate.service.FlatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flat")
public class FlatController {
    FlatService flatService;

    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    @PostMapping("/{id}")
    public void addFlat (@PathVariable ("id") Long realEstateId, @RequestBody Flat flat){
        flatService.addFlat(realEstateId,flat);
    }


}
