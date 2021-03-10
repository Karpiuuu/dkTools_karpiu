package pl.dkcode.tools.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.ChatStatus;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.impels.User;
import pl.dkcode.tools.utils.ChatUtil;

public class ChatListener implements Listener {


    public ChatListener(ToolsPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        User u = UserHandler.get(p);
        if(ToolsPlugin.chatStatus == ChatStatus.OFF){
            if(u.getGroup().getLevel() < GroupType.HELPER.getLevel()){
                e.setCancelled(true);
                ChatUtil.sendMessage(e.getPlayer(), "&7Czat jest aktualnie &9wylaczony&7!");
            }
        }
        else if(ToolsPlugin.chatStatus == ChatStatus.VIP){
            if(u.getGroup().getLevel() < GroupType.VIP.getLevel()){
                e.setCancelled(true);
                ChatUtil.sendMessage(e.getPlayer(), "&7Czat jest wlaczony tylko dla rangi &9VIP &7lub wyzej!");
            }
        }
        e.setFormat(ChatUtil.fixColor(u.getGroup().getPrefix() + "&7" + p.getDisplayName() + " &8» " + u.getGroup().getSuffix() + e.getMessage()));
    }
}
