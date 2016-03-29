package io.github.redwallhp.spacehat;

import java.util.List;


public class Configuration {


    private SpaceHat plugin;
    public double DAMAGE;
    public String WARNING;
    public List<String> DEATH_MESSAGES;


    public Configuration(SpaceHat plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        load();
    }


    public void load() {

        String defaultWarning = "&7It seems you've removed your space helmet. Don't you feel silly? &c(Type &e/rehat&c to put it back on)";

        DAMAGE = plugin.getConfig().getDouble("damage", 1.0d);
        WARNING = plugin.getConfig().getString("warming_message", defaultWarning);
        DEATH_MESSAGES = plugin.getConfig().getStringList("death_messages");

    }


}
