package pl.dkcode.tools.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.bans.Ban;
import pl.dkcode.tools.handlers.bans.BanHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.List;

public class CheckbanCommand extends CommandHandler.Command {
    public CheckbanCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length != 1){
            doUsage(player);
            return false;
        }
        Ban ban = BanHandler.get(args[0]);
        if(!args[0].equalsIgnoreCase("list")) {
            if (ban != null) {
                ChatUtil.sendMessage(sender, "&7Ten gracz ma bana!\n");
                ChatUtil.sendMessage(sender, "&7Nick: &9" + ban.getName());
                ChatUtil.sendMessage(sender, "&7Powod: &9" + ban.getReason());
                ChatUtil.sendMessage(sender, "&7Admin, ktory zbanowal: &9" + ban.getAdmin());
                return ChatUtil.sendMessage(sender, "&7Czas bana: &9" + ban.getTime());
            }
            return ChatUtil.sendMessage(sender, "&cTen gracz nie ma bana!");
        }
        return ChatUtil.sendMessage(sender, "&7Aktualnie zbanowanych: &9" + BanHandler.getAmount());
    }
}
