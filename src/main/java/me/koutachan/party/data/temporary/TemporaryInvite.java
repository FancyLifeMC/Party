package me.koutachan.party.data.temporary;

import me.koutachan.party.Party;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TemporaryInvite {
    //TODO: List<PartyGroup> vs Map<UUID, PartyGroup>
    private static final Map<UUID, Map<UUID, PartyGroup>> temporaryInvite = new ConcurrentHashMap<>();

    public static Map<UUID, Map<UUID, PartyGroup>> getTemporaryInvite() {
        return temporaryInvite;
    }

    public static boolean put(UUID user, PartyGroup group) {
        final boolean containsKey = temporaryInvite.containsKey(user);

        if (!containsKey) {
            temporaryInvite.put(user, Collections.singletonMap(group.getTargetPartyUUID(), group));

            Bukkit.getScheduler().runTaskTimerAsynchronously(Party.getInstance(), () -> {
                temporaryInvite.remove(user);
            },0, Party.getInstance().getInt("settings.invite-delete-ticks"));
        }

        return containsKey;
    }
}