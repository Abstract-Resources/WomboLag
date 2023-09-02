package dev.aabstractt.wombolag.plugin.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class PlayerCommandPreProcessListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent ev) {
        Player bukkitPlayer = ev.getPlayer();
        if (bukkitPlayer.hasPermission("wombolag.admin")) {
            return;
        }

        String[] args = ev.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/f") || args[0].equalsIgnoreCase("/faction")) {
            return;
        }

        ev.setCancelled(true);

        bukkitPlayer.sendMessage(ChatColor.RED + "You cannot use this!");
    }
}