package io.github.redwallhp.spacehat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Iterator;
import java.util.Random;


public class SpaceHatListener implements Listener {


    private SpaceHat plugin;


    public SpaceHatListener(SpaceHat plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    /**
     * Make sure the player spawns with a glass hat so they don't die.
     * This should work with CutePvP's glass helmet mode, so new players will get clear glass, but
     * won't conflict with the team-colored helmets.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerInventory inv = event.getPlayer().getInventory();
        ItemStack hat = inv.getHelmet();
        if (hat == null || !(inv.getHelmet().getType().equals(Material.GLASS) || inv.getHelmet().getType().equals(Material.STAINED_GLASS))) {
            inv.setHelmet(new ItemStack(Material.GLASS, 1));
        }
        event.getPlayer().removeMetadata("noSpaceHat", plugin);
    }


    /**
     * Display a warning message to the player when they take their helmet off
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.ARMOR && event.getSlot() == 39) {
            if (event.getCurrentItem().getType().equals(Material.GLASS) || event.getCurrentItem().getType().equals(Material.STAINED_GLASS)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfiguration().WARNING));
                player.setMetadata("noSpaceHat", new FixedMetadataValue(plugin, event.getCurrentItem()));
                event.setCurrentItem(null);
            }
        }
    }


    /**
     * Display a custom death message on death
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().hasMetadata("noSpaceHat")) {

            // Display a custom death message
            if (plugin.getConfiguration().DEATH_MESSAGES.size() > 0) {
                String msg = plugin.getConfiguration().DEATH_MESSAGES.get(new Random().nextInt(plugin.getConfiguration().DEATH_MESSAGES.size()));
                msg = msg.replace("%p", event.getEntity().getName());
                event.setDeathMessage(msg);
            }

            // Remove the hat item from the drop list to prevent farming
            ItemStack hat = (ItemStack) event.getEntity().getMetadata("noSpaceHat").get(0).value();
            ItemStack glass = null;
            for (Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext();) {
                ItemStack stack = it.next();
                if (stack.getType().equals(hat.getType())) {
                    stack.setAmount(stack.getAmount() - 1);
                    if (stack.getAmount() < 1) {
                        it.remove();
                    }
                    break;
                }
            }

            // Clean up the metadata
            event.getEntity().removeMetadata("noSpaceHat", plugin);

        }
    }


}
