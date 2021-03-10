package pl.dkcode.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.enums.ChatStatus;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.List;

public class chatCommand extends CommandHandler.Command {
    public chatCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length != 1){
            doUsage(player);
            return false;
        }
        if(args[0].equalsIgnoreCase("on")){
            if(ToolsPlugin.chatStatus == ChatStatus.ON){
                return ChatUtil.sendMessage(sender, "&cChat jest juz wlaczany!");
            }
            ToolsPlugin.chatStatus = ChatStatus.ON;
            sendMessagetoall("&9&lCHAT &8&l- &7Czat zostal wlaczony przez: &9" + sender.getName());
            return ChatUtil.sendMessage(sender, "&cCzat zostal wlaczony!");
        }
        else if(args[0].equalsIgnoreCase("off")){
            if(ToolsPlugin.chatStatus == ChatStatus.OFF){
                return ChatUtil.sendMessage(sender, "&cChat jest juz wlaczany!");
            }
            ToolsPlugin.chatStatus = ChatStatus.OFF;
            sendMessagetoall("&9&lCHAT &8&l- &7Czat zostal wylaczony przez: &9" + sender.getName());
            return ChatUtil.sendMessage(sender, "&cCzat zostal wylaczony!");
        }
        else if(args[0].equalsIgnoreCase("vip")){
            if(ToolsPlugin.chatStatus == ChatStatus.VIP){
                return ChatUtil.sendMessage(sender, "&cChat jest juz wlaczany dla trybu VIP!");
            }
            ToolsPlugin.chatStatus = ChatStatus.VIP;
            sendMessagetoall("&9&lCHAT &8&l- &7Czat zostal wlaczony tylko dla rangi VIP przez: &9" + sender.getName());
            return ChatUtil.sendMessage(sender, "&cCzat zostal wlaczony dla trybu VIP!");
        }
        else if(args[0].equalsIgnoreCase("cc") || args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")){
            sendMessagetoall("&9&lCHAT &8&l- &7Czat zostal wyczyszczony przez: &9" + sender.getName());
            return true;
        }
        return false;
    }


    public void sendMessagetoall(String text){
        for (int i = 0; i< 101l; i++){
            Bukkit.getOnlinePlayers().forEach(e -> {
                ChatUtil.sendMessage(e, "");
            });
        }
        Bukkit.getOnlinePlayers().forEach(e -> {
            e.sendMessage(ChatUtil.fixColor(text));
        });
    }
}
