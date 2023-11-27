package de.marc.ganglife.playerEvents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class TabAPI {

    private static Scoreboard sb;


    public static void setScoreboard() {

        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("00Admin");
        sb.registerNewTeam("01default");

        sb.getTeam("00Admin").setPrefix("§c0x ");
        sb.getTeam("01default").setPrefix("§7");

        for(Player all : Bukkit.getOnlinePlayers()) {
            setTeams(all);
            setHeaderFooter(all);
        }
    }

    private static void setTeams(Player p) {
        String team = "";

        if(p.hasPermission("system.admin")) {
            team = "00Admin";
        } else {
            team = "01default";
        }

        sb.getTeam(team).addPlayer(p);
        p.setScoreboard(sb);
    }

    public static void setHeaderFooter(Player player) {
        player.setPlayerListHeaderFooter("\n §b0§3x-§bG§3ang§bL§3ife§7.§6§lDE \n \n §7Dein Reallife & Roleplay Server",
                "\n §6" + Bukkit.getOnlinePlayers().size() + "§7/§6" + Bukkit.getMaxPlayers() + " §7Spieler\n");
    }
}
