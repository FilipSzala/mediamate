package com.mediamate.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    PriceRepository priceRepository;

    public void createPrice(Price price){
        priceRepository.save(price);
    }
}
