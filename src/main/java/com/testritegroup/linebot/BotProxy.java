package com.testritegroup.linebot;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class BotProxy {
	Logger logger = LoggerFactory.getLogger(BotProxy.class);
	private static BotProxy botProxy = null;
	private BotProxy(){		
	}
	public static BotProxy getInstance(){
		if(botProxy == null){
			botProxy = new BotProxy();
		}
		return botProxy; 
	}
	
	public String receive(String messageJson){
		logger.info(messageJson);
		return "Got message!";
	}

}
