package net.sootmc.jauctionhouse;

import net.sootmc.jauctionhouse.Handlers.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AHCommand implements CommandExecutor {
    private final DatabaseHandler dbh;
    public AHCommand(DatabaseHandler dbh) {
        this.dbh = dbh;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if(args.length == 0) {
            if(dbh.isBlacklisted(player.getUniqueId().toString())) {
                JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You are blacklisted from using the auction house!");
                return true;
            }

            // TODO: Add ah opening method
            // Temp
            JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "This command is not yet implemented!");
            return true;
        }

        switch (args[0]) {
            case "sell":
                if(args.length < 3) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Usage: /ah sell <price> <amount>");
                    return true;
                }
                if(dbh.isBlacklisted(player.getUniqueId().toString())) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You are blacklisted from using the auction house!");
                    return true;
                }
                if(!isInteger(args[1])) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Usage: /ah sell <price> <amount>");
                    return true;
                }
                if(!isInteger(args[2])) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Usage: /ah sell <price> <amount>");
                    return true;
                }
                int price = Integer.parseInt(args[1]);
                int amount = Integer.parseInt(args[2]);
                if(price < 0) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Price must be greater than 0!");
                    return true;
                }
                if(amount < 0) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Amount must be greater than 0!");
                    return true;
                }

                ItemStack item = player.getInventory().getItemInMainHand();
                if(item == null || item.getType().isAir()) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You must be holding an item to sell it!");
                    return true;
                }

                if(item.getAmount() < amount) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You do not have enough of this item to sell!");
                    return true;
                }

                // TODO: Add item selling method - Includes success message
                // Temp
                JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "This command is not yet implemented!");
                return true;
            case "info":
                JAuctionHouse.sendPlayerMessage(player, "JAuctionHouse - Version 1.0.0");
                JAuctionHouse.sendPlayerMessage(player, "Created by JamieIsGeek");
                return true;
            case "blacklist":
                if(!player.hasPermission("jah.admin")) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if(args.length < 2) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Usage: /ah blacklist <player>");
                    return true;
                }

                if(dbh.isBlacklisted(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "This player is already blacklisted!");
                    return true;
                }

                dbh.blacklist(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());
                JAuctionHouse.sendPlayerMessage(player, ChatColor.GREEN + "Successfully blacklisted " + args[1] + "!");
            case "unblacklist":
                if(!player.hasPermission("jah.admin")) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if(args.length < 2) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "Usage: /ah unblacklist <player>");
                    return true;
                }

                if(!dbh.isBlacklisted(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) {
                    JAuctionHouse.sendPlayerMessage(player, ChatColor.RED + "This player is not blacklisted!");
                    return true;
                }

                dbh.unblacklist(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());
                JAuctionHouse.sendPlayerMessage(player, ChatColor.GREEN + "Successfully unblacklisted " + args[1] + "!");
        }
        return true;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
