package com.testritegroup.mobile.server.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.testritegroup.mobile.server.route.model.UserImage;

public class UserImageDao {
	private final String USERIMAGE_TABLE= "userImage";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final MongoCollection<Document> userImageCollection;
	Logger logger = LoggerFactory.getLogger(UserImageDao.class);

	public UserImageDao(final MongoDatabase blogDatabase) {
		userImageCollection = blogDatabase.getCollection(USERIMAGE_TABLE);
	}

	public boolean insertUserDevice(UserImage userImage)  throws Exception{
		String userId= userImage.getUserId();
		String appId=userImage.getAppId();
		String key="";
		Date createDate=new Date();
		List<UserImage> result = findUserImageByUserIdAndAppId(userId, appId);
		if (result!=null && result.size()>0){
			key=result.get(0).getId();
			createDate=result.get(0).getCreateDate();
		}else{
			key= getKey() ;
		}

		logger.info("key(_id)="+key);
		Document doc = new Document("_id", key)
				.append("userId", userId)
				.append("appId", appId)
				.append("mimeType", userImage.getMimeType())
				.append("imageFile", userImage.getImageFile())
				.append("createDate", sdf.format(createDate))
				.append("modifyDate", sdf.format(new Date()));
		
		UpdateOptions updateOptions= new UpdateOptions();
		updateOptions.upsert(true);
		userImageCollection.updateOne(
			    new BasicDBObject("_id", key), 
			    new BasicDBObject("$set", doc), updateOptions);
		return true;
	}
	/**
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 * @throws ParseException
	 */
	public List<UserImage> findUserImageByUserIdAndAppId(String userId, String appId)
			throws ParseException {
		Document queryFilter = new Document("userId", userId).append("appId", appId);
		List<Document> listDoc = userImageCollection.find(queryFilter)
				.sort(new BasicDBObject("createDate", -1))
				.into(new ArrayList<Document>());
		if (listDoc!=null){
			logger.info("listDoc(size)="+listDoc.size());
		}
		List<UserImage> result = new ArrayList<UserImage>();
		for(Document doc:listDoc){
			// 應該只會有一筆資料
			UserImage obj = new UserImage();
			obj.setId(doc.getString("_id"));
			obj.setAppId(doc.getString("appId"));
			if (doc.getString("createDate")==null){
				obj.setCreateDate(new Date());
			}else{
				obj.setCreateDate(sdf.parse(doc.getString("createDate")));
			}
			obj.setImageFile(doc.getString("imageFile") );
			obj.setMimeType(doc.getString("mimeType") );
			obj.setUserId(doc.getString("userId")  );
			obj.setModifyDate(sdf.parse(doc.getString("modifyDate")) );
			logger.info("obj="+obj.toString());
			logger.info("doc key(_id)="+obj.getId());
			result.add(obj);
		}
		return result;
	}
	
	 public static synchronized String getKey() {
		    // delay
		    try {
		      Thread.sleep(50);
		    }
		    catch (InterruptedException ex) {
		    }
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		    String uid = sdf.format(new Date());
		    return uid;
		  }
	 
	 /**
	  * 
	  * @return
	  */
	 public static String generateUUID() {
	        UUID uuid = UUID.randomUUID();
	        return uuid.toString();
	    }
}
