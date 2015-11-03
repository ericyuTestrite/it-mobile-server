package com.testritegroup.mobile.server.route;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.db.MongoDBServices;
import com.testritegroup.mobile.server.db.PushLogDao;
import com.testritegroup.mobile.server.db.UserDevicesDao;
import com.testritegroup.mobile.server.route.model.PushMessage;
import com.testritegroup.mobile.server.route.model.RestResult;
import com.testritegroup.mobile.server.route.model.UserDevices;
import com.testritegroup.mobile.server.util.SendAndroidMessage;
import com.testritegroup.mobile.server.util.SendIOSMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPush{
	UserDevicesDao userDevicesDao;
	PushLogDao pushLogDao;
	public SendPush(){
		this.userDevicesDao = MongoDBServices.getInstance().getUserDevicesDao();
		this.pushLogDao = MongoDBServices.getInstance().getPushLogDao();
	}
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	

	Logger logger = LoggerFactory.getLogger(SendPush.class);

	public Object handle(String body) {
		RestResult result = new  RestResult();
    	logger.info("Received body: "+ body);
        try {
        	PushMessage pushMessage = jsonMapper.readValue(body, PushMessage.class);
        	
        	/**
        	 * @Todo validation the userDevices JSON
        	 */
        	if(pushMessage.getAppId() == null || 
        			pushMessage.getMessage() == null|| 
        			pushMessage.getReceiverUserId() == null){
        		throw new Exception("Some Data not enougth");
        	}
        	
        	List<UserDevices> devices = userDevicesDao.findUserDevicesByUserId(pushMessage.getReceiverUserId()
        			, pushMessage.getAppId());
        	
        	logger.info("Sending message to "+pushMessage.getReceiverUserId()+" which have "+ devices.size() + " devices!");
        	
        	for(UserDevices device:devices){
        		if(device.getDevicePlatform().toLowerCase().equals("ios")){
        			logger.info("Sending message "+pushMessage.getComposedMessage()+" to "+pushMessage.getReceiverUserId()+" iOS");
        			SendIOSMessage.sendMsg(device.getRegId(), pushMessage);
        		}else if(device.getDevicePlatform().toLowerCase().equals("android")){
        			logger.info("Sending message to "+pushMessage.getReceiverUserId()+" android");
        			SendAndroidMessage.sendMsg(device.getRegId(), pushMessage);
        		}
        	}
        	logger.info("Sending messages completed!");
        	result.setReponseCode(200);
            result.setMessage("Success");
            pushLogDao.insertLog(pushMessage);
        	
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.setReponseCode(500);
            result.setMessage(e.getMessage());
        }
        
        String jsonResult = "";
        try{
        	jsonResult = jsonMapper.writeValueAsString(result);
        }catch(Exception e){
        	logger.error(e.getMessage(), e);
        }
        
        return jsonResult;
    }

}