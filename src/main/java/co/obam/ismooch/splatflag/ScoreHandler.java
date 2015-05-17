package co.obam.ismooch.splatflag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

/**
 * Created by troyj_000 on 5/6/2015.
 */
public class ScoreHandler implements Listener {


    public static String teamName = "hidden";
    public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();

        e.getPlayer().setScoreboard(b);


        hideTag(e.getPlayer());

    }

    private static void createTeam(Scoreboard scoreboard) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setNameTagVisibility(NameTagVisibility.NEVER);
    }

    public static void hideTag(Player player) {


        if (player.getScoreboard() != null) {
            if (player.getScoreboard().getTeam(teamName) != null) {
                player.getScoreboard().getTeam(teamName).addPlayer(player);
            } else {
                createTeam(player.getScoreboard());
                player.getScoreboard().getTeam(teamName).addPlayer(player);
            }
        } else {
            player.setScoreboard(scoreboard);
            player.getScoreboard().getTeam(teamName).addPlayer(player);
        }
    }

    public static void showTag(Player player) {
        if (player.getScoreboard() != null) {
            if (player.getScoreboard().getTeam(teamName) != null) {
                player.getScoreboard().getTeam(teamName).removeEntry(player.getName());
            }
        }
    }



    public static void startScores(){


        Bukkit.getScheduler().scheduleSyncRepeatingTask(SplatFlag.getPlugin(), new Runnable() {
            @Override
            public void run() {



                for (Player player : Bukkit.getWorld("splatflag").getPlayers()){

                    Scoreboard b = player.getScoreboard();
                    hideTag(player);
                    if(b.getObjective("splatflag") == null) {
                        final Objective obj = b.registerNewObjective("splatflag", "dummy");
                        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                        obj.setDisplayName(ChatColor.GOLD + "Splat" + ChatColor.DARK_AQUA + "Flag");
                        obj.getScore(ChatColor.YELLOW + "Kills:").setScore(0);
                        obj.getScore(ChatColor.YELLOW + "Assists:").setScore(0);
                        obj.getScore(ChatColor.GREEN + "Team Captures:").setScore(0);
                        obj.getScore(ChatColor.DARK_AQUA + "Captures:").setScore(0);
                        obj.getScore(ChatColor.YELLOW + "Killstreak:").setScore(0);
                        obj.getScore(ChatColor.DARK_AQUA + "Returns:").setScore(0);
                        obj.getScore(ChatColor.DARK_AQUA + "Protects:").setScore(0);
                        obj.getScore(ChatColor.DARK_AQUA + "Defends:").setScore(0);
                        obj.getScore(ChatColor.RED + "Deaths:").setScore(0);
                        obj.getScore(ChatColor.GOLD + "Powerups:").setScore(0);

                    }


                        final Objective obj2 = b.getObjective("splatflag");






                    if(SplatFlag.playerKills.containsKey(player)){

                        obj2.getScore(ChatColor.YELLOW + "Kills:").setScore(SplatFlag.playerKills.get(player));

                    }else{

                        obj2.getScore(ChatColor.YELLOW + "Kills:").setScore(0);
                    }

                    if(SplatFlag.playerAssists.containsKey(player)){

                        obj2.getScore(ChatColor.YELLOW + "Assists:").setScore(SplatFlag.playerAssists.get(player));

                    }else{

                        obj2.getScore(ChatColor.YELLOW + "Assists:").setScore(0);

                    }

                    if(SplatFlag.playerStreak.containsKey(player)){

                        obj2.getScore(ChatColor.YELLOW + "Killstreak:").setScore(SplatFlag.playerStreak.get(player));

                    }else{

                        obj2.getScore(ChatColor.YELLOW + "Killstreak:").setScore(0);

                    }

                    if(SplatFlag.playerReturns.containsKey(player)){

                        obj2.getScore(ChatColor.DARK_AQUA + "Returns:").setScore(SplatFlag.playerReturns.get(player));

                    }else{

                        obj2.getScore(ChatColor.DARK_AQUA + "Returns:").setScore(0);

                    }

                    if(SplatFlag.playerProtects.containsKey(player)){

                        obj2.getScore(ChatColor.DARK_AQUA + "Protects:").setScore(SplatFlag.playerProtects.get(player));

                    }else{

                        obj2.getScore(ChatColor.DARK_AQUA + "Protects:").setScore(0);

                    }

                    if(SplatFlag.playerDefends.containsKey(player)){

                        obj2.getScore(ChatColor.DARK_AQUA + "Defends:").setScore(SplatFlag.playerDefends.get(player));

                    }else{

                        obj2.getScore(ChatColor.DARK_AQUA + "Defends:").setScore(0);

                    }

                    if(SplatFlag.playerPowerups.containsKey(player)){

                        obj2.getScore(ChatColor.GOLD + "Powerups:").setScore(SplatFlag.playerPowerups.get(player));

                    }else{

                        obj2.getScore(ChatColor.GOLD + "Powerups:").setScore(0);

                    }

                    if(SplatFlag.playerDeaths.containsKey(player)){

                        obj2.getScore(ChatColor.RED + "Deaths:").setScore(SplatFlag.playerDeaths.get(player));

                    }else{

                        obj2.getScore(ChatColor.RED + "Deaths:").setScore(0);

                    }

                    if(SplatFlag.playerCaptures.containsKey(player)){

                        obj2.getScore(ChatColor.DARK_AQUA + "Captures:").setScore(SplatFlag.playerCaptures.get(player));

                    }else {

                        obj2.getScore(ChatColor.DARK_AQUA + "Captures:").setScore(0);
                    }


                    if(SplatFlag.team1.contains(player)){

                        obj2.getScore(ChatColor.GREEN + "Team Captures:").setScore(SplatFlag.team1Score);

                    }else if(SplatFlag.team2.contains(player)){

                        obj2.getScore(ChatColor.GREEN + "Team Captures:").setScore(SplatFlag.team2Score);

                    }


                }
            }
        }, 0L, 20L);

    }
}
