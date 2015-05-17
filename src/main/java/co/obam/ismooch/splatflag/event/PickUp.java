package co.obam.ismooch.splatflag.event;

import co.obam.ismooch.splatflag.SplatFlag;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickUp implements Listener {

    @EventHandler
    public static void onPickUp(PlayerPickupItemEvent e) {


        if (e.getItem().getItemStack().equals(new ItemStack(Material.BANNER, 1, (short) 11))) {

            if (SplatFlag.team2.contains(e.getPlayer())) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 2);

                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 11));
                e.getItem().remove();
                e.setCancelled(true);

            } else if (SplatFlag.team1.contains(e.getPlayer())) {

                SplatFlag.spawnBanner(2);
                e.getItem().remove();
                e.setCancelled(true);
                if (SplatFlag.playerReturns.containsKey(e.getPlayer())) {

                    SplatFlag.playerReturns.put(e.getPlayer(), SplatFlag.playerReturns.get(e.getPlayer()) + 1);

                } else {

                    SplatFlag.playerReturns.put(e.getPlayer(), 1);

                }

            }

            if (SplatFlag.team2.contains(e.getPlayer())) {

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have recovered the enemy flag!");

                for (Player p : SplatFlag.team1) {

                    p.sendRawMessage(ChatColor.RED + "Your flag has been picked up by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

                for (Player p : SplatFlag.team2) {

                    if (!p.equals(e.getPlayer())) {

                        p.sendRawMessage(ChatColor.GREEN + "The enemy flag has been recovered by " + ChatColor.YELLOW +
                                e.getPlayer().getName());

                    }

                }

            } else if (SplatFlag.team1.contains(e.getPlayer())) {

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");

                for (Player p : SplatFlag.team1) {

                    if (!p.equals(e.getPlayer())) {
                        p.sendRawMessage(ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                e.getPlayer().getName());
                    }

                }

                for (Player p : SplatFlag.team2) {


                    p.sendRawMessage(ChatColor.RED + "The enemy flag has been returned by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

            }

        } else if (e.getItem().getItemStack().equals(new ItemStack(Material.BANNER, 1, (short) 4))) {

            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 2);

            if (SplatFlag.team1.contains(e.getPlayer())) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 5, 2);

                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 4));
                e.getItem().remove();
                e.setCancelled(true);

            } else if (SplatFlag.team2.contains(e.getPlayer())) {

                if (SplatFlag.playerReturns.containsKey(e.getPlayer())) {

                    SplatFlag.playerReturns.put(e.getPlayer(), SplatFlag.playerReturns.get(e.getPlayer()) + 1);

                } else {

                    SplatFlag.playerReturns.put(e.getPlayer(), 1);

                }
                SplatFlag.spawnBanner(1);
                e.getItem().remove();
                e.setCancelled(true);

            }


            if (SplatFlag.team1.contains(e.getPlayer())) {

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have recovered the enemy flag!");

                for (Player p : SplatFlag.team2) {

                    p.sendRawMessage(ChatColor.RED + "Your flag has been picked up by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

                for (Player p : SplatFlag.team1) {

                    if (!p.equals(e.getPlayer())) {

                        p.sendRawMessage(ChatColor.GREEN + " The enemy flag has been recovered by " + ChatColor.YELLOW +
                                e.getPlayer().getName());

                    }

                }

            } else if (SplatFlag.team2.contains(e.getPlayer())) {

                e.getPlayer().sendRawMessage(ChatColor.GREEN + "You have returned your team's flag!");


                for (Player p : SplatFlag.team2) {

                    if (!p.equals(e.getPlayer())) {
                        p.sendRawMessage(ChatColor.GREEN + "Your flag has been returned by " + ChatColor.YELLOW +
                                e.getPlayer().getName());
                    }

                }

                for (Player p : SplatFlag.team1) {


                    p.sendRawMessage(ChatColor.RED + "The enemy flag has been returned by " + ChatColor.YELLOW +
                            e.getPlayer().getName());

                }

            }

        }

    }
}
