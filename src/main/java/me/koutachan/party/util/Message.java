package me.koutachan.party.util;

import me.koutachan.party.Party;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.entity.Player;

public class Message {
    @Deprecated
    public static String getString(final boolean b, String key1, String key2) {
        return b ? Party.getInstance().getString(key1) : Party.getInstance().getString(key2);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(message.replaceAll("%user%", player.getName()));
    }

    public static void sendMessage(PartyGroup group, String yamlKey) {
        group.getPlayer().sendMessage(Party.getInstance().getString(yamlKey)
                .replaceAll("%owner%", group.getTargetPartyGroup().getPlayer().getName()));
    }

    public static String getString(PartyGroup group, String yamlKey) {
        return Party.getInstance().getString(yamlKey)
                .replaceAll("%owner%", group.getTargetPartyGroup().getPlayer().getName());
    }

    public static void sendMessageYaml(Player target, PartyGroup group, String yamlKey) {
        sendMessage(target, getString(group, yamlKey));
    }

    public static void sendMessageYaml(Player player, String yamlKey) {
        sendMessage(player, Party.getInstance().getString(yamlKey));
    }
}