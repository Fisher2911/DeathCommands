package io.github.fisher2911.deathcommands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Set;

public class DeathListener implements Listener {

    private final DeathCommands plugin;

    public DeathListener(final DeathCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        final Set<CommandInfo> commandInfos = this.plugin.getAllowedCommands(player);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            commandInfos.forEach(commandInfo -> {
                final CommandInfo.Type type = commandInfo.getType();
                final String command = PlaceholderAPI.setPlaceholders(player,
                        commandInfo.
                                getCommand().
                        replace("%player%",
                                player.getName()));

                switch (type) {
                    case PLAYER -> player.chat("/" + command);
                    case CONSOLE -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            });
        }, 1);
    }
}
