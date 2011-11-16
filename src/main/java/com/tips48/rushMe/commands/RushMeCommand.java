package com.tips48.rushMe.commands;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.GameMode;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.teams.Team;
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
			} else if (args[0].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.RED + "Arena's:");
				sender.sendMessage(ChatColor.AQUA
						+ RMUtils.readableSet(GameManager.getArenaNames()));
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
					GameManager.addToGame(a, player, null);
					player.sendMessage(ChatColor.AQUA + "Joined the arena "
							+ a.getName());
					SpoutGUI.getHudOf(player).getKillFeedQueue()
							.addToQueue("1");
					SpoutGUI.getHudOf(player).getKillFeedQueue()
							.addToQueue("2");
					SpoutGUI.getHudOf(player).getKillFeedQueue()
							.addToQueue("3");
					SpoutGUI.getHudOf(player).getKillFeedQueue()
							.addToQueue("4");
					SpoutGUI.getHudOf(player).getKillFeedQueue()
							.addToQueue("5");
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
				Player player = (Player) sender;
				if (!player.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(player);
					return true;
				}
				if (GameManager.getArena(args[1]) != null) {
					player.sendMessage(ChatColor.RED
							+ "There is already an arena with that name.  Please choose another");
					return true;
				}
				Arena a = GameManager.createArena(args[1],
						GameManager.getDefaultGameMode(), player.getEntityId());
				RMChat.sendArenaInfo(player, a);
				player.sendMessage(ChatColor.AQUA
						+ "Right click twice to define the arena's boundaries");
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
				Player player = (Player) sender;
				if (!sender.hasPermission("RushMe.create")) {
					RMChat.sendNoPermission(player);
					return true;
				}
				if (GameManager.getArena(args[1]) != null) {
					player.sendMessage(ChatColor.RED
							+ "There is already an arena with that name.  Please choose another");
					return true;
				}
				GameMode g = GameManager.getGameMode(args[2]);
				if (g == null) {
					player.sendMessage(ChatColor.RED + args[2]
							+ " is not a valid GameMode.");
					player.sendMessage(ChatColor.RED
							+ "Valid GameModes: "
							+ RMUtils.readableSet(GameManager
									.getGameModeNames()));
					return true;
				}
				Arena a = GameManager.createArena(args[1], g,
						player.getEntityId());
				RMChat.sendArenaInfo(player, a);
				player.sendMessage(ChatColor.AQUA
						+ "Right click twice to define the arena's boundaries");
			} else if (args[0].equalsIgnoreCase("join")) {
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
						Team t = a.getTeam(args[2]);
						if (t == null) {
							player.sendMessage(ChatColor.RED
									+ "There is no team with the name "
									+ args[2]);
							player.sendMessage(ChatColor.RED + "Teams:");
							player.sendMessage(ChatColor.RED
									+ RMUtils.readableList(a.getTeams()));
							return true;
						}
						GameManager.addToGame(a, player, t);
						player.sendMessage(ChatColor.AQUA + "Joined the arena "
								+ a.getName());
					} else {
						player.sendMessage(ChatColor.RED
								+ "No arena with the name " + args[1]);
					}
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
