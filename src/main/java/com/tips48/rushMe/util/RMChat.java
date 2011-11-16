/*
 * This file is part of RushMe.
 *
 * RushMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RushMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.tips48.rushMe.util;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.teams.Team;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class RMChat {

	private RMChat() {

	}

	/**
	 * Sends the help scrren to the sender
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Commands:");
		sender.sendMessage(ChatColor.AQUA + "/RushMe - General Information");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe list - Lists all the created Arena's");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe create <Name> [GameMode] - Creates the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe delete <Name> - Deletes the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe info <Name> - Displays info about the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe start <Name> - Starts the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe stop <Name> - Stops the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe join <Name> - Joins the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe leave <Name> - Leaves the specified Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe define <Name> - Starts defining an Arena");
		sender.sendMessage(ChatColor.AQUA
				+ "/RushMe done <Name> - Indicates that you are done defining the Arena");
		sender.sendMessage(ChatColor.AQUA + "/RushMe help - Shows this dialog");
	}

	/**
	 * Tells the sender they have entered a wrong arugment
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendWrongArguments(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Wrong argument(s)");
		sender.sendMessage(ChatColor.RED
				+ "Type /RushMe help for valid commands");
	}

	/**
	 * Sends the main command to the sender
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendMainCommand(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "RushMe version "
				+ RushMe.getVersion() + "_" + RushMe.getSubVersion()
				+ " by tips48");
		sender.sendMessage(ChatColor.AQUA
				+ "Type /RushMe help for more information");
	}

	/**
	 * Tells the sender they entered too many arguments
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendTooManyArguments(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Too many argument(s)");
		sender.sendMessage(ChatColor.RED
				+ "Type /RushMe help for valid commands");
	}

	/**
	 * Tells the sender they must be a player
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendPlayerOnly(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "This is a Player only command");
	}

	/**
	 * Tells the sender they don't have permission
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 */
	public static void sendNoPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "You don't have permission to use this command");
	}

	/**
	 * Sends the specified arena's info
	 * 
	 * @param sender
	 *            {@link CommandSender} to send to
	 * @param a
	 *            {@link Arena} with info to give
	 */
	public static void sendArenaInfo(CommandSender sender, Arena a) {
		sender.sendMessage(ChatColor.RED + "Info for Arena: " + a.getName());
		sender.sendMessage(ChatColor.AQUA + "Gamemode: "
				+ (a.getGameMode().getName()));
		sender.sendMessage(ChatColor.AQUA + "Time before start: "
				+ a.getTimeBeforeStart());
		sender.sendMessage(ChatColor.AQUA + "Time left: "
				+ RMUtils.parseIntForMinute(a.getTimeLeft()));
		sender.sendMessage(ChatColor.AQUA + "Players: "
				+ RMUtils.readableSet(RMUtils.toSet(a.getPlayers())));
		Set<String> teamInfo = new HashSet<String>();
		for (Team t : a.getTeams()) {
			String info;
			if (t.getInfiniteLives()) {
				info = t.getName() + " (Infinite)";
			} else {
				info = t.getName() + " (" + t.getSpawnsLeft() + "/"
						+ t.getMaxSpawnsLeft() + ")";
			}
			teamInfo.add(info);
		}
		sender.sendMessage(ChatColor.AQUA + "Teams: "
				+ RMUtils.readableSet(teamInfo));
	}

}
