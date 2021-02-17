package pl.dkcode.tools.handlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoHandler {

    private MongoClient client;

    public MongoHandler(){
        try{
            client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (Exception e){
          e.printStackTrace();
        }
    }

    public MongoClient get(){
        return client;
    }

    public MongoDatabase db(String name){
        return client.getDatabase(name);
    }

}
