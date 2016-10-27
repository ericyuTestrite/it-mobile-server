package com.testritegroup.mobile.server.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.testritegroup.mobile.server.Config;

public class MongoDBServices {
	static private MongoDBServices dbServer;
	final MongoClient mongoClient;
    final MongoDatabase database;
	
    
    //register DAO
	private final UserDevicesDao userDevicesDao;
	public UserDevicesDao getUserDevicesDao() {
		return userDevicesDao;
	}
	
	//register DAO
	private final PushLogDao pushLogDao;
	public PushLogDao getPushLogDao() {
		return pushLogDao;
	}
	
	//register DAO (userImageDao)
	private final UserImageDao userImageDao;
	public UserImageDao getUserImageDao() {
		return userImageDao;
	}
	
	
	
	private MongoDBServices(String dbName){
		String mongoDBURL = Config.getInstance().getMongoDBURL();
		mongoClient = new MongoClient(new MongoClientURI(mongoDBURL));
		database = mongoClient.getDatabase(dbName);
		
		// initial the DAO
        userDevicesDao = new UserDevicesDao(database);
        pushLogDao = new PushLogDao(database);
        // initial  userImageDao
        userImageDao = new UserImageDao(database);
	}
	
	public static MongoDBServices getInstance(){
		if(dbServer == null){
			dbServer = new MongoDBServices("itpush");
		}
		return dbServer;
	}

}
