package co.obam.ismooch.splatflag.objects;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import java.util.*;

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
    private int kills;
    private int assists;
    private int killStreak;
    private int returns;
    private int deaths;
    private int powerups;
    private int defends;
    private int protects;
    private int captures;
    private boolean shield;
    private SplatLobby lobby;
    private boolean hit;
    private UUID assistOn;

    private SplatMap splatMap;


    public static Map<Player, SplatPlayer> splatPlayers = new HashMap<Player, SplatPlayer>();

    private SplatPlayer(Player p) {

        player = p;
        splatPlayers.put(p, this);

    }

    public static void removePlayer(Player p){

        if(splatPlayers.containsKey(p)){

            splatPlayers.remove(p);

        }

    }

    public static SplatPlayer getInstanceOfPlayer(Player p) {

        if (!splatPlayers.containsKey(p)) {

            return new SplatPlayer(p);

        } else {

            return splatPlayers.get(p);

        }

    }

    public void setTeam(int team) {

        this.team = team;

    }

    public int getTeam() {

        return team;

    }

    public void setArmorColor(List<Integer> colors) {

        int Red = 0;
        int Green = 1;
        int Blue = 2;

        armorColor.add(Red, colors.get(Red));
        armorColor.add(Green, colors.get(Green));
        armorColor.add(Blue, colors.get(Blue));

    }

    public List<Integer> getArmorColor() {

        return armorColor;

    }

    public void setColor(DyeColor color) {

        this.color = color;

    }

    public DyeColor getColor() {

        return color;

    }

    public Player getPlayer() {

        return player;

    }

    public SplatMap getSplatMap() {

        return splatMap;

    }

    public void setSplatMap(SplatMap sm) {

        splatMap = sm;

    }

    public void setKills(int i) {

        kills = i;

    }

    public void setAssists(int i) {

        assists = i;

    }

    public void setReturns(int i) {

        returns = i;

    }

    public void setKillStreak(int i) {

        killStreak = i;

    }

    public void setDeaths(int i) {

        deaths = i;

    }

    public void setPowerups(int i) {

        powerups = i;

    }

    public void setDefends(int i) {

        defends = i;

    }

    public void setProtects(int i) {

        protects = i;

    }

    public void setCaptures(int i) {

        captures = i;

    }

    public int getKills() {

        return kills;

    }

    public int getAssists() {

        return assists;

    }

    public int getReturns() {

        return returns;

    }

    public int getKillStreak() {

        return killStreak;

    }

    public int getDeaths() {

        return deaths;

    }

    public int getPowerups() {

        return powerups;

    }

    public int getDefends() {

        return defends;

    }

    public int getProtects() {

        return protects;

    }

    public int getCaptures() {

        return captures;

    }

    public boolean hasShield() {

        return shield;

    }

    public void setShield(boolean b) {

        shield = b;

    }

    public void setLobby(SplatLobby lobby){

        this.lobby = lobby;

    }

    public SplatLobby getLobby(){

        return lobby;

    }

    public void setHit(boolean bool){

        hit = bool;

    }

    public boolean isHit(){

        return hit;

    }

    public void setAssistOn(UUID u){

        assistOn = u;

    }


    public UUID getAssistOn(){

        return assistOn;

    }


}
