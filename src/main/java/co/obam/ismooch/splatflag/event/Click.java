package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Click implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {

        if (e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            e.setCancelled(true);
            return;


        }

        if (SplatPlayer.getInstanceOfPlayer(e.getPlayer()).getLobby() != null) {

            SplatPlayer sPlayer = SplatPlayer.getInstanceOfPlayer(e.getPlayer());

            DyeColor color = sPlayer.getColor();


            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.STANDING_BANNER)) {

                    if (sPlayer.getTeam() == 1 && e.getPlayer().getInventory().getHelmet().getItemMeta() instanceof BannerMeta &&
                            (sPlayer.getSplatMap().getTeam2Ban().getX() - e.getClickedBlock().getX() == 0 ||
                                    sPlayer.getSplatMap().getTeam2Ban().getX() + e.getClickedBlock().getX() == 0) &&
                            (sPlayer.getSplatMap().getTeam2Ban().getZ() - e.getClickedBlock().getZ() == 0 ||
                                    sPlayer.getSplatMap().getTeam2Ban().getZ() + e.getClickedBlock().getZ() == 0)) {

                        if (((Banner) e.getClickedBlock().getState()).getBaseColor().equals(DyeColor.WHITE)) {

                            e.getPlayer().sendRawMessage(
                                    ChatColor.RED + "You can not turn in a flag until your flag is returned!");
                            e.setCancelled(true);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 5, 1);
                            return;

                        }
                        int red = sPlayer.getArmorColor().get(0);
                        int green = sPlayer.getArmorColor().get(1);
                        int blue = sPlayer.getArmorColor().get(2);

                        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
                        LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
                        lam.setColor(Color.fromRGB(red, green, blue));
                        lhelmet.setItemMeta(lam);
                        e.getPlayer().getInventory().setHelmet(lhelmet);
                        e.getPlayer().updateInventory();
                        sPlayer.getLobby().setTeam1Score(sPlayer.getLobby().getTeam1Score() + 1);

                        sPlayer.setCaptures(sPlayer.getCaptures() + 1);

                        sPlayer.getSplatMap().spawnBanner(1);

                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);

                        for (Player player : sPlayer.getSplatMap().getTeam2()) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                    " has captured your flag!");


                        }

                        for (Player player : sPlayer.getSplatMap().getTeam1()) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN +
                                    " has captured the enemy flag!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
                        }


                    } else if (sPlayer.getTeam() == 2 && e.getPlayer().getInventory().getHelmet().getItemMeta() instanceof BannerMeta &&
                            (sPlayer.getSplatMap().getTeam1Ban().getX() - e.getClickedBlock().getX() == 0 ||
                                    sPlayer.getSplatMap().getTeam1Ban().getX() + e.getClickedBlock().getX() == 0) &&
                            (sPlayer.getSplatMap().getTeam1Ban().getZ() - e.getClickedBlock().getZ() == 0 ||
                                    sPlayer.getSplatMap().getTeam1Ban().getZ() + e.getClickedBlock().getZ() == 0)) {

                        int red = sPlayer.getArmorColor().get(0);
                        int green = sPlayer.getArmorColor().get(1);
                        int blue = sPlayer.getArmorColor().get(2);

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
                        sPlayer.getLobby().setTeam2Score(sPlayer.getLobby().getTeam2Score());
                        sPlayer.getSplatMap().spawnBanner(1);

                        sPlayer.setCaptures(sPlayer.getCaptures() + 1);


                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);

                        for (Player player : sPlayer.getSplatMap().getTeam1()) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.RED +
                                    " has captured your flag.");


                        }

                        for (Player player : sPlayer.getSplatMap().getTeam2()) {

                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN +
                                    " has captured the enemy flag!");

                        }
                    }

                    Banner ban = (Banner) e.getClickedBlock().getState();

                    if (sPlayer.getTeam() == 1 && ban.getBaseColor().equals(sPlayer.getSplatMap().getTeam2BanColor())) {

                        ItemStack helm = new ItemStack(Material.BANNER, 1);
                        BannerMeta meta = (BannerMeta) helm.getItemMeta();
                        meta.setBaseColor(sPlayer.getSplatMap().getTeam2BanColor());
                        helm.setItemMeta(meta);
                        e.getPlayer().getInventory().setHelmet(helm);
                        e.getPlayer().updateInventory();
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1, 1);
                        e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have taken the enemy team's flag!");

                        for (Player player : sPlayer.getSplatMap().getTeam2()) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getDisplayName() + ChatColor.RED +
                                    " has stolen your flag!");

                        }

                        for (Player player : sPlayer.getSplatMap().getTeam1()) {

                            if (!e.getPlayer().equals(player)) {

                                player.sendRawMessage(
                                        ChatColor.YELLOW + e.getPlayer().getDisplayName() + ChatColor.GREEN +
                                                " has stolen the enemy team's flag!");

                            }

                        }

                        ban.setBaseColor(DyeColor.WHITE);
                        ban.update();


                    } else if (sPlayer.getTeam() == 2 &&
                            ban.getBaseColor().equals(sPlayer.getSplatMap().getTeam1BanColor())) {

                        ItemStack helm = new ItemStack(Material.BANNER, 1);
                        BannerMeta meta = (BannerMeta) helm.getItemMeta();
                        meta.setBaseColor(sPlayer.getSplatMap().getTeam1BanColor());
                        helm.setItemMeta(meta);
                        e.getPlayer().getInventory().setHelmet(helm);
                        e.getPlayer().updateInventory();
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1, 1);
                        e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have taken the enemy team's flag!");

                        for (Player player : sPlayer.getSplatMap().getTeam1()) {

                            player.sendRawMessage(ChatColor.YELLOW + e.getPlayer().getDisplayName() + ChatColor.RED +
                                    " has stolen your flag!");

                        }

                        for (Player player : sPlayer.getSplatMap().getTeam2()) {

                            if (!e.getPlayer().equals(player)) {

                                player.sendRawMessage(
                                        ChatColor.YELLOW + e.getPlayer().getDisplayName() + ChatColor.GREEN +
                                                " has stolen the enemy team's flag!");

                            }

                        }

                        ban.setBaseColor(DyeColor.WHITE);
                        ban.update();


                    }
                }
            }
        }
    }
}
