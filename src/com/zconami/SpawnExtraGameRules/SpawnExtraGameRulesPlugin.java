package com.zconami.SpawnExtraGameRules;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.zconami.SpawnExtraGameRules.listener.TradeRestrictionListener;

public class SpawnExtraGameRulesPlugin extends JavaPlugin {

    // ===================================
    // CONSTANTS
    // ===================================

    public static final String PLUGIN_NAME = "SpawnExtraGameRules";
    public static final String SPAWN_WORLD_NAME = "spawn";

    // ===================================
    // ATTRIBUTES
    // ===================================

    private final TradeRestrictionListener tradeRestrictionListener;

    // ===================================
    // CONSTRUCTORS
    // ===================================

    public SpawnExtraGameRulesPlugin() {
        super();
        this.tradeRestrictionListener = new TradeRestrictionListener();
    }

    // ===================================
    // IMPLEMENTATION OF JavaPlugin
    // ===================================

    @Override
    public void onEnable() {
        getLogger().info("=== ENABLE START ===");
        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(tradeRestrictionListener, this);
        getLogger().info("=== ENABLE COMPLETE ===");
    }

    @Override
    public void onDisable() {
        getLogger().info("=== DISABLE START ===");
        getLogger().info("Unregistering listeners...");
        HandlerList.unregisterAll(tradeRestrictionListener);
        getLogger().info("=== DISABLE COMPLETE ===");
    }

}
