package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class Disconnect implements Listener {


    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){

        SplatPlayer splatPlayer = SplatPlayer.getInstanceOfPlayer(e.getPlayer());

        splatPlayer.getLobby().removePlayer(e.getPlayer());

        splatPlayer.getLobby().setPlayerCount(splatPlayer.getLobby().getPlayerCount() - 1);

        if(splatPlayer.getSplatMap() != null){

            if(splatPlayer.getTeam() == 1){

                splatPlayer.getSplatMap().removeFromTeam1(e.getPlayer());

            }else if(splatPlayer.getTeam() == 2){

                splatPlayer.getSplatMap().removeFromTeam2(e.getPlayer());

            }

        }

        splatPlayer.setLobby(null);

        SplatPlayer.removePlayer(e.getPlayer());




    }

}
