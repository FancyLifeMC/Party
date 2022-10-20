package me.koutachan.party;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.TextArgument;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.plugin.java.JavaPlugin;

public final class Party extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new CommandAPICommand("party")
                .withSubcommand(new CommandAPICommand("create")
                        .executesPlayer((player, args) -> {
                            if (!PartyDataManager.isJoinedParty(player)) {

                            } else {

                            }
                        }))
                .withSubcommand(new CommandAPICommand("delete")
                        .executesPlayer((player, args) -> {
                            PartyGroup group = PartyDataManager.getGroup(player);

                            if (group != null) {
                                if (group.isOwner()) {
                                    PartyDataManager.removeParty(player.getUniqueId());
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
                            if (args.length != 0 && PartyDataManager.isJoinedParty(player)) {
                                String chat = String.join("", (String[]) args);

                                PartyDataManager.getParty().get(player.getUniqueId()).getPartyUsers().forEach(v -> v.getPlayer().sendMessage(chat));
                            }
                        }))
                .register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}