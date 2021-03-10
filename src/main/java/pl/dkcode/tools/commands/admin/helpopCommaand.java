package pl.dkcode.tools.commands.admin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.impels.User;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.HashMap;
import java.util.List;


public class helpopCommaand extends CommandHandler.Command {
    public helpopCommaand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    private HashMap<Player, Long> user = new HashMap<>();

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0){
            doUsage(p);
            return false;
        }
        if(user.containsKey(p)){
            if(user.get(p) >= System.currentTimeMillis()){
                sender.sendMessage(ChatUtil.fixColor("&7Musisz odczekac jeszcze: &9" +((user.get(p) - System.currentTimeMillis())/1000)  + "s&7!"));
                return false;
            }
            else{
                user.remove(ToolsPlugin.getMain().getServer().getPlayer(sender.getName()));
            }
        }
        else{
            StringBuilder msg = new StringBuilder();
            for (String arg : args) {
                msg.append(arg).append(" ");
            }
            TextComponent text = new TextComponent();
            text.setText(ChatUtil.fixColor("&7(&9" + sender.getName() +"&7) &8 - &9" + msg));
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + sender.getName()));
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtil.fixColor("&7/tp &9" + sender.getName())).create()));
            for(Player p1 : Bukkit.getOnlinePlayers()){
                User u = UserHandler.get(p1);
                if(u.getGroup().getLevel() > GroupType.HELPER.getLevel()){
                    p1.spigot().sendMessage(text);
                }
            }
            sender.sendMessage(ChatUtil.fixColor("&9Wiadomosc zostala wyslana do administracji!"));
            user.put(p, System.currentTimeMillis() + 30*1000);
        }


        return false;
    }
}
