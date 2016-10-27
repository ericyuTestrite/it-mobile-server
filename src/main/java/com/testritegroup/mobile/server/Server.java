package com.testritegroup.mobile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.route.AdAuthRoute;
import com.testritegroup.mobile.server.route.PushLog;
import com.testritegroup.mobile.server.route.PushRegister;
import com.testritegroup.mobile.server.route.SendPush;
import com.testritegroup.mobile.server.route.UploadUserImage;

@RestController
@SpringBootApplication
public class Server {
	
	Logger logger = LoggerFactory.getLogger(Server.class);

    @RequestMapping(value="/pushRegister",method=RequestMethod.POST)
    Object pushRegister(@RequestBody String body) throws Exception {
    	PushRegister pushRegister = new PushRegister();
		return pushRegister.handle(body);
    }
    
    @RequestMapping(value="/pushLog/{userId}/appId/{appId}",method=RequestMethod.GET)
    Object pushLog(@PathVariable("userId") String userId, 
    		@PathVariable("appId") String appId) throws Exception {
    	PushLog pushLog = new PushLog();
    	String req[] = new String[] {userId, appId};
    	Object obj = pushLog.handle(req);
    	return pushLog.handle(req);
    }
    
    @RequestMapping(value="/adAuth",method=RequestMethod.POST)
    Object adAuth(@RequestBody String body)  throws Exception {
    	AdAuthRoute adAuthRoute = new AdAuthRoute();
		return adAuthRoute.handle(body);
    }
    
    @RequestMapping("/sendPush")
    Object sendPush(@RequestBody String body)  throws Exception {
    	SendPush sendPush = new SendPush();
		return sendPush.handle(body);
    }
    
    @RequestMapping("/hi/{name}")
    Object hi(@PathVariable("name") String name)  throws Exception {
    	logger.info("Hello "+ name);
		return "Hello "+ name;
    }
    
    
    
    @RequestMapping("/uploadImage")
    Object uploadImage(@RequestBody String body)  throws Exception {
    	//{"userId":"TXXXX","appId":"com.testritegroup.app.testriteItPush","mimeType":"image/jpg","imageFile":"AA...."}
    	logger.info(body);
    	UploadUserImage uploadUserImage = new UploadUserImage();
		return uploadUserImage.handle(body);
    }
    
    @RequestMapping("/findUploadImage")
    Object findUploadImage(@RequestBody String body)  throws Exception {
    	//{"userId":"TXXXX","appId":"com.testritegroup.app.testriteItPush"}
    	logger.info(body);
    	UploadUserImage uploadUserImage = new UploadUserImage();

		return uploadUserImage.findUploadImage(body);
	 }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Server.class, args);
    }

}