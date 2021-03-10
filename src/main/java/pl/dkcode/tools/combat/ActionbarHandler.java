package pl.dkcode.tools.combat;

import org.bukkit.entity.Player;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.utils.ChatUtil;
import pl.dkcode.tools.utils.DataUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActionbarHandler implements Runnable {


    private final ToolsPlugin plugin;
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    public ActionbarHandler(ToolsPlugin toolsPlugin){
        plugin = toolsPlugin;

        service.scheduleAtFixedRate(this, 0, 300, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            if(!player.isOnline()) return;
            String msg = "";
            CombatHandler.PVPClient client = CombatHandler.get(player);
            if(client != null && client.isFight()){
                if(!msg.equals("")){
                    msg = msg + " &8| ";
                }
                msg = msg + "&7Jestes podczas PVP zostalo Ci: &9" + DataUtil.secondsToString(client.getEndTime()) + "&7!";
            }
            if(!msg.equals("")){
                ChatUtil.sendActionbar(player, msg);
            }
        });
    }
}
