package dev.aabstractt.wombolag.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerAsyncPreLoginEvent;
import dev.aabstractt.wombolag.manager.ProfileManager;
import lombok.NonNull;

public final class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(@NonNull PlayerAsyncPreLoginEvent ev) {
        ProfileManager.getInstance().load(ev.getXuid(), ev.getName());
    }
}