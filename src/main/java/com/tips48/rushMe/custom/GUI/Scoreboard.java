package com.tips48.rushMe.custom.GUI;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.data.PlayerData;
import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Scoreboard {

	private static final Map<String, Set<Widget>> widgets = new HashMap<String, Set<Widget>>();

	public static void draw(SpoutPlayer player) {

		if (!PlayerData.isActive(player)) {
			return;
		}

		Set<Widget> widgetsDrawn = new HashSet<Widget>();

		Gradient teamBackdrop = new GenericGradient();
		teamBackdrop.setTopColor(new Color(27, 76, 224, 200));
		teamBackdrop.setBottomColor(new Color(27, 76, 224, 200));
		teamBackdrop.setAnchor(WidgetAnchor.CENTER_CENTER);
		teamBackdrop.setY(-150);
		teamBackdrop.setX(-400);
		teamBackdrop.setHeight(600);
		teamBackdrop.setWidth(400);
		teamBackdrop.setPriority(RenderPriority.Highest);
		widgetsDrawn.add(teamBackdrop);

		Gradient enemyBackdrop = new GenericGradient();
		enemyBackdrop.setTopColor(new Color(27, 76, 224, 200));
		enemyBackdrop.setBottomColor(new Color(27, 76, 224, 200));
		enemyBackdrop.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyBackdrop.setY(-150);
		enemyBackdrop.setHeight(600);
		enemyBackdrop.setWidth(400);
		enemyBackdrop.setPriority(RenderPriority.Highest);
		widgetsDrawn.add(enemyBackdrop);
		// TODO another spout bug, no height > 400

		Gradient verticalLine = new GenericGradient();
		verticalLine.setTopColor(new Color(255, 255, 255, 100));
		verticalLine.setBottomColor(new Color(255, 255, 255, 200));
		verticalLine.setAnchor(WidgetAnchor.CENTER_CENTER);
		verticalLine.setHeight(615);
		verticalLine.setWidth(4);
		verticalLine.setX(-2);
		verticalLine.setY(-135);
		verticalLine.setPriority(RenderPriority.Low);
		widgetsDrawn.add(verticalLine);

		// TODO temporary solution for spout bug!

		Gradient teamHorizontalLine = new GenericGradient();
		teamHorizontalLine.setTopColor(new Color(255, 255, 255, 100));
		teamHorizontalLine.setBottomColor(new Color(255, 255, 255, 200));
		teamHorizontalLine.setAnchor(WidgetAnchor.CENTER_CENTER);
		teamHorizontalLine.setHeight(5);
		teamHorizontalLine.setWidth(400);
		teamHorizontalLine.setY(-135);
		teamHorizontalLine.setX(-400);
		widgetsDrawn.add(teamHorizontalLine);

		Gradient enemyHorizontalLine = new GenericGradient();
		enemyHorizontalLine.setTopColor(new Color(255, 255, 255, 100));
		enemyHorizontalLine.setBottomColor(new Color(255, 255, 255, 200));
		enemyHorizontalLine.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyHorizontalLine.setHeight(5);
		enemyHorizontalLine.setWidth(400);
		enemyHorizontalLine.setY(-135);
		widgetsDrawn.add(enemyHorizontalLine);

		Gradient playerTeamBackground = new GenericGradient();
		playerTeamBackground.setTopColor(new Color(0, 200, 255));
		playerTeamBackground.setBottomColor(new Color(0, 200, 255));
		playerTeamBackground.setAnchor(WidgetAnchor.CENTER_CENTER);
		playerTeamBackground.setX(-400);
		playerTeamBackground.setY(-150);
		playerTeamBackground.setWidth(400);
		playerTeamBackground.setHeight(15);
		playerTeamBackground.setPriority(RenderPriority.High);
		widgetsDrawn.add(playerTeamBackground);

		Gradient enemyTeamBackground = new GenericGradient();
		enemyTeamBackground.setTopColor(new Color(255, 0, 0));
		enemyTeamBackground.setBottomColor(new Color(255, 0, 0));
		enemyTeamBackground.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyTeamBackground.setY(-150);
		enemyTeamBackground.setWidth(400);
		enemyTeamBackground.setHeight(15);
		enemyTeamBackground.setPriority(RenderPriority.High);
		widgetsDrawn.add(enemyTeamBackground);

		/*	Team playerTeam = GameManager.getPlayersTeam(player);
				Team enemyTeam = null;
				for (Team team : GameManager.getTeams()) {
					if (team != playerTeam) {
						enemyTeam = team;
					}
				}         */

		/*	Label teamName = new GenericLabel();
				teamName.setText(playerTeam.getPrefix());
				teamName.setAnchor(WidgetAnchor.CENTER_CENTER);
				teamName.setX(-395);
				teamName.setY(-148);
				teamName.setScale(1.5F);
				teamName.setPriority(RenderPriority.Low);
				widgetsDrawn.add(teamName);

				Label enemyName = new GenericLabel();
				enemyName.setText(enemyTeam.getPrefix());
				enemyName.setAnchor(WidgetAnchor.CENTER_CENTER);
				enemyName.setY(-148);
				enemyName.setX(5);
				enemyName.setScale(1.5F);
				enemyName.setPriority(RenderPriority.Low);
				widgetsDrawn.add(enemyName);      */

		Label teamScoreLabel = new GenericLabel();
		teamScoreLabel.setText("SCORE");
		teamScoreLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		teamScoreLabel.setY(-148);
		teamScoreLabel.setX(-50);
		teamScoreLabel.setScale(1.5F);
		teamScoreLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(teamScoreLabel);

		Label enemyScoreLabel = new GenericLabel();
		enemyScoreLabel.setText("SCORE");
		enemyScoreLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyScoreLabel.setY(-148);
		enemyScoreLabel.setX(350);
		enemyScoreLabel.setScale(1.5F);
		enemyScoreLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(enemyScoreLabel);

		Label teamKillsLabel = new GenericLabel();
		teamKillsLabel.setText("K");
		teamKillsLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		teamKillsLabel.setY(-148);
		teamKillsLabel.setX(-105);
		teamKillsLabel.setScale(1.5F);
		teamKillsLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(teamKillsLabel);

		Label enemyKillsLabel = new GenericLabel();
		enemyKillsLabel.setText("K");
		enemyKillsLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyKillsLabel.setY(-148);
		enemyKillsLabel.setX(295);
		enemyKillsLabel.setScale(1.5F);
		enemyKillsLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(enemyKillsLabel);

		Label teamDeathsLabel = new GenericLabel();
		teamDeathsLabel.setText("D");
		teamDeathsLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		teamDeathsLabel.setY(-148);
		teamDeathsLabel.setX(-75);
		teamDeathsLabel.setScale(1.5F);
		teamDeathsLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(teamDeathsLabel);

		Label enemyDeathsLabel = new GenericLabel();
		enemyDeathsLabel.setText("D");
		enemyDeathsLabel.setAnchor(WidgetAnchor.CENTER_CENTER);
		enemyDeathsLabel.setY(-148);
		enemyDeathsLabel.setX(325);
		enemyDeathsLabel.setScale(1.5F);
		enemyDeathsLabel.setPriority(RenderPriority.Low);
		widgetsDrawn.add(enemyDeathsLabel);

		for (int y = -90; y <= 130; y += 20) {
			Gradient teamLine = new GenericGradient();
			teamLine.setBottomColor(new Color(0, 0, 0, 200));
			teamLine.setTopColor(new Color(0, 0, 0, 200));
			teamLine.setAnchor(WidgetAnchor.CENTER_CENTER);
			teamLine.setX(-400);
			teamLine.setY(-y);
			teamLine.setWidth(400);
			teamLine.setHeight(1);
			widgetsDrawn.add(teamLine);

			Gradient enemyLine = new GenericGradient();
			enemyLine.setBottomColor(new Color(0, 0, 0, 200));
			enemyLine.setTopColor(new Color(0, 0, 0, 200));
			enemyLine.setAnchor(WidgetAnchor.CENTER_CENTER);
			enemyLine.setY(-y);
			enemyLine.setWidth(400);
			enemyLine.setHeight(1);
			widgetsDrawn.add(enemyLine);
		}

/*		int currentY = 130;
		for (String name : playerTeam.getByScore()) {
			Label playerName = new GenericLabel();
			playerName.setAnchor(WidgetAnchor.CENTER_CENTER);
			playerName.setX(-395);
			playerName.setY(-currentY);
			playerName.setText(name);
			playerName.setScale(1.3F);
			widgetsDrawn.add(playerName);

			Label score = new GenericLabel();
			score.setAnchor(WidgetAnchor.CENTER_CENTER);
			score.setX(-50);
			score.setY(-currentY);
			score.setText(Integer.toString(PlayerData.getScore(name)));
			score.setScale(1.3F);
			widgetsDrawn.add(score);

			Label kills = new GenericLabel();
			kills.setAnchor(WidgetAnchor.CENTER_CENTER);
			kills.setX(-105);
			kills.setY(-currentY);
			kills.setText(Integer.toString(PlayerData.getKills(name)));
			kills.setScale(1.3F);
			widgetsDrawn.add(kills);

			Label deaths = new GenericLabel();
			deaths.setAnchor(WidgetAnchor.CENTER_CENTER);
			deaths.setX(-75);
			deaths.setY(-currentY);
			deaths.setText(Integer.toString(PlayerData.getDeaths(name)));
			deaths.setScale(1.3F);
			widgetsDrawn.add(deaths);

			currentY -= 20;
		}

		currentY = 111;
		for (String name : enemyTeam.getByScore()) {
			Label playerName = new GenericLabel();
			playerName.setAnchor(WidgetAnchor.CENTER_CENTER);
			playerName.setX(5);
			playerName.setY(-currentY);
			playerName.setText(name);
			playerName.setScale(1.3F);
			widgetsDrawn.add(playerName);

			Label score = new GenericLabel();
			score.setAnchor(WidgetAnchor.CENTER_CENTER);
			score.setX(350);
			score.setY(-currentY);
			score.setText(Integer.toString(PlayerData.getScore(name)));
			score.setScale(1.3F);
			widgetsDrawn.add(score);

			Label kills = new GenericLabel();
			kills.setAnchor(WidgetAnchor.CENTER_CENTER);
			kills.setX(295);
			kills.setY(-currentY);
			kills.setText(Integer.toString(PlayerData.getKills(name)));
			kills.setScale(1.3F);
			widgetsDrawn.add(kills);

			Label deaths = new GenericLabel();
			deaths.setAnchor(WidgetAnchor.CENTER_CENTER);
			deaths.setX(325);
			deaths.setY(-currentY);
			deaths.setText(Integer.toString(PlayerData.getDeaths(name)));
			deaths.setScale(1.3F);
			widgetsDrawn.add(deaths);

			currentY -= 20;
		}
		 */

		// TODO
		// Lastly: Add to screen

		for (Widget w : widgetsDrawn) {
			player.getMainScreen().attachWidget(RushMe.getInstance(), w);
		}

		widgets.put(player.getName(), widgetsDrawn);

	}

	public static boolean hasScoreboardOpen(SpoutPlayer player) {
		return widgets.containsKey(player.getName());
	}

	public static void remove(SpoutPlayer player) {
		if (!PlayerData.isActive(player)) {
			return;
		}
		if (widgets.containsKey(player.getName())) {
			for (Widget w : widgets.get(player.getName())) {
				player.getMainScreen().removeWidget(w);
			}
			widgets.remove(player.getName());
		}
	}

}
