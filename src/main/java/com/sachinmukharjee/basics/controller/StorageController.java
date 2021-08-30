package com.sachinmukharjee.basics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachinmukharjee.basics.service.ExternalStorage;
/*
 * Usage of Qualifier Annoation
 */
@RestController
@RequestMapping("/store")
public class StorageController {
	
	@Autowired
	@Qualifier("dropBoxStorage")
	ExternalStorage externalStorage;
	
	public String storeData() {
		String data = "Excel.xls";
		return externalStorage.storeObject(data);
	}
}
