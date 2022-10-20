package me.koutachan.party.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyDataManager {
    private static final Map<UUID, PartyGroup> party = new HashMap<>();

    public static Map<UUID, PartyGroup> getParty() {
        return party;
    }

    public static PartyGroup getGroup(Player player) {
        return party.get(player.getUniqueId());
    }

    public static void removeParty(UUID target) {
        PartyGroup group = party.remove(target);

        if (group != null) {
            group.getTargetPartyGroup().getPartyUsers().forEach(v -> {
                party.remove(v.getPlayer().getUniqueId());
            });
        }
    }

    public static boolean createParty(Player player) {
        PartyGroup group = party.get(player.getUniqueId());

        if (group == null) {
            //new party
            party.put(player.getUniqueId(), new PartyGroup(player, player.getUniqueId()));
        }

        return group == null;
    }

    public static boolean isJoinedParty(Player player) {
        return party.containsKey(player.getUniqueId());
    }
}