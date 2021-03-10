package pl.dkcode.tools.utils;


import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.impels.User;


public class ChatUtil {


    public static String replace(String s){
        if(s == null || s.equals("")){
            return "";
        }
        String s1 = s;
        s1 = s1.replace(">>","»");
        s1 = s1.replace("<<","«");
        s1 = s1.replace("{O}","•");
        s1 = s1.replace("<3", "❤");
        s1 = s1.replace("(n)", "✖");
        s1 = s1.replace("(y)","✔");
        s1 = s1.replace("&","§");
        return s1;
    }


    public static void sendMessage(Player p, String message){
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }

    public static void sendActionbar(Player p, String message) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(
                new PacketPlayOutChat(
                        IChatBaseComponent.ChatSerializer.a(
                                "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"
                        ), (byte)2
                )
        );
    }

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
    public static void helpop(String mess) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            User u = UserHandler.get(player);
            if (u.getGroup().getLevel() > GroupType.HELPER.getLevel()) {
                player.sendMessage(fixColor(mess));
            }
        }
    }
    
}