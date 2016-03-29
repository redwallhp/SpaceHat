package io.github.redwallhp.spacehat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;


public class SpaceHat extends JavaPlugin implements CommandExecutor {


    private Configuration configuration;


    @Override
    public void onEnable() {
        configuration = new Configuration(this);
        new SpaceHatListener(this);
        new DamageTask(this).runTaskTimer(this, 20L, 20L);
        getCommand("rehat").setExecutor(this);
    }


    public Configuration getConfiguration() {
        return configuration;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rehat") && sender instanceof Player) {

            Player player = (Player) sender;

            if (!player.hasMetadata("noSpaceHat")) return true;

            ItemStack hat = (ItemStack) player.getMetadata("noSpaceHat").get(0).value();
            if (hat != null) {
                player.getInventory().setHelmet(hat);
                player.removeMetadata("noSpaceHat", this);
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have your hat item!");
            }

        }
        return true;
    }


}
