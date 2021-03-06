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
import pl.dkcode.tools.utils.DataUtil;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BanCommand extends CommandHandler.Command {
    public BanCommand(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
        super(name, perm, usage, aliases, plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        // /ban <nick> <czas/perm> <powod>
        if(args.length < 3){
            doUsage(player);
            return false;
        }
        OfflinePlayer target = getPlugin().getServer().getOfflinePlayer(args[0]);
        if(target == null){
            return ChatUtil.sendMessage(sender, "&cTen gracz nie istnieje w bazie");
        }
        if(BanHandler.get(target.getName()) != null){
            return ChatUtil.sendMessage(sender, "&cTen gracz ma juz bana");
        }
        long banTime = 0L;
        if(!args[1].equalsIgnoreCase("perm"))
            banTime = DataUtil.parseDateDiff(args[1],true);
        StringBuilder banReason = new StringBuilder();
        for(int i = 2;i<args.length;i++) banReason.append(args[i]).append(" ");
        Player targetPlayer = getPlugin().getServer().getPlayer(args[0]);
        Ban ban = BanHandler.create(new Ban((targetPlayer == null ? args[0] : target.getName()),banReason.toString(),sender.getName(),banTime));
        if(targetPlayer != null){
            String reason =
                    "\n&4Zostales zbanowany przez &c" + ban.getAdmin()+
                            "\n&4Wygasa &c" + ((ban.getTime() == 0L) ? "Nigdy!" : "&4za &c" + DataUtil.secondsToString(ban.getTime())) +
                            "\n&4Powod: &c" + ban.getReason()+
                            "\n&4Kup unbana na stronie: &4https://crafteria.pl" +
                            "\n&7Niesluszny ban? Wejdz na ts3: &4ts.crafteria.pl &7lub na fp: &4fp.crafteria.pl";
            targetPlayer.kickPlayer(ChatUtil.fixColor(reason));
        }
        return false;
    }
}
