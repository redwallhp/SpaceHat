package io.github.redwallhp.spacehat;


import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class DamageTask extends BukkitRunnable {


    private SpaceHat plugin;
    private double damage;


    public DamageTask(SpaceHat plugin) {
        this.plugin = plugin;
        this.damage = plugin.getConfiguration().DAMAGE;
    }


    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.hasMetadata("noSpaceHat") && !player.isDead()) {
                player.damage(damage);
            }
        }
    }


}
