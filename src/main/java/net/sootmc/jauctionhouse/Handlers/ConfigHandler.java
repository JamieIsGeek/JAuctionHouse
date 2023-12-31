package net.sootmc.jauctionhouse.Handlers;

import net.sootmc.jauctionhouse.JAuctionHouse;
import org.bukkit.configuration.Configuration;

public class ConfigHandler {
    private final JAuctionHouse plugin;
    private Configuration config;

    public ConfigHandler(JAuctionHouse plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        try {
            plugin.saveConfig();

            this.config = plugin.getConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getFromConfig(String path) {
        return config.get(path);
    }
    public String getString(String path) {
        return config.getString(path);
    }
    public Integer getInt(String path) {
        return config.getInt(path);
    }
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }
}