package co.obam.ismooch.splatflag.creator;

import co.obam.ismooch.splatflag.loader.MapLoader;
import co.obam.ismooch.splatflag.objects.SplatMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class SplatMapBuilder {


    public static SplatMap createMap(String name) {

        System.out.println("[SplatFlag] Generating new instance for SplatMap " + name);

        int Red = 0;
        int Green = 1;
        int Blue = 2;

        int x = 0;
        int y = 1;
        int z = 2;

        World world = MapLoader.getSplatMaps().get(name);

        SplatMap map = new SplatMap();

        Date date = new Date();

        Time now = new Time(date.getTime());

        String worldname = String.valueOf(name + now.toString());

        World sourceWorld = MapLoader.splatMaps.get(name);

        Bukkit.createWorld(new WorldCreator(worldname));


        World targetWorld = Bukkit.getWorld(worldname);

        File target = targetWorld.getWorldFolder();

        Bukkit.unloadWorld(worldname, false);

        File source = sourceWorld.getWorldFolder();

        copyWorld(source, target);

        World ref = Bukkit.createWorld(new WorldCreator(worldname));

        map.setWorld(ref);

        List<Location> powerUps = new ArrayList<Location>();
        List<Location> team1Spawn = new ArrayList<Location>();
        List<Location> team2Spawn = new ArrayList<Location>();

        for (Location loc : MapLoader.splatTeam1Spawns.get(name)) {

            team1Spawn.add(flipWorld(ref, loc));

        }

        for (Location loc : MapLoader.splatTeam2Spawns.get(name)) {

            team2Spawn.add(flipWorld(ref, loc));

        }

        for (Location loc : MapLoader.splatPowerSpawns.get(name)) {

            powerUps.add(flipWorld(ref, loc));

        }

        map.setTeam1Spawn(team1Spawn);

        map.setTeam2Spawn(team2Spawn);

        map.setPowerUps(powerUps);

        map.setTeam1Ban(flipWorld(ref, MapLoader.splatTeam1Banner.get(name)));

        map.setTeam2Ban(flipWorld(ref, MapLoader.splatTeam2Banner.get(name)));

        map.setTeam1BanColor(MapLoader.splatTeam1Color.get(name));

        map.setTeam2BanColor(MapLoader.splatTeam2Color.get(name));

        map.setBaseMap(name);

        map.setTeam1Color(MapLoader.splatArmor1Color.get(name).get(Red), MapLoader.splatArmor1Color.get(name).get(Green), MapLoader.splatArmor1Color.get(name).get(Blue));

        map.setTeam2Color(MapLoader.splatArmor2Color.get(name).get(Red), MapLoader.splatArmor2Color.get(name).get(Green), MapLoader.splatArmor2Color.get(name).get(Blue));

        System.out.println("[SplatFlag] New instance created for " + name + "!");


        return map;
    }

    public static Location flipWorld(World world, Location loc) {

        return new Location(world, loc.getX(), loc.getY(), loc.getZ());

    }


    public static void unloadWorld(World world) {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }


    public static boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }


    public static void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists()) {

                        target.mkdirs();

                    }
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }


}
