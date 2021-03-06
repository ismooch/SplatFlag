package co.obam.ismooch.splatflag.objects;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/* This is the SplatMap object class that will
   help utilize special functions for during game
   operation *he says hoepfully*
 */

public class SplatMap {

    private List<Location> powerUps;
    private List<Location> team1Spawn;
    private List<Location> team2Spawn;
    private Location team1Ban;
    private Location team2Ban;
    private List<Integer> team1Color;
    private List<Integer> team2Color;
    private DyeColor team1BanColor;
    private DyeColor team2BanColor;
    private int team1Respawn;
    private int team2Respawn;
    private int Red = 0;
    private int Green = 1;
    private int Blue = 2;
    private String baseMap;
    private int smID;
    private List<Player> team1;
    private List<Player> team2;
    private SplatMap splatmap;
    private World world;
    private Map<UUID, Integer> powerUpHit = new HashMap<UUID, Integer>();

    public void powerUpSpawn() {

        Location loc = powerUps.get(new Random().nextInt(powerUps.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

        int ran = new Random().nextInt(100);
        if (ran < 25) {

            Cow power = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
            power.setCustomName(ChatColor.YELLOW + "Speed Buff");
            powerUpHit.put(power.getUniqueId(), 0);

            for (Player p : getTeam1()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Speed " + ChatColor.GOLD + "powerup has spawned!");

            }

            for (Player p : getTeam2()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Speed " + ChatColor.GOLD + "powerup has spawned!");

            }

        } else if (ran >= 25 && ran < 50) {

            Chicken power = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
            power.setCustomName(ChatColor.YELLOW + "Shield");
            powerUpHit.put(power.getUniqueId(), 0);

            for (Player p : getTeam1()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Shield " + ChatColor.GOLD + "powerup has spawned!");

            }

            for (Player p : getTeam2()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Shield " + ChatColor.GOLD + "powerup has spawned!");

            }

        } else if (ran >= 50 && ran < 75) {

            Pig power = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
            power.setCustomName(ChatColor.YELLOW + "Reduced Respawn");
            powerUpHit.put(power.getUniqueId(), 0);

            for (Player p : getTeam1()) {

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Respawn " + ChatColor.GOLD +
                        "powerup has spawned!");

            }

            for (Player p : getTeam2()) {

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Respawn " + ChatColor.GOLD +
                        "powerup has spawned!");

            }

        } else if (ran >= 75 && ran <= 100) {

            IronGolem power = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
            power.setCustomName(ChatColor.YELLOW + "Mega Reload");
            powerUpHit.put(power.getUniqueId(), 0);

            for (Player p : getTeam1()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Reload " + ChatColor.GOLD + "powerup has spawned!");

            }

            for (Player p : getTeam2()) {

                p.sendRawMessage(
                        ChatColor.GOLD + "A " + ChatColor.YELLOW + "Reload " + ChatColor.GOLD + "powerup has spawned!");

            }


        }


    }

