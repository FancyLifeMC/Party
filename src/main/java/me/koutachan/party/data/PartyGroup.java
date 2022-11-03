package me.koutachan.party.data;

import me.koutachan.party.Party;
import org.bukkit.entity.Player;

import java.util.*;

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
            partyUsers = new ArrayList<>() {{
                add(PartyGroup.this);
            }};
        } else {
            getTargetPartyGroup().getPartyUsers().add(this);
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

    public void chatPlayer(Player player, String message) {
        chat(player, Party.getInstance().getString("party-chat").replaceAll("%user%", player.getName()).replaceAll("%chat%", message));
    }

    public void chat(Player player, String message) {
        getTargetPartyGroup().getPartyUsers().forEach(v -> {
            v.getPlayer().sendMessage(message.replaceAll("%user%", player.getName()));
        });
    }

    public void chatYaml(Player player, String yamlKey) {
        chat(player, Party.getInstance().getString(yamlKey));
    }

    public void chatYaml(Player player, PartyGroup group, String yamlKey) {
        chat(player, Party.getInstance().getString(yamlKey).replaceAll("%owner%", group.getTargetPartyGroup().getPlayer().getName()));
    }

    public void chatYaml(PartyGroup group, String yamlKey) {
        chat(group.getPlayer(), Party.getInstance().getString(yamlKey));
    }
}