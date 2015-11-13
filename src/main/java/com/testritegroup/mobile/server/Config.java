package com.testritegroup.mobile.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	
	static Logger logger = LoggerFactory.getLogger(Config.class);
	
	public static Config thisConfig = null;
	
	public Integer servicePort = new Integer(8082);
	public Integer getServicePort() {
		return servicePort;
	}

	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}

	public String getMongoDBURL() {
		return mongoDBURL;
	}

	public void setMongoDBURL(String mongoDBURL) {
		this.mongoDBURL = mongoDBURL;
	}

	public String mongoDBURL;
	
	public String adServer;
	
	public String getAdServer() {
		return adServer;
	}

	public void setAdServer(String adServer) {
		this.adServer = adServer;
	}
	
	private String adDomain;

	public String getAdDomain() {
		return adDomain;
	}

	public void setAdDomain(String adDomain) {
		this.adDomain = adDomain;
	}

	private Config() throws Exception {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			input = new FileInputStream("./resources/conf.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			Integer port = new Integer(prop.getProperty("servicePort"));
			logger.info("PushServer running on "+port.intValue());
			
			mongoDBURL = prop.getProperty("mongoDBURL");
			logger.info("Connecting to MongoDB "+mongoDBURL);
			
			adServer = prop.getProperty("adServer");
			logger.info("Connecting to AdServer "+adServer);
			
			adDomain = prop.getProperty("adDomain");
			logger.info("AD Domain "+adDomain);

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Config getInstance(){
		if(thisConfig==null){
			try {
				thisConfig = new Config();
			} catch (Exception e) {
				logger.error("Loading resources/conf.properties failed, "+ e.getMessage());
			}
		}
		return thisConfig;
	}

}
