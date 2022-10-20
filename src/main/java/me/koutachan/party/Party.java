package me.koutachan.party;

import me.koutachan.party.commands.PartyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Party extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("party").setExecutor(new PartyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
