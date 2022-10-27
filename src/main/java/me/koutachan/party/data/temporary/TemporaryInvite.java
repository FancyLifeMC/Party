package me.koutachan.party.data.temporary;

import me.koutachan.party.Party;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TemporaryInvite {
    //TODO: List<PartyGroup> vs Map<UUID, PartyGroup>
    private static final Map<UUID, Map<UUID, PartyGroup>> temporaryInvite = new ConcurrentHashMap<>();

    public static Map<UUID, Map<UUID, PartyGroup>> getTemporaryInvite() {
        return temporaryInvite;
    }

    public static PartyGroup get(UUID user, UUID target) {
        Map<UUID,PartyGroup> invites = temporaryInvite.get(user);

        if (invites != null) {
            return invites.get(target);
        }

        return null;
    }

    public static boolean put(UUID user, PartyGroup group) {
        final Map<UUID, PartyGroup> newInvites = temporaryInvite.containsKey(user)
                ? temporaryInvite.get(user) : new HashMap<>();

        final boolean containsKey = newInvites.containsKey(group.getTargetPartyUUID());

        if (!containsKey) {
            newInvites.put(user, group);

            Bukkit.getScheduler().runTaskTimerAsynchronously(Party.getInstance(), () -> {
                remove(user, group);
            }, 0, Party.getInstance().getInt("settings.invite-delete-ticks"));

            //Accepted New Invite
            temporaryInvite.put(user, newInvites);
        }

        return containsKey;
    }

    public static PartyGroup remove(UUID user, PartyGroup group) {
        return remove(user, group.getTargetPartyUUID());
    }

    public static PartyGroup remove(UUID user, UUID uuid) {
        Map<UUID, PartyGroup> invites = temporaryInvite.get(user);

        if (invites != null) {
            return invites.remove(uuid);
        }
        return null;
    }
}