package me.koutachan.party.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import me.koutachan.party.data.temporary.TemporaryInvite;
import me.koutachan.party.util.Message;
import org.bukkit.entity.Player;

@Command("party")
public class PartyCommand extends Message {
    @Subcommand("create")
    public static void onCreateParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            PartyDataManager.createParty(new PartyGroup(player, player.getUniqueId()));

            sendMessageYaml(player, "create-party");
        } else {
            sendMessage(group, "already-joined-party");
        }
    }

    @Subcommand("delete")
    public static void onDeleteParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            sendMessageYaml(player, "not-joined-party");
            return;
        }

        if (group.isOwner()) {
            PartyDataManager.removeParty(group);

            sendMessageYaml(player, "delete-party");
        } else {
            sendMessageYaml(player, "not-party-owner");
        }
    }

    @Subcommand("join")
    public static void onPartyJoin(Player player, @APlayerArgument Player target) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            sendMessage(group, "already-joined-party");
            return;
        }

        PartyGroup groupOfInvited = TemporaryInvite.remove(player.getUniqueId(), target.getUniqueId());

        if (groupOfInvited != null) {
            sendMessage(PartyDataManager.createParty(new PartyGroup(player, target.getUniqueId())), "joined-party");
        } else {
            sendMessageYaml(player, "not-founded-party");
        }
    }

    @Subcommand("toggle")
    public static void onToggleChat(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            final boolean value = !group.isToggledChat();

            group.setToggledChat(value);
            sendMessageYaml(player, value ? "toggle-chat-on" : "toggle-chat-off");
        } else {
            sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("chat")
    public static void onChat(Player player, @AStringArgument String text) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group != null) {
            group.chatPlayer(player, text);
        } else {
            sendMessageYaml(player, "not-joined-party");
        }
    }

    @Subcommand("invite")
    public static void onInviteParty(Player player, @APlayerArgument Player target) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            sendMessageYaml(player, "not-joined-party");
            return;
        }

        if (!group.isOwner()) {
            sendMessageYaml(player, "not-party-owner");
            return;
        }

        PartyGroup targetGroup = PartyDataManager.getGroup(target);

        if (targetGroup == null){
            if (TemporaryInvite.put(target.getUniqueId(), group))  {
                sendMessage(group,  "already-invited-party");
                return;
            }
            sendMessageYaml(player, target, "invite-party");
            sendMessageYaml(target, player, "invite-party-target");
        } else {
            sendMessageYaml(player, "this-user-already-joined-party");
        }
    }

    @Subcommand("leave")
    public static void onLeaveParty(Player player) {
        PartyGroup group = PartyDataManager.getGroup(player);

        if (group == null) {
            sendMessageYaml(player, "not-joined-party");
            return;
        }

        if (group.isOwner()) {
            sendMessageYaml(player, "cannot-leave-party-owner");
            return;
        }

        group.chatYaml(PartyDataManager.leaveParty(group), "leave-party-everyone");
        sendMessageYaml(player, "leave-party");
    }
}