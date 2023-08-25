package dev.aabstractt.wombolag.plugin;

import dev.aabstractt.wombolag.plugin.listener.AsyncPlayerPreLoginListener;
import dev.aabstractt.wombolag.plugin.profile.SpigotSender;
import dev.aabstractt.wombolag.shared.manager.FactionManager;
import dev.aabstractt.wombolag.shared.manager.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public final class WomboPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        FactionManager.getInstance().init("uri");
        ProfileManager.getInstance().init("uri/profiles", playerName -> {
            Player bukkitPlayer = Bukkit.getPlayer(playerName);
            if (bukkitPlayer == null) {
                return null;
            }

            FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(bukkitPlayer.getUniqueId());
            if (floodgatePlayer == null) {
                return null;
            }

            floodgatePlayer.isFromProxy()

            return new SpigotSender(
                    floodgatePlayer.getXuid(),
                    floodgatePlayer.getCorrectUsername()
            );
        });

        this.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
    }
}