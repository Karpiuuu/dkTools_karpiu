package pl.dkcode.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.enums.User;
import pl.dkcode.tools.utils.ChatUtil;
import pl.dkcode.tools.utils.DataUtil;

import java.security.acl.Group;
import java.util.List;
import java.util.Locale;

public class GroupCommand extends CommandHandler.Command {
    public GroupCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length == 0){
            Player p = (Player) sender;
            doUsage(p);
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        User u = UserHandler.get(p);
        GroupType type = GroupType.valueOf(args[1]);
        if(type == null){
            return ChatUtil.sendMessage(sender, "&cTaka grupa nie istnieje!");
        }
        final long time = DataUtil.parseDateDiff(args[2], true);
        if(args[2].equalsIgnoreCase("perm")){
            u.setGroup(type);
            u.setGroup_time(0L);
            u.synchronize();
            ChatUtil.sendMessage(p, "&4Otrzymales grupe: &c" + type + " &4na czas nieokreslony!");
            return ChatUtil.sendMessage(sender, "&4Nadano groupe: &c" + type + " &4dla gracza: &c" + args[0] + "&4.");
        }
        if(time > System.currentTimeMillis()){
            u.setGroup(type);
            u.setGroup_time(time);
            u.synchronize();
            ChatUtil.sendMessage(p, "&4Otrzymales grupe: &c" + type + " &4do: &c" + DataUtil.getDate(time) + "&4.");
            return ChatUtil.sendMessage(sender, "&4Nadano groupe: &c" + type + " &4dla gracza: &c" + args[0] + "&4 do: &c" + DataUtil.getDate(time) + "&4.");
        }
        return false;
    }
}
