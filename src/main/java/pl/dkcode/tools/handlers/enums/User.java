package pl.dkcode.tools.handlers.enums;

import com.google.gson.Gson;
import org.bson.Document;
import pl.dkcode.tools.handlers.MongoHandler;
import pl.dkcode.tools.handlers.UserHandler;

public class User {

    private final String name;
    private final String ip;
    private Integer connect_int;

    public User(String name, String ip, Integer connect_int) {
        this.name = name;
        this.ip = ip;
        this.connect_int = connect_int;
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
        UserHandler.getCollection().insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        UserHandler.getCollection().findOneAndUpdate(new Document("name", name), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete(){
        UserHandler.getCollection().findOneAndDelete(new Document("name", name));
    }

}