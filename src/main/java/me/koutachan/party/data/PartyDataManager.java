package me.koutachan.party.data;

import me.koutachan.party.Party;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PartyDataManager {
    private static final Map<UUID, PartyGroup> party = new ConcurrentHashMap<>();

    public static Map<UUID, PartyGroup> getParty() {
        return party;
    }

    public static PartyGroup getGroup(Player player) {
        return party.get(player.getUniqueId());
    }

    /**
     * @param group 削除するグループ
     * @implNote
     * <li>安全にパーティーを削除します
     */
    public static void removeParty(PartyGroup group) {
        String messages = Party.getInstance().getString("disband-party");

        group.getTargetPartyGroup().getPartyUsers().forEach(v -> {
            v.getPlayer().sendMessage(messages);

            party.remove(v.getPlayer().getUniqueId());
        });
    }

    /**
     * @param group パーティーから削除するプレイヤー
     */
    public static PartyGroup leaveParty(PartyGroup group) {
        group.getTargetPartyGroup().getPartyUsers().remove(group);
        party.remove(group.getPlayer().getUniqueId(), group);
        return group;
    }

    public static PartyGroup createParty(PartyGroup group) {
        party.put(group.getPlayer().getUniqueId(), group);
        return group;
    }

    @Deprecated
    public static boolean isJoinedParty(Player player) {
        return party.containsKey(player.getUniqueId());
    }
}