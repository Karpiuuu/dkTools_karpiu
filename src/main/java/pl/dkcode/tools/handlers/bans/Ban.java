package pl.dkcode.tools.handlers.bans;

import com.google.gson.Gson;
import org.bson.Document;

public class Ban {

    private String name;
    private String reason;
    private String admin;
    private long time;
    private long start;


    public Ban(String name, String reason, String admin, long time) {
        this.name = name;
        this.reason = reason;
        this.admin = admin;
        this.time = time;
        this.start = System.currentTimeMillis();
        insert();
    }

    public void insert(){
        BanHandler.getCollection().insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        BanHandler.getCollection().findOneAndUpdate(new Document("name", name), new Document("$set", Document.parse(new Gson().toJson(this))));
    }


    public void delete(){
        BanHandler.getCollection().findOneAndDelete(new Document("name", name));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdmin(){
        return this.admin;
    }
    public void setAdmin(String admin){
        this.admin = admin;
    }

    public long getTime(){
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStart() {
        return start;
    }

}
