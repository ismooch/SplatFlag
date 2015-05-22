package co.obam.ismooch.splatflag.objects;


import co.obam.ismooch.splatflag.SplatFlag;
import co.obam.ismooch.splatflag.creator.SplatMapBuilder;
import co.obam.ismooch.splatflag.effects.SplatEffects;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/*
 *This will be the command handler for the lobbies for SplatFlag
 * Hopefully
 */
public class SplatLobby {


    private int playerCount;
    private String size;
    private Location spawn;
    private List<Location> signs = new ArrayList<Location>();
    private String name;
    private List<UUID> playerList = new ArrayList<UUID>();
    private String status;
    private SplatMap map;
    private String mapChoice;
    private int taskID;
    private int snowball;
    private int check;

    public int getPlayerCount() {

        return playerCount;

    }

    public void setPlayerCount(int i) {

        playerCount = i;

    }

    public String getSize() {

        return size;

    }

    public void setSize(String s) {

        size = s;

    }

    public Location getSpawn() {

        return spawn;

    }

    public void setSpawn(Location loc) {

        spawn = loc;

    }

    public List<Location> getSigns() {

        return signs;

    }

    public void setSigns(List<Location> list) {

        signs = list;

    }

    public String getName() {

        return name;

    }

    public void setName(String s) {

        name = s;

    }

    public List<UUID> getPlayerList() {

        return playerList;

    }

    public void setPlayerList(List<UUID> list) {

        playerList = list;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String s) {

        status = s;

    }

    public void removePlayer(Player p) {

        UUID u = p.getUniqueId();

        removePlayer(u);


    }

    public void removePlayer(UUID u) {

        if (playerList.contains(u)) {

            playerList.remove(u);
            playerCount = playerCount - 1;


        }

    }

    public boolean hasPlayer(Player p) {

        UUID u = p.getUniqueId();

        return hasPlayer(u);

    }

    public boolean hasPlayer(UUID u) {

        return playerList.contains(u);

    }

    public void addPlayer(UUID u) {

        if (!playerList.contains(u)) {

            playerList.add(u);
            playerCount = playerCount + 1;

        }

    }

    public void addPlayer(Player p) {

        UUID u = p.getUniqueId();

        addPlayer(u);

    }

    public void addSign(Location loc) {

        if (!signs.contains(loc)) {

            signs.add(loc);

        }

    }

    public void removeSign(Location loc) {

        if (signs.contains(loc)) {

            signs.remove(loc);

        }

    }

    public boolean isFull() {

        if (size.equalsIgnoreCase("large")) {

            return playerCount >= 14;

        } else if (size.equalsIgnoreCase("medium")) {

            return playerCount >= 10;

        } else {

            return playerCount >= 6;

        }

    }

    public void updateSigns() {


        for (Location loc : signs) {

            if (!(loc.getBlock() instanceof Sign)) {

                removeSign(loc);

            } else {

                Sign sign = (Sign) loc.getBlock().getState();

                sign.setLine(1, ChatColor.BLUE + name);
                sign.setLine(3, status);

                if (map == null) {

                    sign.setLine(4, ChatColor.DARK_AQUA + "Splat" + ChatColor.GOLD + "Flag!");

                } else {

                    sign.setLine(4, ChatColor.GOLD + map.getBaseMap());

                }


                if (size.equalsIgnoreCase("large")) {

                    sign.setLine(2, ChatColor.DARK_AQUA + "" + playerCount + "/14");

                } else if (size.equalsIgnoreCase("medium")) {

                    sign.setLine(2, ChatColor.DARK_AQUA + "" + playerCount + "/10");

                } else if (size.equalsIgnoreCase("small")) {

                    sign.setLine(2, ChatColor.DARK_AQUA + "" + playerCount + "/6");

                }

            }


        }

    }


    public SplatMap getMap() {

        return map;

    }

    public void setMap(SplatMap sm) {

        map = sm;

    }

    public void generateMap() {

        map = SplatMapBuilder.createMap(mapChoice);
        updateSigns();

    }

    public int getTaskID() {

        return taskID;

    }

