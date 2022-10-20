package me.koutachan.party.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyGroup {
    private final Player player;
    private final UUID targetParty;

    private final List<PartyGroup> partyUsers = new ArrayList<>();

    public PartyGroup(Player player, UUID targetParty) {
        this.player = player;
        this.targetParty = targetParty;
    }

    public PartyGroup getTargetPartyGroup() {
        return PartyDataManager.getParty().get(targetParty);
    }

    public UUID getTargetPartyUUID() {
        return targetParty;
    }

    /**
     * @return Partyに入っているユーザーのUUID
     * @implNote
     * <li> オーナーのみ (パフォーマンスの観点)
     * <li> {@link #getTargetPartyGroup()} を使用してください
     */
    public List<PartyGroup> getPartyUsers() {
        return partyUsers;
    }

    /**
     * @return 自身のプレイヤーを返します
     */
    public Player getPlayer() {
        return player;
    }
}