package me.koutachan.party.commands;

import me.koutachan.party.data.PartyDataManager;
import me.koutachan.party.data.PartyGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO: 個々にあるメッセージは後で全てカスタマイズ出来るようにする
public class PartyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //プレイヤー以外からは反応させないようにする。
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("create")) {
                if (PartyDataManager.createParty(player)) {
                    sender.sendMessage("[Party] パーティーを作成しました");
                } else {
                    sender.sendMessage("[Party] あなたは既にパーティーに参加していませんか？");
                }
                //Todo: Add Message
            } else if (args[0].equalsIgnoreCase("delete")) {
                PartyGroup group = PartyDataManager.getParty().get(player.getUniqueId());

                if (group == null) {
                    sender.sendMessage("[Party] パーティーは作られていません。");
                } else if (group.getTargetPartyUUID().equals(player.getUniqueId())) {
                    //TODO: Add Message
                    if (PartyDataManager.removeParty(player.getUniqueId())) {
                        sender.sendMessage("[Party] パーティーを削除しました");
                    } else {
                        sender.sendMessage("[Party] パーティーを削除できませんでした");
                    }
                } else {
                    sender.sendMessage("[Party] あなたはこのパーティーの削除を行えません。");
                }
            }
        } else {
            //TODO: implement help
        }

        return true;
    }
}
