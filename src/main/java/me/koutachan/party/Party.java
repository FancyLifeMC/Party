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
import org.bukkit.plugin.java.JavaPlugin;

public final class Party extends JavaPlugin {

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(false));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);
        CommandAPI.registerCommand(PartyCommand.class);

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}