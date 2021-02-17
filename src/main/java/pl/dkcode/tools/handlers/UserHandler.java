package pl.dkcode.tools.handlers;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.entity.Player;
import pl.dkcode.tools.handlers.enums.User;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private static final Map<String, User> users = new HashMap<>();

    private static MongoCollection collection;


    public UserHandler(MongoCollection collection){
        this.collection = collection;
        collection.find().forEach((Block<? super Document>) (Document document) -> {
            try{
                User u = new Gson().fromJson(document.toJson(), User.class);
                users.put(u.getName(), u);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public static User create(Player player){
        if(users.containsKey(player.getName())) return users.get(player.getName());
        User user = new User(player.getName(), player.getAddress().getAddress().getHostAddress(), 1);
        users.put(user.getName(), user);
        return user;
    }

    public static User get(Player p){
        return users.get(p.getName());
    }

    public static MongoCollection getCollection() {
        return collection;
    }
}
