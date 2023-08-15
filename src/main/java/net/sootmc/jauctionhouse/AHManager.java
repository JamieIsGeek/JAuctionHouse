package net.sootmc.jauctionhouse;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AHManager {
    private final JAuctionHouse plugin;
    private final ItemStack nextPageItem = new ItemStack(Material.GREEN_WOOL, 1);
    private final ItemStack previousPageItem = new ItemStack(Material.RED_WOOL, 1);
    public AHManager(JAuctionHouse plugin) {
        this.plugin = plugin;

        nextPageItem.getItemMeta().setDisplayName(ChatColor.GREEN + "Next Page");
        previousPageItem.getItemMeta().setDisplayName(ChatColor.RED + "Previous Page");
    }
    public void openAuctionHouse(Player player) {
        ChestGui auctionHouse = new ChestGui(6, ChatColor.AQUA + "Auction House");

        auctionHouse.show(player);
    }
}
