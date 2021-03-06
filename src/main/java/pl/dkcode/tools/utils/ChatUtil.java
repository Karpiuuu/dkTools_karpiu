package pl.dkcode.tools.utils;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.dkcode.tools.ToolsPlugin;


public class ChatUtil {

    public static String fixColor(String s) {
        return s.replace("&", "§");
    }

    public static boolean sendMessage(CommandSender p, String s) {
        if (p instanceof Player) {
            p.sendMessage(fixColor(s));
            return true;
        } else {
            System.out.println(s);
            return true;
        }
    }

    public static void clear(int lines) {
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlugin.getMain(), new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < lines; i++) {
                    Bukkit.broadcastMessage("  ");
                }
            }
        });
    }
    public static void sendtoadmins(String mess) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("op")) {
                player.sendMessage(mess);
            }
        }
    }
    
}