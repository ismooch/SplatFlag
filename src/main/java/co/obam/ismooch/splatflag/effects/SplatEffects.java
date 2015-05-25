package co.obam.ismooch.splatflag.effects;

import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SplatEffects {


    public static void launchYellowFirework(Entity e, int speed) {
        Firework fw = (Firework) e.getWorld().spawn(e.getLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder effect = FireworkEffect.builder();
        effect.trail(true);
        effect.withColor(Color.YELLOW);
        effect.flicker(true);
        effect.with(FireworkEffect.Type.BALL_LARGE);
        meta.addEffect(effect.build());
        fw.setFireworkMeta(meta);

    }

    public static void launchBlueFirework(Entity e, int speed) {
        Firework fw = (Firework) e.getWorld().spawn(e.getLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder effect = FireworkEffect.builder();
        effect.trail(true);
        effect.withColor(Color.BLUE);
        effect.flicker(true);
        effect.with(FireworkEffect.Type.BALL_LARGE);
        meta.addEffect(effect.build());
        fw.setFireworkMeta(meta);

    }

    public static void launchRedFirework(Entity e) {

        Firework fw = (Firework) e.getWorld().spawn(e.getLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder effect = FireworkEffect.builder();
        effect.trail(true);
        effect.withColor(Color.RED);
        effect.flicker(true);
        effect.with(FireworkEffect.Type.BALL_LARGE);
        meta.addEffect(effect.build());
        fw.setFireworkMeta(meta);


    }

    public static void splash(Location l, SplatPlayer sp, Integer team) {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int yCoord = (int) l.getY();

        List<Location> tempList = new ArrayList<Location>();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {

                    Location loc = new Location(w, xCoord + x, yCoord + y, zCoord + z);
                    if (sp.getTeam() == 1 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) ||
                            loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if (new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);

                            loc.getBlock().setData(sp.getSplatMap().getTeam1BanColor().getDyeData());
                        }

                    } else if (sp.getTeam() == 2 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) ||
                            loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if (new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);
                            loc.getBlock().setData(sp.getSplatMap().getTeam2BanColor().getDyeData());
                        }
                    }
                }


            }
        }
    }

}