    public void startGame() {

        status = String.valueOf(ChatColor.RED + "In Progress");


        for (UUID u : playerList) {

            Player p = Bukkit.getPlayer(u);

            SplatPlayer sp = SplatPlayer.getInstanceOfPlayer(p);

            if (map.getTeam1().size() < 1 && map.getTeam2().size() < 1) {

                int chance = new Random().nextInt(100);

                if (chance < 50) {

                    map.addToTeam1(p);
                    sp.setTeam(1);
                    sp.setArmorColor(map.getTeam1Color());
                    sp.setSplatMap(map);
                    sp.setColor(map.getTeam1BanColor());


                } else {

                    map.addToTeam2(p);
                    sp.setTeam(2);
                    sp.setArmorColor(map.getTeam2Color());
                    sp.setSplatMap(map);
                    sp.setColor(map.getTeam2BanColor());

                }

            } else if (map.getTeam1().size() > map.getTeam2().size()) {

                map.addToTeam2(p);
                sp.setTeam(2);
                sp.setArmorColor(map.getTeam2Color());
                sp.setSplatMap(map);
                sp.setColor(map.getTeam2BanColor());

            } else if (map.getTeam2().size() > map.getTeam1().size()) {

                map.addToTeam1(p);
                sp.setTeam(1);
                sp.setArmorColor(map.getTeam1Color());
                sp.setSplatMap(map);
                sp.setColor(map.getTeam1BanColor());

            }

            if (map.isTeam1(p)) {

                map.spawnTeam1(p);

            } else if (map.isTeam2(p)) {

                map.spawnTeam2(p);

            }

        }


        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SplatFlag.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (snowball < 1) {

                    snowball++;

                } else {

                    snowball = 0;

                    for (UUID u : playerList) {

                        Player p = Bukkit.getPlayer(u);

                        int amount = 0;
                        for (ItemStack is : p.getInventory().all(Material.SNOW_BALL).values()) {
                            amount = amount + is.getAmount();
                        }
                        if (amount < 64) {

                            p.getInventory().addItem(new ItemStack(Material.SNOW_BALL));

                        }

                    }

                }

                check++;
                if (check == 120) {

                    check = 0;
                    map.powerUpSpawn();

                }
                for (Player p : map.getWorld().getPlayers()) {

                    p.setFoodLevel(20);

                }

                for (Entity e : map.getWorld().getEntities()) {

                    if (e instanceof Player) {

                        Player p = (Player) e;


                        if (SplatPlayer.getInstanceOfPlayer(p).hasShield()) {

                            p.getWorld().playEffect(p.getEyeLocation(), Effect.SMOKE, 20, 5);

                        }

                    }

                    if (e instanceof Cow) {

                        if (map.getPowerUpHit().containsKey(e.getUniqueId())) {

                            SplatEffects.launchRedFirework(e);

                        }

                    } else if (e instanceof Pig) {

                        if (map.getPowerUpHit().containsKey(e.getUniqueId())) {

                            SplatEffects.launchRedFirework(e);

                        }

                    } else if (e instanceof Chicken) {

                        if (map.getPowerUpHit().containsKey(e.getUniqueId())) {

                            SplatEffects.launchRedFirework(e);

                        }


                    } else if (e instanceof IronGolem) {

                        if (map.getPowerUpHit().containsKey(e.getUniqueId())) {

                            SplatEffects.launchRedFirework(e);

                        }

                    }
                    if (e instanceof Item) {

                        ItemStack is = ((Item) e).getItemStack();

                        if (is.equals(new ItemStack(Material.BANNER, 1, (short) 4))) {


                            SplatEffects.launchBlueFirework(e, 10);

                        } else if (is.equals(new ItemStack(Material.BANNER, 1, (short) 11))) {

                            SplatEffects.launchYellowFirework(e, 10);

                        }

                    }

                }


            }
        }, 0L, 20L);


    }

    public void endGame() {

        Bukkit.getScheduler().cancelTask(taskID);
        status = String.valueOf(ChatColor.RED + "Ending Game");
        File file = map.getWorld().getWorldFolder();
        SplatMapBuilder.unloadWorld(map.getWorld());

        SplatMapBuilder.deleteWorld(file);


    }


}
