
package com.mediamate.flat;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flat")
public class FlatController {
    FlatService flatService;

    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    @PostMapping("/{id}")
    public void addFlat (@PathVariable ("id") Long realEstateId, @RequestBody Flat flat){
        flatService.createFlat(realEstateId,flat);
    }

    @GetMapping ("")
    public List<Flat> displayFlats (){
        return flatService.findFlats();
    }


}

