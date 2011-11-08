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
	 * 
	 * @param sender
	 *            {@link CommandSender} Command sender object
	 * @param cmd
	 *            {@link Command} Command object
	 * @param commandLabel
	 *            Name of command sent
	 * @param args
	 *            Command arguments
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
				if (GameManager.inGame(player)) {
					player.sendMessage(ChatColor.RED
							+ "You are already in the arena: "
							+ GameManager.getPlayerArena(player).getName());
					player.sendMessage(ChatColor.RED
							+ "To leave use the command /RushMe leave "
							+ GameManager.getPlayerArena(player).getName());
					return true;
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					GameManager.addToGame(a, player);
					player.sendMessage(ChatColor.AQUA + "Joined the arena "
							+ a.getName());
				} else {
					player.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (!(sender instanceof Player)) {
					RMChat.sendPlayerOnly(sender);
					return true;
				}
				Player player = (Player) sender;
				if (!GameManager.inGame(player)) {
					player.sendMessage(ChatColor.RED
							+ "You aren't in an arena!");
					return true;
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					GameManager.removeFromGame(a, player);
					player.sendMessage(ChatColor.AQUA + "Left the arena "
							+ a.getName());
				} else {
					player.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("create")) {
				if (!(sender instanceof Player)) {
					RMChat.sendPlayerOnly(sender);
					return true;
				}
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				Arena a = GameManager.createArena(args[1],
						GameManager.getDefaultGameMode());
				RMChat.sendArenaInfo(sender, a);
				sender.sendMessage(ChatColor.AQUA
						+ "Right click twice to define the arena's boundaries");
				Arena.addArena(sender.getName(), a);
			} else if (args[0].equalsIgnoreCase("delete")) {
				if (!(sender.hasPermission("RushMe.delete"))) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					GameManager.removeArena(a);
					sender.sendMessage(ChatColor.AQUA + args[1] + " deleted.");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					RMChat.sendArenaInfo(sender, a);
				} else {
					sender.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("start")) {
				if (!(sender.hasPermission("RushMe.start"))) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					a.start();
					sender.sendMessage(ChatColor.AQUA + a.getName()
							+ " started");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (!(sender.hasPermission("RushMe.stop"))) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				Arena a = GameManager.getArena(args[1]);
				if (a != null) {
					a.stop();
					sender.sendMessage(ChatColor.AQUA + a.getName()
							+ " stopped");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "No arena with the name " + args[1]);
				}
			} else {
				RMChat.sendWrongArguments(sender);
			}
			return true;
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("create")) {
				if (!(sender instanceof Player)) {
					RMChat.sendPlayerOnly(sender);
					return true;
				}
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(sender);
					return true;
				}
				GameMode g = GameManager.getGameMode(args[2]);
				if (g == null) {
					sender.sendMessage(ChatColor.RED + args[2]
							+ " is not a valid GameMode.");
					sender.sendMessage(ChatColor.RED
							+ "Valid GameModes: "
							+ RMUtils.readableSet(GameManager
									.getGameModeNames()));
					return true;
				}
				Arena a = GameManager.createArena(args[1], g);
				RMChat.sendArenaInfo(sender, a);
				sender.sendMessage(ChatColor.AQUA
						+ "Right click twice to define the arena's boundaries");
				Arena.addArena(sender.getName(), a);
			} else {
				RMChat.sendWrongArguments(sender);
			}
		} else {
			RMChat.sendTooManyArguments(sender);
		}
		return true;
	}

}
