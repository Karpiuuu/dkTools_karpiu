package pl.dkcode.tools.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.combat.CombatHandler;
import pl.dkcode.tools.handlers.UserHandler;
import pl.dkcode.tools.handlers.bans.Ban;
import pl.dkcode.tools.handlers.bans.BanHandler;
import pl.dkcode.tools.handlers.impels.User;
import pl.dkcode.tools.utils.ChatUtil;
import pl.dkcode.tools.utils.DataUtil;

public class JoinListener implements Listener {


    private ToolsPlugin plugin;
    private final UserHandler userHandler;

    public JoinListener(ToolsPlugin plugin, UserHandler userHandler){
        this.userHandler = userHandler;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.sendTitle(ChatUtil.fixColor("&9&lCrafteria.pl"), ChatUtil.fixColor("&7Wybierz tryb gry!"));
        User u = userHandler.get(e.getPlayer());
        if(u == null){
           u = userHandler.create(e.getPlayer());
        }
        else {
            u.setConnect_int(u.getConnect_int() + 1);
            u.synchronize();
        }
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        CombatHandler.PVPClient client = CombatHandler.get(player);
        if(client == null || !client.isFight()) return;
        plugin.getServer().getOnlinePlayers().forEach(player1 -> {
            ChatUtil.sendMessage(player1, "&7Gracz: &9" + player.getName() + "&7 wylogowal sie podczas walki");
        });
        e.getPlayer().getWorld().strikeLightningEffect(e.getPlayer().getLocation());
        player.setHealth(0.0);
    }

    @EventHandler
    public void onPre(PlayerPreLoginEvent event){
        final Ban b = BanHandler.get(event.getName());
        if(b != null){
            if(b.getTime() < System.currentTimeMillis() && b.getTime() != 0L){
                BanHandler.delete(event.getName());
                return;
            }
            String reason =
                    "\n&7Zostales zbanowany przez &4" + b.getAdmin()+
                            "\n&7Powod: &4" + b.getReason()+
                            "\n&7Wygasa &4" + ((b.getTime() == 0L) ? "Nigdy :(" : "&4za &c" + DataUtil.secondsToString(b.getTime())) +
                            "\n&7Kup unbana na stronie: &4https://twojadomena.pl" +
                            "\n&7Niesluszny ban? Wejdz na ts3: &4ts.twojadomena.pl &7lub na fp: &4fp.twojadomena.pl";
            event.setResult(PlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ChatUtil.fixColor(reason));
        }
    }

}
