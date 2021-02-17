package pl.dkcode.tools;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dkcode.tools.handlers.MongoHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.listeners.Testlistener;

public class ToolsPlugin extends JavaPlugin {


    public void onEnable() {

        MongoHandler handler = new MongoHandler();
        UserHandler userHandler = new UserHandler(handler.db("minecraft").getCollection("users"));
        new Testlistener(this, userHandler);
    }

    public void onDisable() { }
}
