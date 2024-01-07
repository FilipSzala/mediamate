package com.mediamate.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    }

