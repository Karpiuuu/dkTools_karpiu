package pl.dkcode.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.CommandHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.utils.ChatUtil;

import java.util.List;

public class GamemodeCommand extends CommandHandler.Command {
    public GamemodeCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length == 1){
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")){
                player.setGameMode(GameMode.CREATIVE);
                player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9CREATIVE"));

            }
            else if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")){
                player.setGameMode(GameMode.SURVIVAL);
                player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9SURVIVAL"));
            }
            else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")){
                player.setGameMode(GameMode.ADVENTURE);
                player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9ADVENTURE"));
            }
            else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")){
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9SPECTATOR"));
            }
        }
        else if(args.length == 2){
            Player player = Bukkit.getPlayer(args[1]);
            if(player != null){
                if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9CREATIVE"));

                }
                else if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")){
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9SURVIVAL"));
                }
                else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")){
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9ADVENTURE"));
                }
                else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")){
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendTitle(ChatUtil.fixColor("&9&lGAMEMODE"), ChatUtil.fixColor("&7Zmieniono tryb gry na &9SPECTATOR"));
                }
            } else{
                return ChatUtil.sendMessage(sender, "&cGracz jest offline!");
            }
        }
        else {
            Player p = (Player) sender;
            doUsage(p);
        }
        return false;
    }
}
