package com.testritegroup.mobile.server.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.Config;
import com.testritegroup.mobile.server.auth.ADAuth;
import com.testritegroup.mobile.server.auth.AuthPassword;
import com.testritegroup.mobile.server.route.model.RestResult;
import com.testritegroup.mobile.server.route.model.UserAdProfile;

public class AdAuthRoute{
	Logger logger = LoggerFactory.getLogger(AdAuthRoute.class);

	private ObjectMapper jsonMapper = new ObjectMapper();

	
	public RestResult handle(String body) {
		ADAuth authentication = new ADAuth();
		UserAdProfile profile = null;
		RestResult result = new RestResult();
	    try {
	    	AuthPassword authPassword = jsonMapper.readValue(body, AuthPassword.class);
	    	Config config= Config.getInstance();
	    	profile = authentication.authenticate(authPassword, config.getAdServer(), config.getAdDomain());
	    	
	    	
	    	if(profile!=null){
	    		result.setMessage("success");
	    		result.setReponseCode(200);
	    		result.setData(profile);
	    	}else{
	    		result.setMessage("Authentication Failed or can't find the user ");
	    		result.setReponseCode(403);
	    	}
	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    }
		return result;
	}
	
	

}
