package com.testritegroup.mobile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import static java.util.Collections.singletonList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.testritegroup.linebot.BotProxy;
import com.testritegroup.mobile.server.route.AdAuthRoute;
import com.testritegroup.mobile.server.route.PushLog;
import com.testritegroup.mobile.server.route.PushRegister;
import com.testritegroup.mobile.server.route.SendPush;
import com.testritegroup.mobile.server.route.UploadUserImage;

@RestController
@SpringBootApplication
@LineMessageHandler
public class Server {
	@Autowired
    private LineMessagingClient lineMessagingClient;
	
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
    
    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        logger.info("event: " + event);
        final BotApiResponse apiResponse = lineMessagingClient
                .replyMessage(new ReplyMessage(event.getReplyToken(),
                                               singletonList(new TextMessage(event.getMessage().getText()))))
                .get();
        logger.info("Sent messages: " + apiResponse);
    }

    @EventMapping
    public void defaultMessageEvent(Event event) {
    	logger.info("event: " + event);
    }
    
    
    Object hi(@RequestBody String body)  throws Exception {
		return BotProxy.getInstance().receive(body);
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