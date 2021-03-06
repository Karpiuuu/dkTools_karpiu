package pl.dkcode.tools.handlers.bans;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class BanHandler {

    private static final Map<String, Ban> bans = new HashMap<>();

    private static MongoCollection collection;



    public BanHandler(MongoCollection collection) {
        this.collection = collection;
        collection.find().forEach((Block<? super Document>) (Document document) -> {
            try{
                Ban ban = new Gson().fromJson(document.toJson(), Ban.class);
                bans.put(ban.getName(), ban);
            } catch (Exception e){
                e.printStackTrace();
            }
        });


    }

    public static Ban create(Ban ban){
        bans.put(ban.getName(), ban);
        return ban;
    }

    public static Ban delete(String p){
        Ban ban = bans.values().stream().filter(b -> b.getName().equals(p)).findFirst().get();
        bans.remove(ban.getName());
        ban.delete();
        return ban;
    }

    public static Ban get(String  player){
        return bans.get(player);
    }

    public static MongoCollection getCollection(){
        return collection;
    }


}
