package me.koutachan.party.util;

import me.koutachan.party.Party;
import org.bukkit.entity.Player;

public class Conditions {
    @Deprecated
    public static String getString(final boolean b, String key1, String key2) {
        return b ? Party.getInstance().getString(key1) : Party.getInstance().getString(key2);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(message.replaceAll("%user%", player.getName()));
    }

    public static void sendMessageYaml(Player player, String yamlKey) {
        sendMessage(player, Party.getInstance().getString(yamlKey));
    }
}