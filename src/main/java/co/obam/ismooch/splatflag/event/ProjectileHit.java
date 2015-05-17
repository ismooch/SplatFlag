package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class ProjectileHit implements Listener {

    @EventHandler
    public static void onProjegtileHit(EntityDamageByEntityEvent e) {

        Projectile ball;
        Player player;
        if (e.getDamager() instanceof Snowball && ((Snowball) e.getDamager()).getShooter() instanceof Player) {
            ball = (Projectile) e.getDamager();
            player = (Player) ball.getShooter();





            if (e.getEntity() instanceof Chicken) {

                if (SplatFlag.powerUpHit.containsKey(e.getEntity().getUniqueId())) {


                    if (SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) < 5) {


                        SplatFlag.powerUpHit.put(e.getEntity().getUniqueId(),
                                SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        if (SplatFlag.playerPowerups.containsKey(player)) {

                            SplatFlag.playerPowerups.put(player, SplatFlag.playerPowerups.get(player) + 1);


                        } else {

                            SplatFlag.playerPowerups.put(player, 1);

                        }

                        e.getEntity().remove();
                        if (SplatFlag.team1.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Shield " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team1) {

                                SplatFlag.playerShields.add(p);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Shield " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : SplatFlag.team2) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (SplatFlag.team2.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Shield " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team2) {

                                SplatFlag.playerShields.add(p);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Shield " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : SplatFlag.team1) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }

            if (e.getEntity() instanceof IronGolem) {

                if (SplatFlag.powerUpHit.containsKey(e.getEntity().getUniqueId())) {


                    if (SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) < 5) {


                        SplatFlag.powerUpHit.put(e.getEntity().getUniqueId(),
                                SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        if (SplatFlag.playerPowerups.containsKey(player)) {

                            SplatFlag.playerPowerups.put(player, SplatFlag.playerPowerups.get(player) + 1);


                        } else {

                            SplatFlag.playerPowerups.put(player, 1);

                        }

                        e.getEntity().remove();
                        if (SplatFlag.team1.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Reload " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team1) {

                                p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
                                p.updateInventory();
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Reload " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : SplatFlag.team2) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (SplatFlag.team2.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Reload " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team2) {

                                p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
                                p.updateInventory();
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : SplatFlag.team1) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }

            if (e.getEntity() instanceof Pig) {

                if (SplatFlag.powerUpHit.containsKey(e.getEntity().getUniqueId())) {


                    if (SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) < 5) {


                        SplatFlag.powerUpHit.put(e.getEntity().getUniqueId(),
                                SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        if (SplatFlag.playerPowerups.containsKey(player)) {

                            SplatFlag.playerPowerups.put(player, SplatFlag.playerPowerups.get(player) + 1);


                        } else {

                            SplatFlag.playerPowerups.put(player, 1);

                        }

                        e.getEntity().remove();
                        if (SplatFlag.team1.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Respawn " +
                                    ChatColor.GREEN + "powerup for your team!");

                            SplatFlag.team1Respawn = 10;


                            for (Player p : SplatFlag.team1) {


                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Respawn " +
                                                    ChatColor.GREEN + " power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : SplatFlag.team2) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                                @Override
                                public void run() {


                                    SplatFlag.team1Respawn = 15;
                                    for(Player p : SplatFlag.team1){

                                        p.sendRawMessage(ChatColor.GOLD + "Your respawn powerup has worn off!");

                                    }

                                }
                            }, 30 * 20L);

                        } else if (SplatFlag.team2.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Respawn " +
                                    ChatColor.GREEN + "powerup for your team!");

                            SplatFlag.team2Respawn = 10;


                            for (Player p : SplatFlag.team2) {


                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Respawn " +
                                                    ChatColor.GREEN + " power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : SplatFlag.team1) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                                @Override
                                public void run() {


                                    SplatFlag.team2Respawn = 15;
                                    for(Player p : SplatFlag.team1){

                                        p.sendRawMessage(ChatColor.GOLD + "Your respawn powerup has worn off!");

                                    }

                                }
                            }, 30 * 20L);

                        }

                    }

                }

            }

            if (e.getEntity() instanceof Cow) {

                if (SplatFlag.powerUpHit.containsKey(e.getEntity().getUniqueId())) {


                    if (SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) < 5) {


                        SplatFlag.powerUpHit.put(e.getEntity().getUniqueId(),
                                SplatFlag.powerUpHit.get(e.getEntity().getUniqueId()) + 1);

                    } else {

                        if (SplatFlag.playerPowerups.containsKey(player)) {

                            SplatFlag.playerPowerups.put(player, SplatFlag.playerPowerups.get(player) + 1);


                        } else {

                            SplatFlag.playerPowerups.put(player, 1);

                        }

                        e.getEntity().remove();
                        if (SplatFlag.team1.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Speed " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team1) {

                                PotionEffect pt = new PotionEffect(PotionEffectType.SPEED, 30 * 20, 2, false, false);
                                p.addPotionEffect(pt);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }


                            }

                            for (Player p : SplatFlag.team2) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        } else if (SplatFlag.team2.contains(player)) {

                            player.sendRawMessage(ChatColor.GREEN + "You have acquired a " + ChatColor.GOLD + "Speed " +
                                    ChatColor.GREEN + "powerup for your team!");
                            for (Player p : SplatFlag.team2) {

                                PotionEffect pt = new PotionEffect(PotionEffectType.SPEED, 30 * 20, 2, false, false);
                                p.addPotionEffect(pt);
                                if (!p.equals(player)) {

                                    p.sendRawMessage(
                                            ChatColor.GREEN + "Your team has received a " + ChatColor.GOLD + "Speed " +
                                                    ChatColor.GREEN + "power up from " + ChatColor.YELLOW +
                                                    player.getName());

                                }

                            }

                            for (Player p : SplatFlag.team1) {

                                p.sendRawMessage(ChatColor.RED + "The other team has received a powerup!");

                            }

                        }

                    }

                }

            }
            if (e.getEntity() instanceof Player) {

                if ((SplatFlag.team1.contains(e.getEntity()) && SplatFlag.team2.contains(player)) ||
                        (SplatFlag.team2.contains(e.getEntity())) && SplatFlag.team1.contains(player)) {

                    player.playSound(player.getLocation(), Sound.SLIME_WALK, 10, -1);
                    ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.SLIME_WALK, 10, -1);

                    if(SplatFlag.playerShields.contains((Player) e.getEntity())){

                        SplatFlag.playerShields.remove((Player) e.getEntity());
                        ((Player) e.getEntity()).sendRawMessage(ChatColor.RED + "Your shield has been broken!");
                        player.sendRawMessage(ChatColor.GREEN + "You have broken the shield of " + ChatColor.YELLOW + ((Player) e.getEntity()).getDisplayName() + ChatColor.GREEN + "!");

                    }else if (SplatFlag.playerHit.containsKey(e.getEntity())) {

                        if (!SplatFlag.playerDeaths.containsKey(e.getEntity())) {

                            SplatFlag.playerDeaths.put((Player) e.getEntity(), 1);

                        } else {

                            SplatFlag.playerDeaths.put((Player) e.getEntity(),
                                    SplatFlag.playerDeaths.get((Player) e.getEntity()) + 1);

                        }

                        if (SplatFlag.team1.contains(player)) {


                            Location loc = player.getLocation();
                            Location loc2 = SplatFlag.team2Ban;
                            if (((loc.getX() - loc2.getX() >= -7 && loc.getX() - loc2.getX() <= 7) ||
                                    (loc.getX() + loc2.getX() <= 7 && loc.getX() + loc2.getX() >= -7)) &&
                                    ((loc.getY() - loc2.getY() >= -7 && loc.getY() - loc2.getY() <= 7) ||
                                            (loc.getY() + loc2.getY() <= 7 && loc.getY() + loc2.getY() >= -7)) &&
                                    ((loc.getZ() - loc2.getZ() >= -7 && loc.getZ() - loc2.getZ() <= 7) ||
                                            (loc.getZ() + loc2.getZ() <= 7 && loc.getZ() + loc2.getZ() >= -7))) {

                                if (!SplatFlag.playerDefends.containsKey(player)) {

                                    SplatFlag.playerDefends.put(player, 1);

                                } else {

                                    SplatFlag.playerDefends.put(player, SplatFlag.playerDefends.get(player) + 1);

                                }

                            }

                        } else if (SplatFlag.team2.contains(player)) {

                            Location loc = player.getLocation();
                            Location loc2 = SplatFlag.team1Ban;

                            if (((loc.getX() - loc2.getX() >= -7 && loc.getX() - loc2.getX() <= 7) ||
                                    (loc.getX() + loc2.getX() <= 7 && loc.getX() + loc2.getX() >= -7)) &&
                                    ((loc.getY() - loc2.getY() >= -7 && loc.getY() - loc2.getY() <= 7) ||
                                            (loc.getY() + loc2.getY() <= 7 && loc.getY() + loc2.getY() >= -7)) &&
                                    ((loc.getZ() - loc2.getZ() >= -7 && loc.getZ() - loc2.getZ() <= 7) ||
                                            (loc.getZ() + loc2.getZ() <= 7 && loc.getZ() + loc2.getZ() >= -7))) {

                                if (!SplatFlag.playerDefends.containsKey(player)) {

                                    SplatFlag.playerDefends.put(player, 1);

                                } else {

                                    SplatFlag.playerDefends.put(player, SplatFlag.playerDefends.get(player) + 1);

                                }

                            }

                        }


                        if (SplatFlag.team1.contains(player)) {


                            Location loc2 = null;
                            for (Player p : SplatFlag.team1) {

                                if (!player.equals(p)) {

                                    if (p.getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 4))) {

                                        loc2 = p.getLocation();

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

                                    if (!SplatFlag.playerProtects.containsKey(player)) {

                                        SplatFlag.playerProtects.put(player, 1);

                                    } else {

                                        SplatFlag.playerProtects.put(player, SplatFlag.playerProtects.get(player) + 1);

                                    }

                                }
                            }

                        } else if (SplatFlag.team2.contains(player)) {


                            Location loc2 = null;

                            for (Player p : SplatFlag.team2) {

                                if (!player.equals(p)) {

                                    if (p.getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 4))) {

                                        loc2 = p.getLocation();

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

                                    if (!SplatFlag.playerProtects.containsKey(player)) {

                                        SplatFlag.playerProtects.put(player, 1);

                                    } else {

                                        SplatFlag.playerProtects.put(player, SplatFlag.playerProtects.get(player) + 1);

                                    }

                                }
                            }

                        }
                        if (!player.equals(SplatFlag.playerHit.get(e.getEntity()))) {

                            if (!SplatFlag.playerAssists.containsKey(SplatFlag.playerHit.get(e.getEntity()))) {

                                SplatFlag.playerAssists.put(SplatFlag.playerHit.get(e.getEntity()), 1);

                            } else {

                                SplatFlag.playerAssists.put(SplatFlag.playerHit.get(e.getEntity()),
                                        SplatFlag.playerAssists.get(SplatFlag.playerHit.get(e.getEntity())) + 1);

                            }

                        }
                        SplatFlag.playerHit.remove(e.getEntity());

                        ((Player) e.getEntity()).setGameMode(GameMode.SPECTATOR);

                        e.getEntity().teleport(player.getLocation());

                        if (!SplatFlag.playerKills.containsKey(player)) {

                            SplatFlag.playerKills.put(player, 1);

                        } else {

                            SplatFlag.playerKills.put(player, SplatFlag.playerKills.get(player) + 1);

                        }

                        if (!SplatFlag.playerStreak.containsKey(player)) {

                            SplatFlag.playerStreak.put(player, 1);

                        } else {

                            SplatFlag.playerStreak.put(player, SplatFlag.playerStreak.get(player) + 1);

                        }

                        if (SplatFlag.playerStreak.containsKey(e.getEntity())) {

                            SplatFlag.playerStreak.remove(e.getEntity());

                        }

                        if (SplatFlag.playerStreak.get(player) == 5) {

                            for (Player p : SplatFlag.team1) {

                                p.sendRawMessage(
                                        ChatColor.YELLOW + player.getName() + ChatColor.GOLD + " is dominating!");

                            }
                            for (Player p : SplatFlag.team2) {

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

                        if(SplatFlag.team1.contains((Player) e.getEntity())){

                            ((Player) e.getEntity()).sendRawMessage(
                                    ChatColor.GREEN + "You will respawn in " + ChatColor.YELLOW + SplatFlag.team1Respawn + ChatColor.GREEN +
                                            " seconds!");

                        }else if(SplatFlag.team2.contains((Player) e.getEntity())){

                            ((Player) e.getEntity()).sendRawMessage(
                                    ChatColor.GREEN + "You will respawn in " + ChatColor.YELLOW + SplatFlag.team2Respawn + ChatColor.GREEN +
                                            " seconds!");


                        }

                        int team = 0;

                        if (((Player) e.getEntity()).getInventory().getHelmet().getType().equals(Material.BANNER)) {

                            if (((Player) e.getEntity()).getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 11))) {

                                e.getEntity().getWorld().dropItemNaturally(e.getDamager().getLocation(), new ItemStack(Material.BANNER, 1, (short) 11));
                                team = 1;

                            } else if (((Player) e.getEntity()).getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 4))) {

                                e.getEntity().getWorld().dropItemNaturally(e.getDamager().getLocation(), new ItemStack(Material.BANNER, 1, (short) 4));
                                team = 2;

                            }
                            if (team == 1) {

                                if (SplatFlag.team1.contains(e.getEntity())) {

                                    for (Player p : SplatFlag.team1) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "Your team's flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : SplatFlag.team2) {

                                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                } else if (SplatFlag.team2.contains(e.getEntity())) {

                                    for (Player p : SplatFlag.team2) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "The enemy flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : SplatFlag.team1) {

                                        p.sendRawMessage(ChatColor.GREEN + "Your team's flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                }

                            }

                            if (team == 2) {

                                if (SplatFlag.team2.contains(e.getEntity())) {

                                    for (Player p : SplatFlag.team2) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "Your team's flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : SplatFlag.team1) {

                                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                } else if (SplatFlag.team1.contains(e.getEntity())) {

                                    for (Player p : SplatFlag.team1) {

                                        if (!p.equals(e.getEntity())) {

                                            p.sendRawMessage(ChatColor.RED + "The enemy flag has been dropped by " +
                                                    ChatColor.YELLOW + e.getEntity().getName());

                                        }

                                    }

                                    for (Player p : SplatFlag.team2) {

                                        p.sendRawMessage(ChatColor.GREEN + "Your team's flag has been dropped by " +
                                                ChatColor.YELLOW + e.getEntity().getName());

                                    }

                                }

                            }

                        }
                        if (SplatFlag.team1.contains(e.getEntity())) {

                            SplatFlag.respawn((Player) e.getEntity(), 1);

                        } else if (SplatFlag.team2.contains(e.getEntity())) {

                            SplatFlag.respawn((Player) e.getEntity(), 2);

                        }


                        for (Player p : Bukkit.getWorld("splatflag").getPlayers()) {

                            if (!p.equals(e.getEntity()) && !p.equals(player)) {

                                p.sendRawMessage(
                                        ChatColor.YELLOW + player.getName() + ChatColor.RED + " has splatted " +
                                                ChatColor.YELLOW + e.getEntity().getName() + ChatColor.RED + "!");


                            }


                        }

                    } else {

                        SplatFlag.playerHit.put((Player) e.getEntity(), player);

                    }

                }

            }
        }

    }
}
