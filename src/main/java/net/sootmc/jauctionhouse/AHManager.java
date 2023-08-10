package net.sootmc.jauctionhouse;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        SGMenu auctionHouse = new SGMenu(plugin, plugin.getSpigui(), ChatColor.AQUA + "Auction House", 6, player.getUniqueId().toString());
        SGButton nextPage = new SGButton(
                new ItemBuilder(nextPageItem).build()
        );
        SGButton previousPage = new SGButton(
                new ItemBuilder(previousPageItem).build()
        );

        auctionHouse.setButton(0, 3, nextPage);
        auctionHouse.setButton(0, 5, previousPage);

        player.openInventory(auctionHouse.getInventory());
    }
}
