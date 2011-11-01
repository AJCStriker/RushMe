package com.tips48.rushMe.commands;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.GameMode;
import com.tips48.rushMe.util.RMChat;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RushMeCommand implements CommandExecutor {

	/**
	 * Called when a command called RushMe is sent
	 * @param sender {@link CommandSender} Command sender object
	 * @param cmd {@link Command} Command object
	 * @param commandLabel Name of command sent
	 * @param args Command arguments
	 * @return if command was used
	 */
	public boolean onCommand(CommandSender sender, Command cmd,
	                         String commandLabel, String[] args) {
		if (args.length == 0) {
			RMChat.sendMainCommand(sender);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				RMChat.sendHelp(sender);
			} else {
				RMChat.sendWrongArguments(sender);
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("join")) {
				if (!(sender instanceof Player)) {
					RMChat.sendPlayerOnly(sender);
					return true;
				}
				Player player = (Player) sender;
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					GameManager.addToGame(a, player);
					player.sendMessage(ChatColor.AQUA + "Joined the arena " + a.getName());
				} else {
					player.sendMessage(ChatColor.RED + "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("create")) {
				// TODO add permissions to plugin.yml
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				Arena a = GameManager.createArena(args[1], GameMode.RUSH, 600);
				RMChat.sendArenaInfo(sender, a);
			} else if (args[0].equalsIgnoreCase("delete")) {
				if (!(sender.hasPermission("RushMe.delete"))) {
					RMChat.sendNoPermission(sender);
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					GameManager.removeArena(a);
				} else {
					sender.sendMessage(ChatColor.RED + "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					RMChat.sendArenaInfo(sender, a);
				} else {
					sender.sendMessage(ChatColor.RED + "No arena with the name " + args[1]);
				}
			} else {
				RMChat.sendWrongArguments(sender);
			}
			return true;
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("create")) {
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				try {
					Arena a = GameManager.createArena(args[1], GameMode.RUSH, Integer.parseInt(args[2]));
					RMChat.sendArenaInfo(sender, a);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + args[2] + " is not a valid integer.");
				}
			} else {
				RMChat.sendWrongArguments(sender);
			}
		} else if (args.length == 4) {
			if (args[0].equals("create")) {
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				GameMode g = GameMode.valueOf(args[3]);
				if (g == null) {
					sender.sendMessage(ChatColor.RED + args[3] + " is not a valid Game mode.");
					sender.sendMessage(ChatColor.RED + "Game modes: " + RMUtils.readableArray(GameMode.values()));
					return true;
				}
				try {
					Arena a = GameManager.createArena(args[1], g, Integer.parseInt(args[2]));
					RMChat.sendArenaInfo(sender, a);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + args[2] + " is not a valid integer.");
				}
			} else {
				RMChat.sendWrongArguments(sender);
			}
		} else {
			RMChat.sendTooManyArguments(sender);
		}
		return true;
	}

}
