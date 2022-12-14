package me.koutachan.party;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import me.koutachan.party.command.PartyCommand;
import me.koutachan.party.event.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
        CommandAPI.onDisable();
        // Plugin shutdown logic
    }

    public static Party getInstance() {
        return INSTANCE;
    }

    public String getString(String key) {
        Object object = getConfig().get("messages." + key);

        return ChatColor.translateAlternateColorCodes('&', object instanceof String ? (String) object : String.join("\n", (List<String>) object));
    }

    public int getInt(String key) {
        return getConfig().getInt(key);
    }
}