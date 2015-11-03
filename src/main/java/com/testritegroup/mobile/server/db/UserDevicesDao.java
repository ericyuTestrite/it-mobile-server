package com.testritegroup.mobile.server.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.testritegroup.mobile.server.route.model.UserDevices;

public class UserDevicesDao {
	private final MongoCollection<Document> userDevicesCollection;

	public UserDevicesDao(final MongoDatabase blogDatabase) {
		userDevicesCollection = blogDatabase.getCollection("userDevices");
	}

	public boolean insertUserDevice(UserDevices userDevices) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Document doc = new Document("_id", userDevices.getUuid())
				.append("userId", userDevices.getUserId())
				.append("appId", userDevices.getAppId())
				.append("uuid", userDevices.getUuid())
				.append("devicePlatform", userDevices.getDevicePlatform())
				.append("regId", userDevices.getRegId())
				.append("deviceModel", userDevices.getDeviceModel())
				.append("osVersion", userDevices.getOsVersion())
				.append("browserCodeName", userDevices.getBrowserCodeName())
				.append("browserVersion", userDevices.getBrowserVersion())
				.append("browserName", userDevices.getBrowserName())
				.append("browserLanguage", userDevices.getBrowserLanguage())
				.append("browserUserAgent", userDevices.getBrowserUserAgent())
				.append("createDate", sdf.format(new Date()));
		
		UpdateOptions updateOptions= new UpdateOptions();
		updateOptions.upsert(true);
		userDevicesCollection.updateOne(
			    new BasicDBObject("_id", userDevices.getUuid()), 
			    new BasicDBObject("$set", doc), updateOptions);
		return true;
	}
	
	public List<UserDevices> findUserDevicesByUserId(String userId, String appId){
		
		Document queryFilter = new Document("userId", userId).append("appId", appId);
		
		List<Document> userDevicesDocs = userDevicesCollection.find(queryFilter)
				.into(new ArrayList<Document>());
		List<UserDevices> result = new ArrayList<UserDevices>();
		for(Document userdeviceDoc:userDevicesDocs){
			UserDevices device = new UserDevices();
			device.setAppId(userdeviceDoc.getString("appId"));
			device.setDevicePlatform(userdeviceDoc.getString("devicePlatform"));
			device.setRegId(userdeviceDoc.getString("regId"));
			device.setUuid(userdeviceDoc.getString("uuid"));
			device.setDeviceModel(userdeviceDoc.getString("deviceModel"));
			device.setOsVersion(userdeviceDoc.getString("osVersion"));
			result.add(device);
		}
		return result;
	}

}
