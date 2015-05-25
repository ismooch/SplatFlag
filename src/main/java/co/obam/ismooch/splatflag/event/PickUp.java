package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class PickUp implements Listener {

    @EventHandler
    public static void onPickUp(PlayerPickupItemEvent e) {

        SplatPlayer splatPlayer = SplatPlayer.getInstanceOfPlayer(e.getPlayer());


        if (((BannerMeta) e.getItem().getItemStack().getItemMeta()).getBaseColor().equals(splatPlayer.getSplatMap().getTeam1BanColor())) {

            if (splatPlayer.getTeam() == 2) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 2);

                ItemStack ban = new ItemStack(Material.BANNER, 1);
                BannerMeta bam = (BannerMeta) ban.getItemMeta();
                bam.setBaseColor(splatPlayer.getSplatMap().getTeam1BanColor());
                ban.setItemMeta(bam);

                e.getPlayer().getInventory().setHelmet(ban);
                e.getItem().remove();
                e.setCancelled(true);

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have recovered the enemy flag!");

                for (Player p : splatPlayer.getSplatMap().getTeam1()) {

                    p.sendRawMessage(ChatColor.RED + "Your flag has been picked up by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

                for (Player p : splatPlayer.getSplatMap().getTeam2()) {

                    if (!p.equals(e.getPlayer())) {

                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has been recovered by " + ChatColor.YELLOW +
                                e.getPlayer().getName());

                    }

                }

            } else if (splatPlayer.getTeam() == 1) {

                splatPlayer.getSplatMap().spawnBanner(1);
                e.getItem().remove();
                e.setCancelled(true);
                splatPlayer.setReturns(splatPlayer.getReturns() + 1);

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");

                for (Player p : splatPlayer.getSplatMap().getTeam1()) {

                    if (!p.equals(e.getPlayer())) {
                        p.sendRawMessage(ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                e.getPlayer().getName());
                    }

                }

                for (Player p : splatPlayer.getSplatMap().getTeam2()) {


                    p.sendRawMessage(ChatColor.RED + "The enemy flag has been returned by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

            }


        }


        if (((BannerMeta) e.getItem().getItemStack().getItemMeta()).getBaseColor().equals(splatPlayer.getSplatMap().getTeam2BanColor())) {

            if (splatPlayer.getTeam() == 1) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 2);

                ItemStack ban = new ItemStack(Material.BANNER, 1);
                BannerMeta bam = (BannerMeta) ban.getItemMeta();
                bam.setBaseColor(splatPlayer.getSplatMap().getTeam2BanColor());
                ban.setItemMeta(bam);

                e.getPlayer().getInventory().setHelmet(ban);
                e.getItem().remove();
                e.setCancelled(true);

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have recovered the enemy flag!");

                for (Player p : splatPlayer.getSplatMap().getTeam2()) {

                    p.sendRawMessage(ChatColor.RED + "Your flag has been picked up by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

                for (Player p : splatPlayer.getSplatMap().getTeam1()) {

                    if (!p.equals(e.getPlayer())) {

                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has been recovered by " + ChatColor.YELLOW +
                                e.getPlayer().getName());

                    }

                }

            } else if (splatPlayer.getTeam() == 2) {

                splatPlayer.getSplatMap().spawnBanner(2);
                e.getItem().remove();
                e.setCancelled(true);
                splatPlayer.setReturns(splatPlayer.getReturns() + 1);

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");

                for (Player p : splatPlayer.getSplatMap().getTeam2()) {

                    if (!p.equals(e.getPlayer())) {
                        p.sendRawMessage(ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                e.getPlayer().getName());
                    }

                }

                for (Player p : splatPlayer.getSplatMap().getTeam1()) {


                    p.sendRawMessage(ChatColor.RED + "The enemy flag has been returned by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

            }


        }
    }
}
