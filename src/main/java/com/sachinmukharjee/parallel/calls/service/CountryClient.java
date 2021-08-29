package com.sachinmukharjee.parallel.calls.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sachinmukharjee.parallel.calls.model.Country;

@Service
public class CountryClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryClient.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	/*
	 * Sync Calls
	 */
	public List<Country> getCountriesByLanguageSync(String language){
		String url = "https://restcountries.eu/rest/v2/lang/"+language+"?fields=name";
		Country[] response = null;
		try {
			response = restTemplate.getForObject(url, Country[].class);
			LOGGER.info("Successfully called countries api for language {}",language);
		}catch(HttpClientErrorException e) {
			LOGGER.error("Error calling countries api for language {}",language);
			e.printStackTrace();
		}
		return Arrays.asList(response);
	}
	
	public List<Country> getCountriesByRegionSync(String region){
		String url = "https://restcountries.eu/rest/v2/region/" +region+ "?fields=name";
		Country[] response = null;
		try {
			response = restTemplate.getForObject(url, Country[].class);
			LOGGER.info("Sucessfully called countries api for region {}",region);
		}catch(HttpClientErrorException e) {
			LOGGER.error("Error calling countries api for region {}",region);
			e.printStackTrace();
		}
		return Arrays.asList(response);
	}
	
	/*
	 * Async Calls
	 */
	@Async
	public CompletableFuture<List<Country>> getCountriesByLanguageASync(String language){
		String url = "https://restcountries.eu/rest/v2/lang/"+language+"?fields=name";
		Country[] response = null;
		try {
			response = restTemplate.getForObject(url, Country[].class);
			LOGGER.info("Successfully called countries api for language {}",language);
		}catch(HttpClientErrorException e) {
			LOGGER.error("Error calling countries api for language {}",language);
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(Arrays.asList(response));
	}
	
	@Async
	public CompletableFuture<List<Country>> getCountriesByRegionASync(String region){
		String url = "https://restcountries.eu/rest/v2/region/" +region+ "?fields=name";
		Country[] response = null;
		try {
			response = restTemplate.getForObject(url, Country[].class);
			LOGGER.info("Sucessfully called countries api for region {}",region);
		}catch(HttpClientErrorException e) {
			LOGGER.error("Error calling countries api for region {}",region);
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(Arrays.asList(response));
	}
	
	
}	
