package me.koutachan.party.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.entity.Player;

@Command("party")
public class PartyCommand {
    @Subcommand("create")
    public static void onCreateParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            PartyDataManager.createParty(player);
        } else {

        }
    }

    @Subcommand("delete")
    public static void onDeleteParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (group.isOwner()) {
                PartyDataManager.removeParty(player);
            } else {

            }
        } else {

        }
    }

    @Subcommand("toggle")
    public static void onToggleChat(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            final boolean value = !group.isToggledChat();

            group.setToggledChat(value);
        }
    }

    @Subcommand("chat")
    public static void onChat(Player player, @AStringArgument String text) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            group.chat(text);
        }
    }

    @Subcommand("leave")
    public static void onLeave(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (!group.isOwner()) {
                PartyDataManager.leaveParty(group);
            } else {

            }
        } else {
            //todo:; add messages
        }
    }
}
