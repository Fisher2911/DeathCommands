package io.github.fisher2911.deathcommands;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeathCommands extends JavaPlugin {

    private final Multimap<String, CommandInfo> deathMap = Multimaps.
            newSetMultimap(new HashMap<>(), HashSet::new);

    @Override
    public void onEnable() {
        this.loadDeathCommands();
        this.getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    private void loadDeathCommands() {
        this.saveDefaultConfig();
        final FileConfiguration config = this.getConfig();

        final ConfigurationSection section = config.getConfigurationSection("commands");

        if (section == null) {
            this.getLogger().warning("No commands section found.");
            return;
        }

        for (final String key : section.getKeys(false)) {

            final String command = section.getString(key + ".command");
            final String typeString = section.getString(key + ".type");

            CommandInfo.Type type;

            try {
                if (typeString == null) {
                    throw new IllegalArgumentException();
                }
                type = CommandInfo.Type.valueOf(typeString);
            } catch (final IllegalArgumentException exception) {
                type = CommandInfo.Type.CONSOLE;
            }

            final CommandInfo commandInfo = new CommandInfo(command, type);
            this.deathMap.put(key, commandInfo);
        }
    }

    public Set<CommandInfo> getAllowedCommands(final CommandSender sender) {
        final Set<CommandInfo> commandInfos = new HashSet<>();
        for (final Map.Entry<String, CommandInfo> entry : this.deathMap.entries()) {
            if (sender.hasPermission(entry.getKey())) {
                commandInfos.add(entry.getValue());
            }
        }
        return commandInfos;
    }

}
