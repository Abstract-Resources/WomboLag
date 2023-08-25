package dev.aabstractt.wombolag.plugin.profile;

import dev.aabstractt.wombolag.shared.profile.Sender;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

import javax.annotation.Nullable;

@RequiredArgsConstructor @Data
public final class SpigotSender implements Sender {

    private final @NonNull String id;
    private final @NonNull String name;

    public @Nullable Player toBukkitPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    @Override
    public void sendMessage(@NonNull String message) {
        Player bukkitPlayer = this.toBukkitPlayer();
        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            return;
        }

        bukkitPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void sendTitle(@NonNull String title, @Nullable String subtitle) {
        Player bukkitPlayer = this.toBukkitPlayer();
        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            return;
        }

        bukkitPlayer.sendTitle(Title.builder()
                .title(ChatColor.translateAlternateColorCodes('&', title))
                .subtitle(subtitle == null ? null : ChatColor.translateAlternateColorCodes('&', subtitle))
                .build()
        );
    }

    @Override
    public void kick(@NonNull String message) {
        Player bukkitPlayer = this.toBukkitPlayer();
        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            return;
        }

        bukkitPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', message));
    }
}