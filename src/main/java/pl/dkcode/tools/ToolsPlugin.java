package pl.dkcode.tools;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dkcode.tools.commands.admin.BanCommand;
import pl.dkcode.tools.commands.admin.GroupCommand;
import pl.dkcode.tools.commands.user.HelpCommand;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.MongoHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.bans.BanHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.listeners.ChatListener;
import pl.dkcode.tools.listeners.JoinListener;

import java.util.Arrays;

public class ToolsPlugin extends JavaPlugin {

    private static ToolsPlugin main;


    public void onEnable() {

        MongoHandler handler = new MongoHandler();
        UserHandler userHandler = new UserHandler(handler.db("minecraft").getCollection("users"));
        BanHandler banHandler = new BanHandler(handler.db("minecraft").getCollection("bans"));
        new JoinListener(this, userHandler);
        new ChatListener(this);

        //komendy
        CommandHandler.register(
                new HelpCommand("pomoc", GroupType.PLAYER,"/pomoc", Arrays.asList("help"),this),
                new GroupCommand("group", GroupType.PLAYER, "/group <nick> <grupa> <czas/perm>", Arrays.asList("grupa", "perm"), this),
                new BanCommand("ban", GroupType.PLAYER, "/wypierdalaj <nick> <czas/perm> <powod>", Arrays.asList("wypierdalaj"), this)
        );
    }

    public static ToolsPlugin getMain() {
        return main;
    }

    public void onDisable() { }



}
