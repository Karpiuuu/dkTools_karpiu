package pl.dkcode.tools.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.User;
import pl.dkcode.tools.utils.ChatUtil;

public class ChatListener implements Listener {


    public ChatListener(ToolsPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        User u = UserHandler.get(p);
        e.setFormat(ChatUtil.fixColor(u.getGroup().getPrefix() + "&7" + p.getDisplayName() + " &8» " + u.getGroup().getSuffix() + e.getMessage()));
    }
}
