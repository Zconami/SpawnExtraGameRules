package com.zconami.SpawnExtraGameRules.listener;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.zconami.SpawnExtraGameRules.SpawnExtraGameRulesPlugin;

import net.milkbowl.vault.item.Items;

public class TradeRestrictionListener implements Listener {

    // ===================================
    // CONSTANTS
    // ===================================

    private static final String UNTRADABLE_TEXT = "§c[UNTRADABLE]";
    private static final List<String> UNTRADABLE_LORE = Lists.newArrayList("You can't trade this because",
            "that would be pretty broken.");

    // ===================================
    // PUBLIC METHODS
    // ===================================

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        final LivingEntity deadEntity = event.getEntity();
        final String worldName = deadEntity.getLocation().getWorld().getName();
        if (deadEntity instanceof Animals && worldName.equals(SpawnExtraGameRulesPlugin.SPAWN_WORLD_NAME)) {
            event.setDroppedExp(0);
            event.getDrops().stream().filter(this::shouldRestrictTrade).forEach(this::setRestrictTrade);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Inventory clickedInventory = event.getClickedInventory();
        final ItemStack selectedItem = event.getCursor();
        if (clickedInventory instanceof MerchantInventory && isTradeRestricted(selectedItem)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        final ItemStack sourceItem = event.getSource();
        if (isTradeRestricted(sourceItem)) {
            setRestrictTrade(event.getResult());
        }
    }

    // ===================================
    // PRIVATE METHODS
    // ===================================

    private boolean isTradeRestricted(ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }

        final List<String> lore = itemMeta.getLore();
        if (lore.size() < 2 || lore == null) {
            return false;
        }

        return itemMeta.getDisplayName().contains(UNTRADABLE_TEXT) || UNTRADABLE_LORE.stream().anyMatch(lore::contains);
    }

    private boolean shouldRestrictTrade(ItemStack itemStack) {
        if (itemStack.getType() == Material.WOOL) {
            return false;
        }
        return true;
    }

    private void setRestrictTrade(ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final String defaultName = Items.itemByType(itemStack.getType()).getName();
        itemMeta.setDisplayName(defaultName + " " + UNTRADABLE_TEXT);
        itemMeta.setLore(UNTRADABLE_LORE);
        itemStack.setItemMeta(itemMeta);
    }

}
