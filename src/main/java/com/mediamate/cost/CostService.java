package com.mediamate.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CostService {
    @Autowired
    CostRepository costRepository;

    public void createPrice(Cost cost) {
        costRepository.save(cost);
    }
        public Cost getPriceInCurrentMonth(List<Cost> costs){
           Cost costInCurrentMonth = costs.stream()
                    .filter(price -> isSameYearAndMonth(price.getCreatedDay(),LocalDate.now()))
                    .findFirst().orElseThrow();
           return costInCurrentMonth;
        }

        private boolean isSameYearAndMonth(LocalDate dateInDB, LocalDate currentDate) {
        return dateInDB.getYear() == currentDate.getYear() && dateInDB.getMonth() == currentDate.getMonth();
    }
        public Optional <Cost> getCostByRealEstateIdAndDate(Long realEstateId, LocalDate date){
        int year = date.getYear();
        int month = date.getMonthValue();
            return costRepository.findCostByRealEstateIdAndCreationDate(realEstateId,year,month);
        }
    }

