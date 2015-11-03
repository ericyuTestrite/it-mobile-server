package com.testritegroup.mobile.server.route;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.db.MongoDBServices;
import com.testritegroup.mobile.server.db.PushLogDao;
import com.testritegroup.mobile.server.route.model.PushMessage;
import com.testritegroup.mobile.server.route.model.RestResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushLog{
	PushLogDao pushLogDao;
	public PushLog(){
		this.pushLogDao = MongoDBServices.getInstance().getPushLogDao();
	}
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	

	Logger logger = LoggerFactory.getLogger(PushLog.class);

    public Object handle(String body, String req[]) {
		RestResult result = new  RestResult();
    	logger.info("Received body: "+ body);
        try {
        	/**
        	 * @Todo validation the userDevices JSON
        	 */
        	if(req.length <2 || req[0] == null ||
        			req[1] == null){
        		throw new Exception("Some Data not enougth");
        	}
        	
        	List<PushMessage> messages = pushLogDao.findPushLogByUserId(
        			req[0], 
        			req[1]);
        	
        	logger.info("Got "+req[0]+" messages count  "+ messages.size());
        	
        	result.setReponseCode(200);
            result.setMessage("Success");
            result.setData(messages);
        	
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