package com.testritegroup.mobile.server.route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.db.MongoDBServices;
import com.testritegroup.mobile.server.db.UserDevicesDao;
import com.testritegroup.mobile.server.route.model.UserDevices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushRegister{
	UserDevicesDao userDevicesDao; 
	public PushRegister(){
		this.userDevicesDao = MongoDBServices.getInstance().getUserDevicesDao();
	}
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	

	Logger logger = LoggerFactory.getLogger(PushRegister.class);

    public Object handle(String body) {
    	logger.info("Received body: "+ body);
        try {
        	UserDevices userDevices = jsonMapper.readValue(body, UserDevices.class);
        	jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        	
        	/**
        	 * @Todo validation the userDevices JSON
        	 */
        	if(userDevices.getAppId() == null || 
        			userDevices.getRegId() == null|| 
        			userDevices.getDevicePlatform() == null){
        		throw new Exception("Registration Failed...data not well prepared");
        	}
        	        	
        	userDevicesDao.insertUserDevice(userDevices);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return body;
    }

}