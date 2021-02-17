package pl.dkcode.tools.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.User;

public class Testlistener implements Listener {


    private final UserHandler userHandler;

    public Testlistener(ToolsPlugin plugin, UserHandler userHandler){
        this.userHandler = userHandler;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){
        User u = userHandler.get(e.getPlayer());
        if(u == null){
           u = userHandler.create(e.getPlayer());
        }
        else {
            u.setConnect_int(u.getConnect_int() + 1);
            u.synchronize();
        }
        e.setJoinMessage(null);
    }

}
