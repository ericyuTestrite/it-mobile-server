package com.testritegroup.mobile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testritegroup.mobile.server.route.AdAuthRoute;
import com.testritegroup.mobile.server.route.PushLog;
import com.testritegroup.mobile.server.route.PushRegister;
import com.testritegroup.mobile.server.route.SendPush;

@RestController
@EnableAutoConfiguration
public class Server {
	
	Logger logger = LoggerFactory.getLogger(Server.class);

	private ObjectMapper jsonMapper = new ObjectMapper();

    @RequestMapping(value="/pushRegister",method=RequestMethod.POST)
    String pushRegister(@RequestBody String body) throws Exception {
    	PushRegister pushRegister = new PushRegister();
    	Object obj = pushRegister.handle(body);
		return jsonMapper.writeValueAsString(obj);
    }
    
    @RequestMapping(value="/pushLog/{userId}/appId/{appId}",method=RequestMethod.POST)
    String pushLog(String userId, String appId,@RequestBody String body) throws Exception {
    	PushLog pushLog = new PushLog();
    	String req[] = new String[] {userId, appId};
    	Object obj = pushLog.handle(body, req);
    	return jsonMapper.writeValueAsString(obj);
    }
    
    @RequestMapping(value="/adAuth",method=RequestMethod.POST)
    String adAuth(@RequestBody String body)  throws Exception {
    	AdAuthRoute adAuthRoute = new AdAuthRoute();
    	Object obj = adAuthRoute.handle(body);
		return jsonMapper.writeValueAsString(obj);
    }
    
    @RequestMapping("/sendPush")
    String sendPush(@RequestBody String body)  throws Exception {
    	SendPush sendPush = new SendPush();
    	Object obj = sendPush.handle(body);
		return jsonMapper.writeValueAsString(obj);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Server.class, args);
    }

}