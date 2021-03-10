package pl.dkcode.tools;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dkcode.tools.combat.ActionbarHandler;
import pl.dkcode.tools.combat.CombatHandler;
import pl.dkcode.tools.commands.admin.*;
import pl.dkcode.tools.commands.user.HelpCommand;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.MongoHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.bans.BanHandler;
import pl.dkcode.tools.handlers.enums.ChatStatus;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.listeners.ChatListener;
import pl.dkcode.tools.listeners.JoinListener;
import pl.dkcode.tools.listeners.PlayerDamageListener;
import sun.security.krb5.Config;

import java.util.Arrays;

public class ToolsPlugin extends JavaPlugin {

    private static ToolsPlugin main;
    private Config config;

    public static ChatStatus chatStatus = ChatStatus.ON;


    public void onEnable() {

        MongoHandler handler = new MongoHandler();
        UserHandler userHandler = new UserHandler(handler.db("minecraft").getCollection("users"));
        BanHandler banHandler = new BanHandler(handler.db("minecraft").getCollection("bans"));
        new JoinListener(this, userHandler);
        new ChatListener(this);
        new PlayerDamageListener(this);
        new CombatHandler();
        new ActionbarHandler(this);
        getConfig().options().copyDefaults(true);
        saveConfig();

        //komendy
        CommandHandler.register(
                new HelpCommand("pomoc", GroupType.PLAYER,"/pomoc", Arrays.asList("help"),this),
                new GroupCommand("group", GroupType.HEADADMIN, "/group <nick> <grupa> <czas/perm>", Arrays.asList("grupa", "perm"), this),
                new BanCommand("ban", GroupType.ADMIN, "/wypierdalaj <nick> <czas/perm> <powod>", Arrays.asList("wypierdalaj"), this),
                new UnBanCommand("unban", GroupType.ADMIN, "/unban <nick>", Arrays.asList(), this),
                new CheckbanCommand("checkban", GroupType.HELPER, "/checkban <nick/list>", Arrays.asList("sprawdzban"), this),
                new chatCommand("chat", GroupType.HELPER, "/chat <on/off/vip>", Arrays.asList("czat", "c"), this),
                new GamemodeCommand("gamemode", GroupType.HEADADMIN, "/gm <0/1/2/3> <nick>", Arrays.asList("gm"), this),
                new helpopCommaand("helpop", GroupType.PLAYER, "/helpop <wiadomosc>", Arrays.asList(), this)
                );
    }

    public static ToolsPlugin getMain() {
        return main;
    }

    public void onDisable() { }


}
