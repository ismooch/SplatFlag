package co.obam.ismooch.splatflag.effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by troyj_000 on 5/22/2015.
 */
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

}
