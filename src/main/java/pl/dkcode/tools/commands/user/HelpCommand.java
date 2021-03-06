package pl.dkcode.tools.commands.user;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.List;

public class HelpCommand extends CommandHandler.Command {

    public HelpCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        ChatUtil.sendMessage(sender, "&cPomoc");
        return false;
    }
}
