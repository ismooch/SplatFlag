package co.obam.ismooch.splatflag.loader;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This is the MapLoader class that will hopefully
 * handle the majority of the initial loading of the
 * base map files
 */
public class MapLoader {


    public static Map<String, World> splatMaps = new HashMap<String, World>();
    public static Map<String, List<Location>> splatTeam1Spawns = new HashMap<String, List<Location>>();
    public static Map<String, List<Location>> splatTeam2Spawns = new HashMap<String, List<Location>>();
    public static Map<String, List<Location>> splatPowerSpawns = new HashMap<String, List<Location>>();
    public static Map<String, Location> splatTeam1Banner = new HashMap<String, Location>();
    public static Map<String, Location> splatTeam2Banner = new HashMap<String, Location>();
    public static Map<String, DyeColor> splatTeam1Color = new HashMap<String, DyeColor>();
    public static Map<String, DyeColor> splatTeam2Color = new HashMap<String, DyeColor>();
    public static Map<String, List<Integer>> splatArmor1Color = new HashMap<String, List<Integer>>();
    public static Map<String, List<Integer>> splatArmor2Color = new HashMap<String, List<Integer>>();
    public static List<String> splatMapsLarge = new ArrayList<String>();
    public static List<String> splatMapsMedium = new ArrayList<String>();
    public static List<String> splatMapsSmall = new ArrayList<String>();


    public static void loadWorlds() {


        for (String world : SplatFlag.getPlugin().getConfig().getStringList("SplatMaps")) {

            if (Bukkit.getWorld(world) == null) {

                File worldFile = new File(Bukkit.getServer().getWorldContainer(), world);

                if (worldFile.exists()) {

                    Bukkit.getServer().createWorld(new WorldCreator(world));

                } else {

                    System.out.println("[SplatFlag] " + ChatColor.RED + "Error:" + ChatColor.WHITE +
                            " Could not find world file for " + world + "!");

                }

            }

        }

        findMaps();

    }

