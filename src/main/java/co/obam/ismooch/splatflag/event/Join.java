package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class Join implements Listener {


    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e){

        e.getPlayer().teleport(SplatFlag.spawn);

    }
}
