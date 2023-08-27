package dev.aabstractt.wombolag.plugin;

import dev.aabstractt.wombolag.plugin.listener.AsyncPlayerPreLoginListener;
import dev.aabstractt.wombolag.plugin.profile.SpigotSender;
import dev.aabstractt.wombolag.shared.AbstractLoader;
import dev.aabstractt.wombolag.shared.command.BaseCommand;
import dev.aabstractt.wombolag.shared.manager.FactionManager;
import dev.aabstractt.wombolag.shared.manager.ProfileManager;
import dev.aabstractt.wombolag.shared.profile.Sender;
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

            return new SpigotSender(
                    floodgatePlayer.getXuid(),
                    floodgatePlayer.getCorrectUsername()
            );
        });

        this.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);

        BaseCommand factionCommand = AbstractLoader.getInstance().buildFactionsCommand();
        this.getServer().getPluginCommand("faction").setExecutor((commandSender, command, s, strings) -> {
            Sender sender = commandSender instanceof Player
                    ? ProfileManager.getInstance().wrapSender(commandSender.getName())
                    : new SpigotSender(Sender.CONSOLE_XUID, Sender.CONSOLE_NAME);
            if (sender == null) {
                commandSender.sendMessage("An error occurred while executing this command.");

                return true;
            }

            factionCommand.execute(
                    sender,
                    s,
                    strings
            );

            return true;
        });
    }
}