    public static void findMaps() {

        int i = 0;
        int x = 0;
        int y = 1;
        int z = 2;
        int Red = 0;
        int Green = 1;
        int Blue = 2;
        for (World w : Bukkit.getWorlds()) {

            String wname = w.getName();

            if (wname.contains("SM")) {

                System.out.println("[SplatFlag] Looks like I found a world: " + wname);
                System.out.println("[SplatFlag] I will try to load " + wname);
                Plugin p = SplatFlag.getPlugin();
                File config = new File(p.getDataFolder(), "" + wname + ".yml");

                if (config.exists()) {

                    YamlConfiguration mapConfig = YamlConfiguration.loadConfiguration(config);
                    System.out.println("[SplatFlag] Found config for " + mapConfig.getString("Name"));
                    String mapName = mapConfig.getString("Name");

                    System.out.println("[SplatFlag] Found " + mapConfig.getStringList("Locations.Team1Spawns").size() +
                            " spawns for Team 1 for map. Attempting to load...");

                    List<Location> spawnOne = new ArrayList<Location>();
                    List<Location> spawnTwo = new ArrayList<Location>();
                    List<Location> puSpawn = new ArrayList<Location>();

                    for (String s : mapConfig.getStringList("Locations.Team1Spawns")) {

                        String[] split = s.split(":");
                        Location loc =
                                new Location(w, Double.parseDouble(split[x]), Double.parseDouble(split[y]), Double.parseDouble(split[z]));
                        spawnOne.add(loc);


                    }
                    if (spawnOne.size() < 1) {

                        System.out.println("[SplatFlag] No Team 1 spawns were loaded for map " + mapName + " :(");

                    } else {

                        System.out.println(
                                "[SplatFlag] Loaded " + spawnOne.size() + " Team 1 Spawns for map " + mapName + "!");
                        splatTeam1Spawns.put(mapName, spawnOne);

                    }

                    System.out.println("[SplatFlag] Found " + mapConfig.getStringList("Locations.Team2Spawns").size() +
                            " spawns for Team 2 for map. Attempting to load...");

                    for (String s : mapConfig.getStringList("Locations.Team2Spawns")) {

                        String[] split = s.split(":");
                        Location loc =
                                new Location(w, Double.parseDouble(split[x]), Double.parseDouble(split[y]), Double.parseDouble(split[z]));
                        spawnTwo.add(loc);


                    }
                    if (spawnTwo.size() < 1) {

                        System.out.println("[SplatFlag] No Team 2 spawns were loaded for map " + mapName + " :(");

                    } else {

                        System.out.println(
                                "[SplatFlag] Loaded " + spawnTwo.size() + " Team 2 Spawns for map " + mapName + "!");
                        splatTeam2Spawns.put(mapName, spawnTwo);

                    }

                    System.out.println(
                            "[SplatFlag] Found " + mapConfig.getStringList("Locations.PowerupSpawns").size() +
                                    " spawns for Powerups for map. Attempting to load...");

                    for (String s : mapConfig.getStringList("Locations.PowerupSpawns")) {

                        String[] split = s.split(":");
                        Location loc =
                                new Location(w, Double.parseDouble(split[x]), Double.parseDouble(split[y]), Double.parseDouble(split[z]));
                        puSpawn.add(loc);


                    }
                    if (puSpawn.size() < 1) {

                        System.out.println("[SplatFlag] No Powerup spawns were loaded for map " + mapName + " :(");

                    } else {

                        System.out.println(
                                "[SplatFlag] Loaded " + puSpawn.size() + " Powerup Spawns for map " + mapName + "!");
                        splatPowerSpawns.put(mapName, puSpawn);

                    }

                    System.out.println("[SplatFlag] Loading Banner points for " + mapName);

                    String ban1String = mapConfig.getString("Locations.Banner1");
                    String[] ban1Split = ban1String.split(":");
                    Location ban1 =
                            new Location(w, Double.parseDouble(ban1Split[x]), Double.parseDouble(ban1Split[x]), Double.parseDouble(ban1Split[x]));
                    if (ban1.getBlock() == null) {

                        System.out.println("[SplatFlag] Banner 1 was not found :(");

                    }

                    splatTeam1Banner.put(mapName, ban1);

                    String ban2String = mapConfig.getString("Locations.Banner1");
                    String[] ban2Split = ban1String.split(":");
                    Location ban2 =
                            new Location(w, Double.parseDouble(ban1Split[x]), Double.parseDouble(ban1Split[x]), Double.parseDouble(ban1Split[x]));
                    if (ban2.getBlock() == null) {

                        System.out.println("[SplatFlag] Banner 2 was not found :(");

                    }

                    splatTeam2Banner.put(mapName, ban2);

                    System.out.println("[SplatFlag] Loading Team Colors for " + mapName);

                    String color1String = mapConfig.getString("Colors.Team1");
                    DyeColor color1 = DyeColor.valueOf(color1String);
                    splatTeam1Color.put(mapName, color1);
                    System.out.println(
                            "[SplatFlag] Team 1 color is " + ChatColor.valueOf(color1.name()) + color1.name());

                    String color2String = mapConfig.getString("Colors.Team2");
                    DyeColor color2 = DyeColor.valueOf(color1String);
                    splatTeam2Color.put(mapName, color2);
                    System.out.println(
                            "[SplatFlag] Team 2 color is " + ChatColor.valueOf(color2.name()) + color2.name());

                    System.out.println("[SplatFlag] Loading Armor Colors for " + mapName);

                    String armor1String = mapConfig.getString("Colors.Armor1");
                    String[] armor1Split = armor1String.split(":");
                    List<Integer> armor1 = new ArrayList<Integer>();
                    armor1.set(Red, Integer.parseInt(armor1Split[Red]));
                    armor1.set(Green, Integer.parseInt(armor1Split[Green]));
                    armor1.set(Blue, Integer.parseInt(armor1Split[Blue]));

                    splatArmor1Color.put(mapName, armor1);

                    String armor2String = mapConfig.getString("Colors.Armor2");
                    String[] armor2Split = armor2String.split(":");
                    List<Integer> armor2 = new ArrayList<Integer>();
                    armor2.set(Red, Integer.parseInt(armor2Split[Red]));
                    armor2.set(Green, Integer.parseInt(armor2Split[Green]));
                    armor2.set(Blue, Integer.parseInt(armor2Split[Blue]));

                    splatArmor2Color.put(mapName, armor2);

                    System.out.println("[SplatFlag] Checking map size declarations for " + mapName);

                    List<String> sizes = mapConfig.getStringList("Sizes");

                    for(String s : sizes){

                        if(s.equalsIgnoreCase("large")){

                            splatMapsLarge.add(mapName);

                        }else if(s.equalsIgnoreCase("medium")){

                            splatMapsMedium.add(mapName);

                        }else if(s.equalsIgnoreCase("small")){

                            splatMapsSmall.add(mapName);

                        }

                    }


                }

            }

        }

    }

    public static void loadSplatWorld(String name, String world) {

        if (Bukkit.getWorld(world) != null) {

            splatMaps.put(name, Bukkit.getWorld(world));

        }

    }

    public static Map<String, World> getSplatMaps() {

        return splatMaps;


    }


}
