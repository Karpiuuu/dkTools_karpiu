package pl.dkcode.tools.handlers.impels;

import com.google.gson.Gson;
import org.bson.Document;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.GroupType;

import java.util.Locale;

public class User {

    private final String name;
    private final String ip;
    private Integer connect_int;
    private String group;
    private long group_time;

    public User(String name, String ip, Integer connect_int) {
        this.name = name;
        this.ip = ip;
        this.connect_int = connect_int;
        this.group = "PLAYER";
        this.group_time = 0L;
        insert();
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public Integer getConnect_int() {
        return connect_int;
    }

    public void setConnect_int(Integer connect_int) {
        this.connect_int = connect_int;
    }

    private void insert(){
        if(group == null) group = GroupType.PLAYER.getName().toUpperCase();
        UserHandler.getCollection().insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        UserHandler.getCollection().findOneAndUpdate(new Document("name", name), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete(){
        UserHandler.getCollection().findOneAndDelete(new Document("name", name));
    }

    public GroupType getGroup() {
        return GroupType.valueOf(group.toUpperCase());
    }

    public void setGroup(GroupType group) {
        this.group = group.getName().toUpperCase();
    }

    public long getGroup_time() {
        return group_time;
    }

    public boolean isGroupExpired(){
        return (group_time != 0L && group_time < System.currentTimeMillis());
    }

    public void setGroup_time(long group_time) {
        this.group_time = group_time;
    }
}
