package com.sachinmukharjee.basics.service.impl;

import org.springframework.stereotype.Service;

import com.sachinmukharjee.basics.service.ExternalStorage;

@Service("googleDriveStorage")
public class GoogleDriveStorage implements ExternalStorage {

	@Override
	public String storeObject(String data) {
		return "Stored Object in Google Drive Successfully";
	}

}
