package me.koutachan.party.event;

import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        PartyGroup group = PartyDataManager.getGroup(event.getPlayer());

        if (group != null) {
            group.setPlayer(event.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChatEvent(AsyncPlayerChatEvent event) {
        PartyGroup group = PartyDataManager.getGroup(event.getPlayer());

        if (group != null && group.isToggledChat()) {
            group.chatPlayer(event.getPlayer(), event.getMessage());

            event.setCancelled(true);
        }
    }

    //Hard Coded
    /*@EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        PartyGroup group = PartyDataManager.getGroup(event.getPlayer());

        if (group != null) {
            PartyDataManager.leaveParty(group);
        }
    }*/
}