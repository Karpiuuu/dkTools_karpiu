package pl.dkcode.tools.handlers;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.entity.Player;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.impels.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UserHandler {

    private static final Map<String, User> users = new HashMap<>();

    private static MongoCollection collection;

    private static ScheduledExecutorService service = new ScheduledThreadPoolExecutor(4);


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

        service.scheduleAtFixedRate(() -> users.values().forEach(u -> {
            if(u.isGroupExpired()) {
                u.setGroup(GroupType.PLAYER);
                u.setGroup_time(0L);
            }
        }),0,3000, TimeUnit.MILLISECONDS);
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

    public static User get(String p){
        return users.get(p);
    }

    public static MongoCollection getCollection() {
        return collection;
    }
}
