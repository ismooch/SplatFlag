package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Click implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {

        if (e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            e.setCancelled(true);
            return;


        }

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.STANDING_BANNER)) {

                if (SplatFlag.team1.contains(e.getPlayer()) &&
                        e.getPlayer().getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 4)) &&
                        (SplatFlag.team2Ban.getX() - e.getClickedBlock().getX() == 0 ||
                                SplatFlag.team2Ban.getX() + e.getClickedBlock().getX() == 0)) {

                    if (((Banner) e.getClickedBlock().getState()).getBaseColor().equals(DyeColor.WHITE)) {

                        e.getPlayer().sendRawMessage(
                                ChatColor.RED + "You can not turn in a flag until your flag is returned!");
                        e.setCancelled(true);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                        return;

                    }
                    int red = 255;
                    int green = 195;
                    int blue = 53;

                    ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
                    LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
                    lam.setColor(Color.fromRGB(red, green, blue));
                    lhelmet.setItemMeta(lam);
                    e.getPlayer().getInventory().setHelmet(lhelmet);
                    e.getPlayer().updateInventory();
                    SplatFlag.team1Score++;
                    if (SplatFlag.playerCaptures.containsKey(e.getPlayer())) {

                        SplatFlag.playerCaptures.put(e.getPlayer(), SplatFlag.playerCaptures.get(e.getPlayer()) + 1);

                    } else {

                        SplatFlag.playerCaptures.put(e.getPlayer(), 1);

                    }

                    SplatFlag.banRespawn(2);

                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);

                    for (Player player : SplatFlag.team2) {

                        player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                " has captured your flag. It will respawn in 30 seconds!");


                    }

                    for (Player player : SplatFlag.team1) {

                        player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN +
                                " has captured the enemy flag! It will respawn in 30 seconds!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
                    }


                } else if (SplatFlag.team2.contains(e.getPlayer()) &&
                        e.getPlayer().getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 11)) &&
                        (SplatFlag.team1Ban.getX() - e.getClickedBlock().getX() == 0 ||
                                SplatFlag.team1Ban.getX() + e.getClickedBlock().getX() == 0)) {

                    int red = 0;
                    int green = 128;
                    int blue = 255;

                    if (((Banner) e.getClickedBlock().getState()).getBaseColor().equals(DyeColor.WHITE)) {

                        e.getPlayer().sendRawMessage(
                                ChatColor.RED + "You can not turn in a flag until your flag is returned!");
                        e.setCancelled(true);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                        return;

                    }


                    e.setCancelled(true);
                    ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
                    LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
                    lam.setColor(Color.fromRGB(red, green, blue));
                    lhelmet.setItemMeta(lam);
                    e.getPlayer().getInventory().setHelmet(lhelmet);
                    e.getPlayer().getInventory();
                    SplatFlag.team2Score++;
                    SplatFlag.banRespawn(1);
                    if (SplatFlag.playerCaptures.containsKey(e.getPlayer())) {

                        SplatFlag.playerCaptures.put(e.getPlayer(), SplatFlag.playerCaptures.get(e.getPlayer()) + 1);

                    } else {

                        SplatFlag.playerCaptures.put(e.getPlayer(), 1);

                    }

                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);

                    for (Player player : SplatFlag.team1) {

                        player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                " has captured your flag. It will respawn in 30 seconds!");


                    }

                    for (Player player : SplatFlag.team2) {

                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);

                        player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN +
                                " has captured the enemy flag! It will respawn in 30 seconds!");

                    }
                }

                Banner ban = (Banner) e.getClickedBlock().getState();


                if ((SplatFlag.team1.contains(e.getPlayer()) && ban.getBaseColor().equals(DyeColor.BLUE)) ||
                        (SplatFlag.team2.contains(e.getPlayer()) && ban.getBaseColor().equals(DyeColor.YELLOW)) ||
                        ban.getBaseColor().equals(DyeColor.WHITE)) {

                    if (ban.getBaseColor().equals(DyeColor.BLUE)) {


                        ItemStack helm = new ItemStack(Material.BANNER, 1, (short) 4);
                        e.getPlayer().getInventory().setHelmet(helm);
                        e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have taken the other team's flag!");
                        e.getPlayer().updateInventory();
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 1);
                        for (Player player : SplatFlag.team2) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                    " has stolen your flag!");

                        }

                        for (Player player : SplatFlag.team1) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN +
                                    " has stolen the other team's flag!");

                        }
                        ban.setBaseColor(DyeColor.WHITE);
                        ban.update();


                    } else if (ban.getBaseColor().equals(DyeColor.YELLOW)) {

                        ItemStack helm = new ItemStack(Material.BANNER, 1, (short) 11);
                        e.getPlayer().getInventory().setHelmet(helm);
                        e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have taken the other team's flag!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 1);
                        for (Player player : SplatFlag.team1) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                    " has stolen your flag!");

                        }

                        for (Player player : SplatFlag.team2) {

                            if (!player.equals(e.getPlayer())) {

                                player.sendRawMessage(
                                        ChatColor.GREEN + "The enemy flag has been seized by " + ChatColor.YELLOW +
                                                e.getPlayer().getName());

                            }
                        }
                        ban.setBaseColor(DyeColor.WHITE);
                        ban.update();

                    } else if (ban.getBaseColor().equals(DyeColor.WHITE)) {


                        if (SplatFlag.team1.contains(e.getPlayer()) &&
                                (SplatFlag.team2Ban.getX() + ban.getLocation().getX() == 0 ||
                                        SplatFlag.team2Ban.getX() - ban.getLocation().getX() == 0) &&
                                e.getPlayer().getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 14))) {


                            int red = 255;
                            int green = 195;
                            int blue = 53;

                            ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
                            LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
                            lam.setColor(Color.fromRGB(red, green, blue));
                            lhelmet.setItemMeta(lam);
                            e.getPlayer().getInventory().setHelmet(lhelmet);


                            SplatFlag.spawnBanner(2);
                            e.setCancelled(true);
                            for (Player player : SplatFlag.team1) {

                                if (!player.equals(e.getPlayer())) {
                                    player.sendRawMessage(
                                            ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                                    e.getPlayer().getName() + ChatColor.GREEN + "!");
                                }


                            }
                            e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");
                            e.getPlayer().getInventory().remove(new ItemStack(Material.BANNER));

                            for (Player player : SplatFlag.team2) {

                                player.sendRawMessage(ChatColor.RED + "The enemy team's flag has been returned by " +
                                        ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED + "!");


                            }

                        } else if (SplatFlag.team2.contains(e.getPlayer()) &&
                                (SplatFlag.team1Ban.getX() - ban.getLocation().getX() == 0 ||
                                        SplatFlag.team1Ban.getX() + ban.getLocation().getX() == 0) &&
                                e.getPlayer().getInventory().getHelmet().equals(new ItemStack(Material.BANNER, 1, (short) 4))) {
                            int red = 0;
                            int green = 128;
                            int blue = 255;


                            SplatFlag.spawnBanner(1);
                            e.setCancelled(true);
                            ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
                            LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
                            lam.setColor(Color.fromRGB(red, green, blue));
                            lhelmet.setItemMeta(lam);

                            e.getPlayer().getInventory().setHelmet(lhelmet);
                            for (Player player : SplatFlag.team2) {

                                if (!player.equals(e.getPlayer())) {
                                    player.sendRawMessage(
                                            ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                                    e.getPlayer().getName() + ChatColor.GREEN + "!");
                                }


                            }
                            e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");
                            e.getPlayer().getInventory().remove(new ItemStack(Material.BANNER));

                            for (Player player : SplatFlag.team1) {

                                player.sendRawMessage(ChatColor.RED + "The enemy team's flag has been returned by " +
                                        ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED + "!");


                            }

                        }

                    }


                }


            }
        }


    }
}
