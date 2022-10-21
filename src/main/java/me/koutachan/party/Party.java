package me.koutachan.party;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.arguments.TextArgument;
import me.koutachan.party.command.PartyCommand;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import me.koutachan.party.event.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Party extends JavaPlugin {

    private static Party INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;

        CommandAPI.onLoad(new CommandAPIConfig());
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        CommandAPI.onEnable(this);
        CommandAPI.registerCommand(PartyCommand.class);

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Party getInstance() {
        return INSTANCE;
    }

    public String getString(String key) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages." + key));
    }
}