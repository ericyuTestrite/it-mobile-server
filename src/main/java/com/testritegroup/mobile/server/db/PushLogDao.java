package com.testritegroup.mobile.server.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.testritegroup.mobile.server.route.model.PushMessage;

public class PushLogDao {
	private final MongoCollection<Document> pushLogCollection;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public PushLogDao(final MongoDatabase blogDatabase) {
		pushLogCollection = blogDatabase.getCollection("pushLog");
	}

	public boolean insertLog(PushMessage message) {
		
		Document doc = new Document("sendDate", sdf.format(new Date()))
				.append("from", message.getFrom())
				.append("to", message.getReceiverUserId())
				.append("appId", message.getAppId())
				.append("platformType", message.getPlatformType())
				.append("category", message.getCategory())
				.append("title", message.getTitle())
				.append("message", message.getMessage());
		
		pushLogCollection.insertOne(doc);
		return true;
	}
	
	public List<PushMessage> findPushLogByUserId(String userId, 
			String appId) throws ParseException{
		
		Document queryFilter = new Document("to", userId).append("appId", appId);
		
		List<Document> pushLogs = pushLogCollection.find(queryFilter)
				.limit(20)
				.sort(new BasicDBObject("sendDate", -1))
				.into(new ArrayList<Document>());
		List<PushMessage> result = new ArrayList<PushMessage>();
		for(Document pushLogDoc:pushLogs){
			PushMessage message = new PushMessage();
			message.setAppId(pushLogDoc.getString("appId"));
			message.setSendDate(sdf.parse(pushLogDoc.getString("sendDate")));
			message.setReceiverUserId(pushLogDoc.getString("to"));
			message.setFrom(pushLogDoc.getString("from"));
			message.setPlatformType(pushLogDoc.getString("platformType"));
			message.setCategory(pushLogDoc.getString("category"));
			message.setTitle(pushLogDoc.getString("title"));
			message.setMessage(pushLogDoc.getString("message"));
			result.add(message);
		}
		return result;
	}

}
