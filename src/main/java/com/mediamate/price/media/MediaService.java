package com.mediamate.price.media;

import com.mediamate.price.Price;
import com.mediamate.price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    MediaRepository mediaRepository;

    PriceService priceService;
    @Autowired
    public MediaService(PriceService priceService) {
        this.priceService = priceService;
    }

    public void createMedia(Media media){
        Price price = new Price(media);
        priceService.createPrice(price);
    }
}
