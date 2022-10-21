package me.koutachan.party.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PartyGroup {
    private final Player player;
    private final UUID targetParty;

    private List<PartyGroup> partyUsers;
    private boolean toggledChat, owner;

    /**
     * @param player プレイヤー
     * @param targetParty オーナーのUUID
     */
    public PartyGroup(Player player, UUID targetParty) {
        this.player = player;
        this.targetParty = targetParty;
        this.owner = player.getUniqueId().equals(targetParty);

        if (owner) {
            partyUsers = Arrays.asList(this);
        }
    }

    /**
     * @return オーナーのパーティーグループを取得します
     */
    public PartyGroup getTargetPartyGroup() {
        return PartyDataManager.getParty().get(targetParty);
    }

    /**
     * @return オーナーのUUIDを取得します
     */
    public UUID getTargetPartyUUID() {
        return targetParty;
    }

    /**
     * @return Partyに入っているユーザー
     * @implNote
     * <li> オーナーのみ (パフォーマンスの観点)
     * <li> {@link #getTargetPartyGroup()} を実行してから使用してください
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

    public boolean isToggledChat() {
        return toggledChat;
    }

    public void setToggledChat(boolean toggledChat) {
        this.toggledChat = toggledChat;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public void chat(String message) {
        getTargetPartyGroup().getPartyUsers().forEach(v -> {
            v.getPlayer().sendMessage(message);
        });
    }
}