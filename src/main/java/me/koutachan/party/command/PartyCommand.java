package me.koutachan.party.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import me.koutachan.party.data.temporary.TemporaryInvite;
import me.koutachan.party.util.Conditions;
import org.bukkit.entity.Player;

@Command("party")
public class PartyCommand {
    @Subcommand("create")
    public static void onCreateParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            PartyDataManager.createParty(new PartyGroup(player, player.getUniqueId()));

            Conditions.sendMessageYaml(player, "create-party");
        } else {
            Conditions.sendMessageYaml(player, "already-joined-party");
        }
    }

    @Subcommand("delete")
    public static void onDeleteParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (group.isOwner()) {
                PartyDataManager.removeParty(group);

                Conditions.sendMessageYaml(player, "delete-party");
            } else {
                Conditions.sendMessageYaml(player, "not-party-owner");
            }
        } else {
            Conditions.sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("toggle")
    public static void onToggleChat(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            final boolean value = !group.isToggledChat();

            group.setToggledChat(value);
        } else {
            Conditions.sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("chat")
    public static void onChat(Player player, @AStringArgument String text) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            group.chat(text);
        } else {
            Conditions.sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("invite")
    public static void onInviteParty(Player player, @APlayerArgument Player target) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (group.isOwner()) {
                PartyGroup targetGroup = PartyDataManager.getGroup(target);

                if (targetGroup == null) {
                    Conditions.sendMessageYaml(player, TemporaryInvite.put(target.getUniqueId(), group) ? "already-invited-party" : "invite-party");
                } else {
                    Conditions.sendMessageYaml(player, "this-user-already-joined-party");
                }
            } else {
                Conditions.sendMessageYaml(player, "not-party-owner");
            }
        } else {
            Conditions.sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("leave")
    public static void onLeaveParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (!group.isOwner()) {
                PartyDataManager.leaveParty(group);
            } else {

            }
        } else {
            Conditions.sendMessageYaml(player, "not-joined-party");
        }
    }
}
