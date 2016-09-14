package com.testritegroup.mobile.server.util;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.testritegroup.mobile.server.Config;
import com.testritegroup.mobile.server.route.model.PushMessage;

import ch.qos.logback.classic.Logger;

public class SendIOSMessage {
	static Config config = Config.getInstance();
	public static void sendMsg(String regId, PushMessage message){
		
		// hardcode for test purpose, should find the cert by appId
		String certPath = null; 
		String certPwd = null;
		String appId = message.getAppId();
		if(message.getAppId().equals(appId)){
			certPath = config.getProp(appId+".iosP12file");
			certPwd = config.getProp(appId+".iosP12Paswoord");
		}
		
		ApnsService service =
			    APNS.newService()
			    .withCert(certPath, certPwd)
			    .withSandboxDestination()
			    .build();
		
		String payload = APNS.newPayload().alertBody(message.getComposedMessage()).build();
		
		service.push(regId, payload);
    }
	
   public static void main(String args[]){
		
		// hardcode for test purpose, should find the cert by appId
		String certPath = null; 
		String certPwd = null;
		String appId = "com.curamobi.cuwalk2";
			certPath = "/Users/ericyu/Desktop/apns-prod.p12";
			certPwd = "Cur@mob1";
		
		ApnsService service =
			    APNS.newService()
			    .withCert(certPath, certPwd).withAppleDestination(true)
			    
			    .build();
		
		String payload = APNS.newPayload().alertBody("Hi").build();
		ApnsNotification notify = service.push("e45b0cbc7e1797e90f2584d2e451a03914acd1467d0bbfd1500038ac3a377f38", payload);
		System.out.println(notify.toString());
    }
	
	
}
