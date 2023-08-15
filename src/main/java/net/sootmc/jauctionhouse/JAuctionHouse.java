package net.sootmc.jauctionhouse;

import net.sootmc.jauctionhouse.Handlers.ConfigHandler;
import net.sootmc.jauctionhouse.Handlers.DatabaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JAuctionHouse extends JavaPlugin {
    private DatabaseHandler databaseHandler;
    private ConfigHandler configHandler;
    private static Logger logger;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler(this);
        configHandler.initialize();
        logger = getLogger();
        databaseHandler = new DatabaseHandler(
                isExternalDB(configHandler.getString("storage.type")),
                configHandler.getString("databaseHost"),
                configHandler.getString("databasePort"),
                configHandler.getString("databaseName"),
                configHandler.getString("databaseUsername"),
                configHandler.getString("databasePassword"),
                this.getDataFolder()
        );

        this.getCommand("ah").setExecutor(new AHCommand(databaseHandler, new AHManager(this)));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean isExternalDB(String type) {
        return !type.equalsIgnoreCase("Sqlite");
    }

    public static void Log(String message, Level level) {
        logger.log(level, message);
    }

    public static void Log(String message) {
        Log(message, Level.INFO);
    }
    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage("[" + ChatColor.AQUA + "JAuctionHouse" + ChatColor.WHITE + "] " + message);
    }
}
