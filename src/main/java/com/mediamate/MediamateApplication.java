package com.mediamate;

import com.mediamate.model.Flat;
import com.mediamate.model.MeterValue;
import com.mediamate.model.RealEstate;
import com.mediamate.repository.RealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;


@SpringBootApplication
public class MediamateApplication {


	public static void main(String[] args) {
		SpringApplication.run(MediamateApplication.class, args);
	}


}
