package com.junmiao.mongo.dao;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoDBDAO {

	public MongoDBDAO(){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MongoClient mongoClient = new MongoClient( "ds035448.mongolab.com" , 35448 );
			DB db = mongoClient.getDB("stocka");
			boolean auth = db.authenticate("root", "password".toCharArray());
			
			String json = "{\"name\" : \"junmiaoshen\"}";
			insertDoc(json, "profiles", db);
		
			
			if (auth) {
				Set<String> colls = db.getCollectionNames();

				for (String s : colls) {
				    System.out.println(s);
				}
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void insertDoc(String json, String collectionName, DB db){
		
		DBCollection collection = db.getCollection(collectionName);
		
		DBObject dbObject = (DBObject)JSON.parse(json);
		
		collection.insert(dbObject);
		
	}

}
