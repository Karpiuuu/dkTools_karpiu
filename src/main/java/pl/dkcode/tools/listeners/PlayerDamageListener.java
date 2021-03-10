package pl.dkcode.tools.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.combat.CombatHandler;

public class PlayerDamageListener implements Listener {

    public PlayerDamageListener(ToolsPlugin toolsPlugin){
        toolsPlugin.getServer().getPluginManager().registerEvents(this, toolsPlugin);
    }


    public static Player getDamager(final EntityDamageByEntityEvent e){
        final Entity damager = e.getDamager();
        if(damager instanceof Player){
            return (Player) damager;
        }
        if(damager instanceof Projectile){
            final Projectile p = (Projectile) damager;
            if(p.getShooter() instanceof Player){
                return (Player)p.getShooter();
            }
        }
        return null;
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void EntityDamageByEntity(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        final Player damager = getDamager(e);
        if(damager == null) return;
        final Player p = (Player) e.getEntity();
        if(p != null){
            if(p.equals(damager)) return;
            CombatHandler.PVPClient client = CombatHandler.get(p);
            CombatHandler.PVPClient clientDamager = CombatHandler.get(damager);
            if(client != null){
                client.hit(damager,e.getDamage());
            }else{
                client = CombatHandler.create(p,damager,e.getDamage());
            }
            if(clientDamager != null){
                clientDamager.hit(p, 0.0);
            }else{
                clientDamager = CombatHandler.create(damager,p,0.0);
            }
        }
    }
}
