package com.tips48.rushMe.commands;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RushMeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd,
                             String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "RushMe version "
                    + RushMe.getVersion() + "_" + RushMe.getSubVersion()
                    + " by tips48");
            sender.sendMessage(ChatColor.AQUA
                    + "Type /RushMe help for more information");
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.RED + "Commands:");
                sender.sendMessage(ChatColor.AQUA
                        + "/RushMe - General Information");
                sender.sendMessage(ChatColor.AQUA
                        + "/RushMe toggle - Toggles activity in RushMe");
                sender.sendMessage(ChatColor.AQUA
                        + "/RushMe help - Shows this dialog");
                return true;
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "You must be a player");
                    return true;
                }
                Player player = (Player) sender;
                if (PlayerData.isActive(player)) {
                    PlayerData.setActive(player, false);
                    player.sendMessage(ChatColor.AQUA + "RushMe toggled off");
                    return true;
                }
                PlayerData.setActive(player, true);
                player.sendMessage(ChatColor.AQUA + "RushMe toggled on");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Wrong argument(s)");
                sender.sendMessage(ChatColor.RED
                        + "Type /RushMe help for valid commands");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Too many argument(s)");
            sender.sendMessage(ChatColor.RED
                    + "Type /RushMe help for valid commands");
            return true;
        }
    }

}
