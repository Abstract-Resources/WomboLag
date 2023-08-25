package dev.aabstractt.wombolag.plugin;

import dev.aabstractt.wombolag.plugin.listener.AsyncPlayerPreLoginListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WomboPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
    }
}