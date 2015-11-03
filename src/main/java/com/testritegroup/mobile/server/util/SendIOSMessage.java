package com.testritegroup.mobile.server.util;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.testritegroup.mobile.server.route.model.PushMessage;

public class SendIOSMessage {
	public static void sendMsg(String regId, PushMessage message){
		
		// hardcode for test purpose, should find the cert by appId
		String certPath = "./resource/apns/iphone_dev.p12"; 
		String certPwd = "Aa123456";
		
		if(message.getAppId().equals("com.testritegroup.app.testriteItPush")){
			certPath = "./resource/apns/iphone_dev.p12";
			certPwd = "Aa123456";
		}
		
		ApnsService service =
			    APNS.newService()
			    .withCert(certPath, certPwd)
			    .withSandboxDestination()
			    .build();
		
		String payload = APNS.newPayload().alertBody(message.getComposedMessage()).build();
		
		service.push(regId, payload);
    }
}
