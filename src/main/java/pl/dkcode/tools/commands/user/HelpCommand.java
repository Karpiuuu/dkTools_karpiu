package pl.dkcode.tools.commands.user;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("pomoc")){
            sender.sendMessage("x");
        }
        return false;
    }
}
