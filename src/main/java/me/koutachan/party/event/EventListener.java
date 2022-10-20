package me.koutachan.party.event;

import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        PartyGroup group = PartyDataManager.getGroup(event.getPlayer());

        if (group != null && group.isToggledChat()) {
            group.chat(event.getMessage());
        }
    }
}