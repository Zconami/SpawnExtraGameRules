package com.zconami.SpawnExtraGameRules;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.zconami.SpawnExtraGameRules.listener.MobDropListener;

public class SpawnExtraGameRulesPlugin extends JavaPlugin {

    // ===================================
    // CONSTANTS
    // ===================================

    public static final String PLUGIN_NAME = "SpawnExtraGameRules";

    // ===================================
    // ATTRIBUTES
    // ===================================

    private final MobDropListener mobDropListener;

    // ===================================
    // CONSTANTS
    // ===================================

    public SpawnExtraGameRulesPlugin() {
        super();
        this.mobDropListener = new MobDropListener();
    }

    // ===================================
    // IMPLEMENTATION OF JavaPlugin
    // ===================================

    @Override
    public void onEnable() {
        getLogger().info("=== ENABLE START ===");
        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(mobDropListener, this);
        getLogger().info("=== ENABLE COMPLETE ===");
    }

    @Override
    public void onDisable() {
        getLogger().info("=== DISABLE START ===");
        getLogger().info("Unregistering listeners...");
        HandlerList.unregisterAll(mobDropListener);
        getLogger().info("=== DISABLE COMPLETE ===");
    }

}