    public void spawnBanner(int i){

        final SplatMap sm = this;
        if(i == 1){

            for(Player p : getTeam1()){

                p.sendRawMessage(ChatColor.GREEN + "Your flag will respawn in " + ChatColor.YELLOW + "30 " + ChatColor.GREEN + "seconds!");

            }


        Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
            @Override
            public void run() {
                {

                    Location loc = new Location(sm.getTeam1Ban().getWorld(), sm.getTeam1Ban().getX(), sm.getTeam1Ban().getY() + 1, sm.getTeam1Ban().getZ());

                    Banner ban = (Banner) loc.getBlock().getState();
                    ban.setBaseColor(sm.getTeam1BanColor());
                    ban.update();

                    for(Player p : sm.getTeam1()){

                        p.sendRawMessage(ChatColor.GREEN + "Your flag has respawned!");

                    }

                    for(Player p : sm.getTeam2()){

                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has respawned!");

                    }


                }


            }
        }, 30 * 20L);

    }else if(i == 2){

            for(Player p : getTeam2()){

                p.sendRawMessage(ChatColor.GREEN + "Your flag will respawn in " + ChatColor.YELLOW + "30 " + ChatColor.GREEN + "seconds!");

            }


            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    {

                        Location loc = new Location(sm.getTeam2Ban().getWorld(), sm.getTeam2Ban().getX(), sm.getTeam2Ban().getY() + 1, sm.getTeam2Ban().getZ());

                        Banner ban = (Banner) loc.getBlock().getState();
                        ban.setBaseColor(sm.getTeam2BanColor());
                        ban.update();

                        for(Player p : sm.getTeam2()){

                            p.sendRawMessage(ChatColor.GREEN + "Your flag has respawned!");

                        }

                        for(Player p : sm.getTeam1()){

                            p.sendRawMessage(ChatColor.GREEN + "The enemy flag has respawned!");

                        }


                    }


                }
            }, 30 * 20L);

        }

    }


    public void respawn(final Player p, final int team) {

        final SplatMap sm = this;

        if (team == 1) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {


                    sm.spawnTeam1(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendRawMessage(ChatColor.GREEN + "You have respawned!");


                }
            }, getTeam1Respawn() * 20);
        } else if (team == 2) {


            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {


                    sm.spawnTeam2(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendRawMessage(ChatColor.GREEN + "You have respawned!");


                }
            }, getTeam2Respawn() * 20);
        }

    }


    public void setTeam1Respawn(int i){

        team1Respawn = i;

    }

    public void setTeam2Respawn(int i){

        team2Respawn = i;

    }

    public int getTeam1Respawn(){

        return team1Respawn;

    }

    public int getTeam2Respawn(){

        return team2Respawn;

    }

    public List<Location> getTeam1Spawn() {

        return team1Spawn;

    }

    public List<Location> getTeam2Spawn() {


        return team2Spawn;

    }

    public List<Location> getPowerUps() {

        return powerUps;

    }

    public Location getTeam1Ban() {

        return team1Ban;

    }

    public Location getTeam2Ban() {

        return team2Ban;

    }

    public void addPowerUp(Location loc) {

        powerUps.add(loc);

    }

    public void addTeam1Spawn(Location loc) {

        team1Spawn.add(loc);

    }

    public void addTeam2Spawn(Location loc) {

        team2Spawn.add(loc);

    }

    public void setTeam1Ban(Location loc) {

        team1Ban = loc;

    }

    public void setTeam2Ban(Location loc) {

        team2Ban = loc;

    }

    public void setPowerUps(List<Location> list) {

        powerUps = list;

    }

    public void setTeam1Spawn(List<Location> list) {

        team1Spawn = list;

    }

    public void setTeam2Spawn(List<Location> list) {

        team2Spawn = list;

    }

    public void spawnTeam2(Player p) {

        Location loc = team2Spawn.get(new Random().nextInt(team1Spawn.size()));

        int red = team2Color.get(Red);
        int green = team2Color.get(Green);
        int blue = team2Color.get(Blue);

        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta) lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta) llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta) lboots.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lboots.setItemMeta(lam);

        p.getInventory().setHelmet(lhelmet);
        p.getInventory().setChestplate(lchest);
        p.getInventory().setLeggings(llegs);
        p.getInventory().setBoots(lboots);

        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));

        p.teleport(loc);


    }


    public void spawnTeam1(Player p) {

        p.getInventory().clear();

        Location loc = team1Spawn.get(new Random().nextInt(team1Spawn.size()));

        int red = team1Color.get(Red);
        int green = team1Color.get(Green);
        int blue = team1Color.get(Blue);

        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta) lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta) llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta) lboots.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lboots.setItemMeta(lam);

        p.getInventory().setHelmet(lhelmet);
        p.getInventory().setChestplate(lchest);
        p.getInventory().setLeggings(llegs);
        p.getInventory().setBoots(lboots);

        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));

        p.teleport(loc);


    }

    public void setTeam1Color(int red, int green, int blue) {

        team1Color.add(Red, red);
        team1Color.add(Blue, blue);
        team1Color.add(Green, green);

    }

    public void setTeam2Color(int red, int green, int blue) {

        team2Color.add(Red, red);
        team2Color.add(Green, green);
        team2Color.add(Blue, blue);

    }

    public List<Integer> getTeam1Color() {

        return team1Color;

    }

    public List<Integer> getTeam2Color() {

        return team2Color;

    }

    public void setTeam1BanColor(DyeColor color) {

        team1BanColor = color;

    }

    public void setTeam2BanColor(DyeColor color) {

        team2BanColor = color;

    }

    public DyeColor getTeam1BanColor() {

        return team1BanColor;

    }

    public DyeColor getTeam2BanColor() {

        return team2BanColor;

    }

    public String getBaseMap() {

        return baseMap;

    }

    public void setBaseMap(String world) {

        baseMap = world;

    }

    public void setBaseMap(World world) {

        baseMap = world.getName();

    }

    public void setSmID(int id) {

        smID = id;

    }

    public int getSmID() {

        return smID;

    }

    public void setTeam1(List<Player> list) {

        team1 = list;

    }

    public void addToTeam1(Player p) {

        team1.add(p);

    }

    public void removeFromTeam1(Player p){

        if(team1.contains(p)){

            team1.remove(p);

        }

    }

    public void removeFromTeam2(Player p){

        if(team2.contains(p)){

            team2.remove(p);

        }

    }

    public List<Player> getTeam1() {

        return team1;

    }

    public void setTeam2(List<Player> list) {

        team2 = list;

    }

    public void addToTeam2(Player p) {

        team2.add(p);

    }

    public List<Player> getTeam2() {

        return team2;

    }

    public boolean isTeam1(Player p) {

        return team1.contains(p);

    }

    public boolean isTeam2(Player p) {

        return team2.contains(p);
    }

    public SplatMap getInstance() {

        return splatmap;

    }


    public World getWorld() {

        return world;

    }

    public void setWorld(World world) {

        this.world = world;
    }

    public Map<UUID, Integer> getPowerUpHit() {

        return powerUpHit;

    }

    public void spawnBan(int i){

        if(i == 1){

            Location loc = new Location(getTeam1Ban().getWorld(), getTeam1Ban().getX(), getTeam1Ban().getY() + 1, getTeam1Ban().getZ());

            Banner ban = (Banner) loc.getBlock().getState();
            ban.setBaseColor(getTeam1BanColor());
            ban.update();


        }else if(i == 2){

            Location loc = new Location(getTeam2Ban().getWorld(), getTeam2Ban().getX(), getTeam2Ban().getY() + 1, getTeam2Ban().getZ());

            Banner ban = (Banner) loc.getBlock().getState();
            ban.setBaseColor(getTeam2BanColor());
            ban.update();


        }

    }

}
