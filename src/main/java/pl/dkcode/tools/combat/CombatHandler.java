package pl.dkcode.tools.combat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CombatHandler {


    private static final HashMap<Player, PVPClient> list = new HashMap<>();
    static int delay;


    public CombatHandler(){
        delay = 20;
    }


    public static PVPClient create(Player player, Player damager, double damage){
        PVPClient client = new PVPClient(player.getName(), System.currentTimeMillis(), damager);
        client.getHit().put(damager, damage);
        list.put(player, client);
        return client;
    }

    public static PVPClient get(Player player){
        return list.get(player);
    }

    public static PVPClient end(Player player){
        PVPClient client = list.get(player);
        list.remove(player);
        return client;
    }


    public static class PVPClient {

        private final String name;
        private Long last_hit;
        private Player last_hitter;

        private final HashMap<Player, Double> hit = new HashMap<>();

        public PVPClient(String name, Long last_hit, Player last_hitter) {
            this.name = name;
            this.last_hit = last_hit;
            this.last_hitter = last_hitter;
        }


        public void hit(Player damager, Double damage){
            this.hit.put(damager, (this.hit.getOrDefault(damager, 0.0)) + damage);
            this.last_hit = System.currentTimeMillis();
            this.last_hitter = damager;
        }


        public String getName() {
            return name;
        }

        public Long getLast_hit() {
            return last_hit;
        }

        public void setLast_hit(Long last_hit) {
            this.last_hit = last_hit;
        }

        public Player getLast_hitter() {
            return last_hitter;
        }

        public void setLast_hitter(Player last_hitter) {
            this.last_hitter = last_hitter;
        }

        public Long getEndTime(){
            return last_hit + (1000 * delay);
        }

        public boolean isFight(){
            return  last_hit + (1000 * delay) > System.currentTimeMillis();
        }

        public HashMap<Player, Double> getHit() {
            return hit;
        }
    }
}
