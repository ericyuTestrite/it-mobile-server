package com.testritegroup.mobile.server.util;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.testritegroup.mobile.server.Config;
import com.testritegroup.mobile.server.route.model.PushMessage;

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
}
