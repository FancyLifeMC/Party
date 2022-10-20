package me.koutachan.party;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.arguments.TextArgument;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.plugin.java.JavaPlugin;

public final class Party extends JavaPlugin {

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);

        // Plugin startup logic
        new CommandAPICommand("party")
                .withSubcommand(new CommandAPICommand("create")
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group == null) {
                                PartyDataManager.createParty(player);
                            } else {

                            }
                        }))
                .withSubcommand(new CommandAPICommand("delete")
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group != null) {
                                if (group.isOwner()) {
                                    PartyDataManager.removeParty(player);
                                } else {

                                }
                            } else {

                            }
                        }))
                .withSubcommand(new CommandAPICommand("toggle")
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group != null) {
                                final boolean value = !group.isToggledChat();

                                group.setToggledChat(value);
                            }
                        }))
                .withSubcommand(new CommandAPICommand("chat")
                        .withArguments(new TextArgument("text"))
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group != null) {
                                group.chat((String) args[0]);
                            }
                        }))
                .withSubcommand(new CommandAPICommand("leave")
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group != null) {
                                if (!group.isOwner()) {
                                    PartyDataManager.leaveParty(group);
                                } else {

                                }
                            } else {
                                //todo:; add messages
                            }
                        }))
                .register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}