package dev.aabstractt.wombolag.plugin.listener;

import dev.aabstractt.wombolag.shared.manager.ProfileManager;
import lombok.NonNull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public final class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(@NonNull AsyncPlayerPreLoginEvent ev) {
        FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(ev.getUniqueId());
        if (floodgatePlayer == null) {
            ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "You must be a Bedrock player to join this server.");

            return;
        }

        ProfileManager.getInstance().load(floodgatePlayer.getXuid(), floodgatePlayer.getCorrectUsername());
    }
}