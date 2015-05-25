package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.SplatFlag;
import co.obam.ismooch.splatflag.objects.SplatMap;
import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;


public class ProjectileHit implements Listener {

    @EventHandler
    public static void onProjegtileHit(EntityDamageByEntityEvent e) {

        Projectile ball;
        Player player;

        if (e.getDamager() instanceof Snowball && ((Snowball) e.getDamager()).getShooter() instanceof Player) {


            ball = (Projectile) e.getDamager();


            player = (Player) ball.getShooter();

            SplatPlayer sPlayer = SplatPlayer.getInstanceOfPlayer(player);

            ItemStack is = new ItemStack(Material.BANNER);
            BannerMeta bm = (BannerMeta) is.getItemMeta();


            if (e.getEntity() instanceof Chicken) {

                if (sPlayer.getSplatMap().getPowerUpHit().containsKey(e.getEntity().getUniqueId())) {


                    if (sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) < 5) {


                        sPlayer.getSplatMap().getPowerUpHit().put(e.getEntity().getUniqueId(),
                                sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        sPlayer.setPowerups(sPlayer.getPowerups() + 1);


                        e.getEntity().remove();
                        if (sPlayer.getTeam() == 1) {

                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Shield " +
                                            ChatColor.GREEN + "powerup for your team!");
                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                SplatPlayer sp = SplatPlayer.getInstanceOfPlayer(p);

                                sp.setShield(true);

                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Shield " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (sPlayer.getTeam() == 2) {

                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Shield " +
                                            ChatColor.GREEN + "powerup for your team!");
                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                SplatPlayer sp = SplatPlayer.getInstanceOfPlayer(p);

                                sp.setShield(true);

                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Shield " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }

            if (e.getEntity() instanceof IronGolem) {

                if (sPlayer.getSplatMap().getPowerUpHit().containsKey(e.getEntity().getUniqueId())) {


                    if (sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) < 5) {


                        sPlayer.getSplatMap().getPowerUpHit().put(e.getEntity().getUniqueId(),
                                sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        sPlayer.setPowerups(sPlayer.getPowerups() + 1);


                        e.getEntity().remove();
                        if (sPlayer.getTeam() == 1) {


                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Reload " +
                                            ChatColor.GREEN + "powerup for your team!");


                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
                                p.updateInventory();
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Reload " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (sPlayer.getTeam() == 2) {

                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Reload " +
                                            ChatColor.GREEN + "powerup for your team!");

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
                                p.updateInventory();
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }

            if (e.getEntity() instanceof Pig) {

                if (sPlayer.getSplatMap().getPowerUpHit().containsKey(e.getEntity().getUniqueId())) {


                    if (sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) < 5) {


                        sPlayer.getSplatMap().getPowerUpHit().put(e.getEntity().getUniqueId(),
                                sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        sPlayer.setPowerups(sPlayer.getPowerups() + 1);


                        e.getEntity().remove();
                        if (sPlayer.getTeam() == 1) {

                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Respawn " +
                                            ChatColor.GREEN + "powerup for your team!");

                            sPlayer.getSplatMap().setTeam1Respawn(5);

                            final SplatMap sm = sPlayer.getSplatMap();


                            for (Player p : sm.getTeam1()) {


                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD +
                                                    "Respawn " +
                                                    ChatColor.GREEN + " power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : sm.getTeam2()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                                @Override
                                public void run() {


                                    sm.setTeam1Respawn(15);
                                    for (Player p : sm.getTeam1()) {

                                        p.sendRawMessage(ChatColor.GOLD + "Your respawn powerup has worn off!");

                                    }

                                }
                            }, 30 * 20L);

                        } else if (sPlayer.getTeam() == 2) {

                            player.sendRawMessage(
                                    ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Respawn " +
                                            ChatColor.GREEN + "powerup for your team!");

                            sPlayer.getSplatMap().setTeam2Respawn(5);

                            final SplatMap sm = sPlayer.getSplatMap();


                            for (Player p : sm.getTeam2()) {


                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD +
                                                    "Respawn " +
                                                    ChatColor.GREEN + " power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : sm.getTeam1()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                                @Override
                                public void run() {


                                    sm.setTeam2Respawn(15);
                                    for (Player p : sm.getTeam2()) {

                                        p.sendRawMessage(ChatColor.GOLD + "Your respawn powerup has worn off!");

                                    }

                                }
                            }, 30 * 20L);

                        }

                    }

                }

            }

            if (e.getEntity() instanceof Cow) {

                if (sPlayer.getSplatMap().getPowerUpHit().containsKey(e.getEntity().getUniqueId())) {


                    if (sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) < 5) {


                        sPlayer.getSplatMap().getPowerUpHit().put(e.getEntity().getUniqueId(),
                                sPlayer.getSplatMap().getPowerUpHit().get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        sPlayer.setPowerups(sPlayer.getPowerups() + 1);


                        e.getEntity().remove();
                        if (sPlayer.getTeam() == 1) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Speed " +
                                    ChatColor.GREEN + "powerup for your team!");


                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                PotionEffect pt = new PotionEffect(PotionEffectType.SPEED, 30 * 20, 2, false, false);
                                p.addPotionEffect(pt);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (sPlayer.getTeam() == 2) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Speed " +
                                    ChatColor.GREEN + "powerup for your team!");

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                PotionEffect pt = new PotionEffect(PotionEffectType.SPEED, 30 * 20, 2, false, false);
                                p.addPotionEffect(pt);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }
            if (e.getEntity() instanceof Player) {

                if ((sPlayer.getSplatMap().getTeam1().contains(e.getEntity()) &&
                        (sPlayer.getSplatMap().getTeam2().contains(player))) ||
                        (sPlayer.getSplatMap().getTeam2().contains(e.getEntity()) &&
                                (sPlayer.getSplatMap().getTeam1().contains(player)))) {

                    player.playSound(player.getLocation(), Sound.SLIME_WALK, 10, -1);
                    ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.SLIME_WALK, 10, -1);

                    SplatPlayer sp = SplatPlayer.getInstanceOfPlayer((Player) e.getEntity());

                    if (sp.hasShield()) {

                        sp.setShield(false);
                        ((Player) e.getEntity()).sendRawMessage(ChatColor.RED + "Your shield has been broken!");
                        player.sendRawMessage(ChatColor.GREEN + "You have broken the shield of " + ChatColor.YELLOW +
                                ((Player) e.getEntity()).getDisplayName() + ChatColor.GREEN + "!");

                    } else if (sp.isHit()) {

                        sp.setDeaths(sp.getDeaths() + 1);

                        if (sPlayer.getTeam() == 1) {


                            Location loc = player.getLocation();
                            Location loc2 = sPlayer.getSplatMap().getTeam1Ban();
                            if (((loc.getX() - loc2.getX() >= -7 && loc.getX() - loc2.getX() <= 7) ||
                                    (loc.getX() + loc2.getX() <= 7 && loc.getX() + loc2.getX() >= -7)) &&
                                    ((loc.getY() - loc2.getY() >= -7 && loc.getY() - loc2.getY() <= 7) ||
                                            (loc.getY() + loc2.getY() <= 7 && loc.getY() + loc2.getY() >= -7)) &&
                                    ((loc.getZ() - loc2.getZ() >= -7 && loc.getZ() - loc2.getZ() <= 7) ||
                                            (loc.getZ() + loc2.getZ() <= 7 && loc.getZ() + loc2.getZ() >= -7))) {

                                sPlayer.setDefends(sPlayer.getDefends() + 1);

                            }

                        } else if (sPlayer.getTeam() == 2) {

                            Location loc = player.getLocation();
                            Location loc2 = sPlayer.getSplatMap().getTeam2Ban();

                            if (((loc.getX() - loc2.getX() >= -7 && loc.getX() - loc2.getX() <= 7) ||
                                    (loc.getX() + loc2.getX() <= 7 && loc.getX() + loc2.getX() >= -7)) &&
                                    ((loc.getY() - loc2.getY() >= -7 && loc.getY() - loc2.getY() <= 7) ||
                                            (loc.getY() + loc2.getY() <= 7 && loc.getY() + loc2.getY() >= -7)) &&
                                    ((loc.getZ() - loc2.getZ() >= -7 && loc.getZ() - loc2.getZ() <= 7) ||
                                            (loc.getZ() + loc2.getZ() <= 7 && loc.getZ() + loc2.getZ() >= -7))) {

                                sPlayer.setDefends(sPlayer.getDefends() + 1);

                            }

                        }


                        if (sPlayer.getTeam() == 1) {


                            Location loc2 = null;
                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                if (!player.equals(p)) {


                                    ItemStack ban = p.getInventory().getHelmet();
                                    if (ban.getItemMeta() instanceof BannerMeta) {
                                        BannerMeta bam = (BannerMeta) ban.getItemMeta();

                                        if (bam.getBaseColor().equals(sPlayer.getSplatMap().getTeam1BanColor())) {

                                            loc2 = p.getLocation();

                                        }

                                    }

                                }
                            }
                            if (loc2 != null) {
                                Location loc = player.getLocation();
                                if (((loc.getX() - loc2.getX() >= -5 && loc.getX() - loc2.getX() <= 5) ||
                                        (loc.getX() + loc2.getX() <= 5 && loc.getX() + loc2.getX() >= -5)) &&
                                        ((loc.getY() - loc2.getY() >= -5 && loc.getY() - loc2.getY() <= 5) ||
                                                (loc.getY() + loc2.getY() <= 5 && loc.getY() + loc2.getY() >= -5)) &&
                                        ((loc.getZ() - loc2.getZ() >= -5 && loc.getZ() - loc2.getZ() <= 5) ||
                                                (loc.getZ() + loc2.getZ() <= 5 && loc.getZ() + loc2.getZ() >= -5))) {

                                    sPlayer.setProtects(sPlayer.getProtects() + 1);

                                }
                            }

                        } else if (sPlayer.getTeam() == 2) {


                            Location loc2 = null;

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                if (!player.equals(p)) {


                                    ItemStack ban = p.getInventory().getHelmet();
                                    if (ban.getItemMeta() instanceof BannerMeta) {
                                        BannerMeta bam = (BannerMeta) ban.getItemMeta();

                                        if (bam.getBaseColor().equals(sPlayer.getSplatMap().getTeam2BanColor())) {

                                            loc2 = p.getLocation();

                                        }

                                    }

                                }
                            }
                            if (loc2 != null) {
                                Location loc = player.getLocation();
                                if (((loc.getX() - loc2.getX() >= -5 && loc.getX() - loc2.getX() <= 5) ||
                                        (loc.getX() + loc2.getX() <= 5 && loc.getX() + loc2.getX() >= -5)) &&
                                        ((loc.getY() - loc2.getY() >= -5 && loc.getY() - loc2.getY() <= 5) ||
                                                (loc.getY() + loc2.getY() <= 5 && loc.getY() + loc2.getY() >= -5)) &&
                                        ((loc.getZ() - loc2.getZ() >= -5 && loc.getZ() - loc2.getZ() <= 5) ||
                                                (loc.getZ() + loc2.getZ() <= 5 && loc.getZ() + loc2.getZ() >= -5))) {

                                    sPlayer.setProtects(sPlayer.getProtects() + 1);

                                }
                            }

                        }
                        if (!player.equals(Bukkit.getPlayer(SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).getAssistOn()))) {

                            UUID u = SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).getAssistOn();

                            SplatPlayer.getInstanceOfPlayer(Bukkit.getPlayer(u)).setAssists(
                                    SplatPlayer.getInstanceOfPlayer(Bukkit.getPlayer(u)).getAssists() + 1);

                            SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).setAssistOn(null);
                            SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).setHit(false);


                            ((Player) e.getEntity()).setGameMode(GameMode.SPECTATOR);

                            e.getEntity().teleport(player.getLocation());

                            sPlayer.setKills(sPlayer.getKills() + 1);

                            sPlayer.setKillStreak(sPlayer.getKillStreak() + 1);

                            SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).setKillStreak(0);

                            if (sPlayer.getKillStreak() == 5) {

                                for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                    p.sendRawMessage(
                                            ChatColor.YELLOW + player.getName() + ChatColor.GOLD + " is dominating!");

                                }
                                for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                    p.sendRawMessage(
                                            ChatColor.YELLOW + player.getName() + ChatColor.GOLD + " is dominating!");

                                }

                            }


                            ((Player) e.getEntity()).sendRawMessage(
                                    ChatColor.RED + "You have been killed by " + ChatColor.YELLOW +
                                            player.getName() + ChatColor.RED + "!");

                            player.sendRawMessage(
                                    ChatColor.RED + "You have slain " + ChatColor.YELLOW + e.getEntity().getName() +
                                            ChatColor.RED + "!");

                            ((Player) e.getEntity()).getInventory().clear();

                            if (sp.getTeam() == 1) {

                                ((Player) e.getEntity()).sendRawMessage(
                                        ChatColor.GREEN + "You will respawn in " + ChatColor.YELLOW +
                                                sPlayer.getSplatMap().getTeam1Respawn() + ChatColor.GREEN +
                                                " seconds!");

                            } else if (sp.getTeam() == 2) {

                                ((Player) e.getEntity()).sendRawMessage(
                                        ChatColor.GREEN + "You will respawn in " + ChatColor.YELLOW +
                                                sPlayer.getSplatMap().getTeam2Respawn() + ChatColor.GREEN +
                                                " seconds!");


                            }

                            int team = 0;

                            ItemStack helm = ((Player) e.getEntity()).getInventory().getHelmet();

                            if (helm.getItemMeta() instanceof BannerMeta) {

                                BannerMeta bam = (BannerMeta) helm.getItemMeta();

                                if (bam.getBaseColor().equals(sPlayer.getSplatMap().getTeam1BanColor())) {


                                    team = 1;

                                    ItemStack ban = new ItemStack(Material.BANNER, 1);
                                    BannerMeta banMeta = (BannerMeta) ban.getItemMeta();
                                    banMeta.setBaseColor(sPlayer.getSplatMap().getTeam1BanColor());
                                    ban.setItemMeta(banMeta);

                                    e.getEntity().getWorld().dropItemNaturally(e.getDamager().getLocation(), ban);

                                } else if (bam.getBaseColor().equals(sPlayer.getSplatMap().getTeam2BanColor())) {

                                    team = 2;

                                    ItemStack ban = new ItemStack(Material.BANNER, 1);
                                    BannerMeta banMeta = (BannerMeta) ban.getItemMeta();
                                    banMeta.setBaseColor(sPlayer.getSplatMap().getTeam2BanColor());
                                    ban.setItemMeta(banMeta);

                                    e.getEntity().getWorld().dropItemNaturally(e.getDamager().getLocation(), ban);

                                }
                                if (team == 1) {


                                    for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "The enemy flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                        p.sendRawMessage(ChatColor.GREEN + "Your team's flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                }


                                if (team == 2) {


                                    for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "The enemy flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                        p.sendRawMessage(ChatColor.GREEN + "Your team's flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                }


                            }
                            if (sPlayer.getTeam() == 2) {

                                sPlayer.getSplatMap().respawn((Player) e.getEntity(), 1);

                            } else if (sPlayer.getTeam() == 1) {

                                sPlayer.getSplatMap().respawn((Player) e.getEntity(), 1);

                            }


                            for (Player p : sPlayer.getSplatMap().getTeam1()) {

                                if (!p.equals(e.getEntity()) && !p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.YELLOW + player.getName() + ChatColor.RED + " has splatted " +
                                                    ChatColor.YELLOW + e.getEntity().getName() + ChatColor.RED + "!");


                                }


                            }

                            for (Player p : sPlayer.getSplatMap().getTeam2()) {

                                if (!p.equals(e.getEntity()) && !p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.YELLOW + player.getName() + ChatColor.RED + " has splatted " +
                                                    ChatColor.YELLOW + e.getEntity().getName() + ChatColor.RED + "!");


                                }


                            }

                        } else {

                            SplatPlayer splat = SplatPlayer.getInstanceOfPlayer((Player) e.getEntity());
                            splat.setHit(true);
                            splat.setAssistOn(player.getUniqueId());

                        }
                    }

                }

            }
        }

    }
}
