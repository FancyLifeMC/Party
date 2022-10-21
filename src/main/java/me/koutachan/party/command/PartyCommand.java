package me.koutachan.party.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.koutachan.party.Party;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.entity.Player;

@Command("party")
public class PartyCommand {
    @Subcommand("create")
    public static void onCreateParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            PartyDataManager.createParty(new PartyGroup(player, player.getUniqueId()));

            player.sendMessage(Party.getInstance().getString("create-party"));
        } else {
            player.sendMessage(Party.getInstance().getString("already-joined-party").replaceAll("%user%", group.getTargetPartyGroup().getPlayer().getName()));
        }
    }

    @Subcommand("delete")
    public static void onDeleteParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (group.isOwner()) {
                PartyDataManager.removeParty(group);

                player.sendMessage(Party.getInstance().getString("delete-party"));
            } else {
                player.sendMessage(Party.getInstance().getString("not-party-owner"));
            }
        } else {
            player.sendMessage(Party.getInstance().getString("not-joined-party"));
        }
    }

    @Subcommand("toggle")
    public static void onToggleChat(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            final boolean value = !group.isToggledChat();

            group.setToggledChat(value);
        } else {
            player.sendMessage(Party.getInstance().getString("not-joined-party"));
        }
    }

    @Subcommand("chat")
    public static void onChat(Player player, @AStringArgument String text) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            group.chat(text);
        } else {
            player.sendMessage(Party.getInstance().getString("not-joined-party"));
        }
    }

    @Subcommand("invite")
    public static void onInviteParty(Player player, @APlayerArgument Player target) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            if (group.isOwner()) {

            } else {
                player.sendMessage(Party.getInstance().getString("not-party-owner"));
            }
        } else {
            player.sendMessage(Party.getInstance().getString("not-joined-party"));
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
            player.sendMessage(Party.getInstance().getString("not-joined-party"));
        }
    }
}
