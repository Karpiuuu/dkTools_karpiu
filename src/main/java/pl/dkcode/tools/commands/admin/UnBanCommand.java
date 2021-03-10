package pl.dkcode.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.bans.Ban;
import pl.dkcode.tools.handlers.bans.BanHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.List;

public class UnBanCommand extends CommandHandler.Command {

    public UnBanCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        //unban <nick>
        Player player = (Player) sender;

        if(args.length != 1) {
            doUsage(player);
            return false;
        }
        if(BanHandler.get(args[0]) == null){
            return ChatUtil.sendMessage(sender, "&cTen gracz nie ma bana!");
        }
        BanHandler.delete(args[0]);
        getPlugin().getServer().getOnlinePlayers().forEach(target -> ChatUtil.sendMessage(target, "&7Gracz &9" + args[0] + "&7 zostal odbanowany przez: &9" + sender.getName()));
        return false;
    }
}
