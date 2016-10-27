package com.testritegroup.mobile.server.route;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.db.MongoDBServices;
import com.testritegroup.mobile.server.db.UserImageDao;
import com.testritegroup.mobile.server.route.model.RestResult;
import com.testritegroup.mobile.server.route.model.UserImage;

public class UploadUserImage{
	UserImageDao userImageDao; 
	public UploadUserImage(){
		this.userImageDao = MongoDBServices.getInstance().getUserImageDao();
	}
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	

	Logger logger = LoggerFactory.getLogger(UploadUserImage.class);

    public RestResult handle(String body) {
    	logger.info("Received body: "+ body);
    
    	RestResult result = new RestResult();
 
        try {
        	UserImage userImage = jsonMapper.readValue(body, UserImage.class);
      
        	if(userImage.getAppId() == null ||  userImage.getUserId()== null|| 
        			userImage.getImageFile()== null|| userImage.getMimeType() == null){
        		throw new Exception("UserImage Failed...data not well prepared");
        	}
        	        	
        	boolean blnRet=userImageDao.insertUserDevice(userImage);
        
        	if(blnRet){
	    		result.setMessage("success");
	    		result.setReponseCode(200);
	    		//result.setData(profile);
	    	}else{
	    		result.setMessage("fail insertUser");
	    		result.setReponseCode(403);
	    	}
        	
        	
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.setMessage("fail "+e.getMessage());
    		result.setReponseCode(403);
        }
        return result;
    }

    
    
    public RestResult findUploadImage(String body) {
    	logger.info("Received body: "+ body);
    
    	RestResult result = new RestResult();
 
        try {
        	UserImage userImage = jsonMapper.readValue(body, UserImage.class);
        	String userId=userImage.getUserId();
        	String appId=userImage.getAppId();
        	if(userImage.getAppId() == null ||  userImage.getUserId()== null){
        		throw new Exception("UserImage Failed...data not well prepared");
        	}
        	
        	
        	 List<UserImage> list =userImageDao.findUserImageByUserIdAndAppId( userId,  appId);
        	 boolean blnRet=false;
        	 if (list!=null && list.size()>0){
        		 blnRet=true;
        	 }
        
        	if(blnRet){
        		 UserImage userImage0=list.get(0); 
	    		result.setMessage("success");
	    		result.setReponseCode(200);
	    		result.setData(userImage0);
	    	}else{
	    		result.setMessage("fail 找不到資料");
	    		result.setReponseCode(403);
	    	}
        	
        	
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.setMessage("fail "+e.getMessage());
    		result.setReponseCode(403);
        }
        return result;
    }
    
    
    
    
}