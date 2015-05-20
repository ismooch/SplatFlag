package co.obam.ismooch.splatflag.objects;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * The SplatPlayer wrapper for easy
 * during game method exectution.. another
 * finger crossing moment
 */
public class SplatPlayer {

    private Player player;

    private int team;
    private DyeColor color;
    private List<Integer> armorColor = new ArrayList<Integer>();

    private SplatMap splatMap;


    private Map<Player, SplatPlayer> splatPlayers = new HashMap<Player, SplatPlayer>();

    private SplatPlayer(Player p){

        player = p;
        splatPlayers.put(p, this);

    }

    public SplatPlayer getInstanceOfPlayer(Player p){

        if(!splatPlayers.containsKey(p)){

            return new SplatPlayer(p);

        }else{

            return splatPlayers.get(p);

        }

    }

    public void setTeam(int team){

        this.team = team;

    }

    public int getTeam(){

        return team;

    }

    public void setArmorColor(List<Integer> colors){

        int Red = 0;
        int Green = 1;
        int Blue = 2;

        armorColor.add(Red, colors.get(Red));
        armorColor.add(Green, colors.get(Green));
        armorColor.add(Blue, colors.get(Blue));

    }

    public List<Integer> getArmorColor(){

        return armorColor;

    }

    public void setColor(DyeColor color){

        this.color = color;

    }

    public DyeColor getColor(){

        return color;

    }

    public Player getPlayer(){

        return player;

    }

    public SplatMap getSplatMap(){

        return splatMap;

    }

    public void setSplatMap(SplatMap sm){

        splatMap = sm;

    }




}
