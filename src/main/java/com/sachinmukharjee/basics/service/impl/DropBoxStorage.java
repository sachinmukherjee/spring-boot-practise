package com.sachinmukharjee.basics.service.impl;

import org.springframework.stereotype.Service;

import com.sachinmukharjee.basics.service.ExternalStorage;

@Service("dropBoxStorage")
public class DropBoxStorage implements ExternalStorage {

	@Override
	public String storeObject(String data) {
		return "Data Stored in DropBox Successfully";
	}

}
