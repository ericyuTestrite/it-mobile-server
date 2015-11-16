package com.testritegroup.mobile.server.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.testritegroup.mobile.server.Config;
import com.testritegroup.mobile.server.route.model.PushMessage;

public class SendAndroidMessage {
	static Logger logger = LoggerFactory.getLogger(SendAndroidMessage.class);

	private static Sender sender;
	private static List<String> androidTargets = new ArrayList<String>();

	public SendAndroidMessage() {
	}


	public static void sendMsg(String regId, PushMessage message) {
		
		if(sender == null){
			//hard coded for test only, this is a test sender for ericyu76@gmail.com
			sender = new Sender(Config.getInstance().getProp(message.getAppId()+".androidSenderKey")); 
		}
		
		Message messageBuilder = new Message.Builder()

				// If multiple messages are sent using the same .collapseKey()
				// the android target device, if it was offline during earlier
				// message
				// transmissions, will only receive the latest message for that
				// key when
				// it goes back on-line.
				.collapseKey("1")
				.delayWhileIdle(true)
				.addData("message", message.getMessage())
				.addData("title", message.getTitle())
				.build();

		try {
			// use this for multicast messages. The second parameter
			// of sender.send() will need to be an array of register ids.
			androidTargets.add(regId);
			MulticastResult result = sender.send(messageBuilder,
					androidTargets, 1);

			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {

				}
			} else {
				int error = result.getFailure();
				logger.error("Broadcast failure: " + error);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			messageBuilder = null;
		}
	}
}
