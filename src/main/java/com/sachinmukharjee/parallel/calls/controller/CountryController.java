package com.sachinmukharjee.parallel.calls.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sachinmukharjee.parallel.calls.model.Country;
import com.sachinmukharjee.parallel.calls.service.CountryClient;

@RestController
@RequestMapping("/api")
public class CountryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
	
	@Autowired
	CountryClient countryClient;
	
	/*
	 * Serial Calls to two API's
	 */
	@GetMapping("/series/country")
	public List<String> getAllRegionBySpecificLanguageSync(@RequestParam(name = "region", required = true) String region, @RequestParam(name="language", required=true) String language){
		long startTime = System.currentTimeMillis();
		
		LOGGER.info("Got Request for region {} and language {}",region,language);
		
		List<Country> getCountryByLanguage = countryClient.getCountriesByLanguageSync(language);
		List<Country> getCountryByRegion = countryClient.getCountriesByRegionSync(region);
		
		List<String> countriesByLanguage = new ArrayList<>(getCountryByLanguage.stream().map(Country::getName).collect(Collectors.toList()));
		countriesByLanguage.retainAll(getCountryByRegion.stream().map(Country::getName).collect(Collectors.toList()));
		
		long endTime = System.currentTimeMillis();
		
		long duration = (endTime - startTime);
		
		LOGGER.info("Time take to execute {} ms", duration);
		return countriesByLanguage;
	}
	
	/*
	 * Parallel Calls
	 */
	@GetMapping("/parallel/country")
	public List<String> getAllRegionBySpecificLanguageASync(@RequestParam(name = "region", required = true) String region, @RequestParam(name="language", required=true) String language){
		long startTime = System.currentTimeMillis();
		
		LOGGER.info("Got Request for region {} and language {}",region,language);
		
		CompletableFuture<List<Country>> getCountryByLanguage = countryClient.getCountriesByLanguageASync(language);
		CompletableFuture<List<Country>> getCountryByRegion = countryClient.getCountriesByRegionASync(region);
		List<String> countriesByLanguage=null;
		try {
			countriesByLanguage = new ArrayList<>(getCountryByLanguage.get().stream().map(Country::getName).collect(Collectors.toList()));
			countriesByLanguage.retainAll(getCountryByRegion.get().stream().map(Country::getName).collect(Collectors.toList()));
		}catch(Throwable e) {
			e.getCause();
		}
		
		long endTime = System.currentTimeMillis();
		
		long duration = (endTime - startTime);
		
		LOGGER.info("Time take to execute {} ms", duration);
		return countriesByLanguage;
	}
	
	
}
