package co.obam.ismooch.splatflag;

import co.obam.ismooch.splatflag.effects.SplatEffects;
import co.obam.ismooch.splatflag.event.*;
import co.obam.ismooch.splatflag.loader.MapLoader;
import co.obam.ismooch.splatflag.objects.SplatPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class SplatFlag extends JavaPlugin implements Listener {


    public static List<String> splatLobbies = new ArrayList<String>();
    public static Map<String, String> splatLobbiesSize = new HashMap<String, String>();
    public static Map<String, Location> splatLobbiesSpawns = new HashMap<String, Location>();
    public static Map<String, List<Location>> splatLobbySigns = new HashMap<String, List<Location>>();
    static SplatFlag plugin;
    public static Location spawn;




    public static SplatFlag getPlugin() {

        return plugin;

    }








    @EventHandler
    public static void onHit(ProjectileHitEvent e) {

        if (SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).getTeam() == 1) {


            SplatEffects.splash(e.getEntity().getLocation(), SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()), 1);

        } else if (SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()).getTeam() == 2) {

            SplatEffects.splash(e.getEntity().getLocation(), SplatPlayer.getInstanceOfPlayer((Player) e.getEntity()), 2);

        }


    }

    @EventHandler
    public static void onDamage(EntityDamageEvent e) {

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {

            e.setCancelled(true);

        }


    }


    @EventHandler
    public static void onDrop(PlayerDropItemEvent e) {


            e.setCancelled(true);

    }


    @EventHandler
    public static void onPlace(BlockPlaceEvent e) {

        if (!e.getPlayer().hasPermission("obam.smod")) {
            if (e.getPlayer().getWorld().getName().equals("splatflag")) {

                e.setCancelled(true);
                for (ItemStack item : e.getPlayer().getInventory().getContents()) {

                    if (!item.getType().equals(Material.LEATHER_BOOTS) &&
                            !item.getType().equals(Material.LEATHER_CHESTPLATE) &&
                            !item.getType().equals(Material.LEATHER_HELMET) &&
                            !item.getType().equals(Material.LEATHER_LEGGINGS) &&
                            !item.getType().equals(Material.SNOW_BALL)) {

                        e.getPlayer().getInventory().remove(item);

                    }

                }

            }
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (!e.getPlayer().hasPermission("obam.smod")) {

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (!e.getWhoClicked().hasPermission("obam.smod")) {

            e.setCancelled(true);

        }

    }


    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        plugin = this;

        getServer().getPluginManager().registerEvents(new PickUp(), this);
        getServer().getPluginManager().registerEvents(new Click(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
        getServer().getPluginManager().registerEvents(new Disconnect(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);

        MapLoader.loadWorlds();

        Configuration config = getConfig();

        splatLobbies = config.getStringList("Lobbies");


        for (String s : splatLobbies) {

            String locString = config.getString("lobby-locations." + s);

            String[] locSplit = locString.split(":");

            Location loc =
                    new Location(Bukkit.getWorld(locSplit[0]), Double.parseDouble(locSplit[1]), Double.parseDouble(locSplit[2]), Double.parseDouble(locSplit[3]));

            splatLobbiesSpawns.put(s, loc);
        }

        for (String s : splatLobbies) {

            List<String> locString = config.getStringList("lobby-signs." + s);

            List<Location> locations = new ArrayList<Location>();

            for (String s2 : locString) {

                String[] s2Split = s2.split(":");

                Location loc =
                        new Location(Bukkit.getWorld(s2Split[0]), Double.parseDouble(s2Split[1]), Double.parseDouble(s2Split[2]), Double.parseDouble(s2Split[3]));

                locations.add(loc);

            }

            splatLobbySigns.put(s, locations);


        }

        for (String s : splatLobbies) {

            splatLobbiesSize.put(s, config.getString("lobby-size." + s));

        }

        getServer().getPluginManager().registerEvents(this, this);





    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player;
        Set<Material> mat = new HashSet<Material>();


        if (sender instanceof Player) {

            player = (Player) sender;

        } else {

            sender.sendMessage("You need be a player to do that!");
            return false;

        }
        if (cmd.getName().equalsIgnoreCase("sf")) {

            if (args.length < 1) {

                player.sendRawMessage(ChatColor.RED + "You need to use an argument!");
                return true;

            } else if (args[0].equalsIgnoreCase("lobby")) {

                if (args.length < 2) {

                    player.sendRawMessage(ChatColor.RED + "You need to use an argument!");
                    return true;

                } else if (args[1].equalsIgnoreCase("list")) {

                    player.sendRawMessage(
                            ChatColor.GREEN + "Name " + ChatColor.YELLOW + " ------ " + ChatColor.GREEN + "Size");

                    for (String s : splatLobbies) {

                        player.sendRawMessage(ChatColor.GREEN + s + ChatColor.YELLOW + " ------ " + ChatColor.GREEN +
                                splatLobbiesSize.get(s));

                    }

                    return true;

                } else if (args[1].equalsIgnoreCase("add")) {

                    if (args.length < 3) {

                        player.sendRawMessage("You need to specify a name!");
                        return true;

                    } else {

                        String name = args[2];

                        List<String> oldList = getConfig().getStringList("Lobbies");

                        oldList.add(name);

                        getConfig().set("Lobbies", oldList);

                        getConfig().set("lobby-locations." + name, "world:0:64:0");

                        getConfig().set("lobby-size." + name, "large");

                        List<String> hold = new ArrayList<String>();

                        hold.add("world:0:64:0");

                        getConfig().set("lobby-signs." + name, hold);

                        saveConfig();

                        reloadConfig();

                        player.sendRawMessage(
                                ChatColor.YELLOW + name + ChatColor.GREEN + " has successfully been added!");

                        player.sendRawMessage(
                                ChatColor.GREEN + "Please remember to set up signs, spawn location, and size!");

                        player.sendRawMessage(ChatColor.GREEN + "Remember to reload for changes to take affect!");

                        return true;


                    }

                } else if (args[1].equalsIgnoreCase("remove")) {

                    if (args.length < 3) {

                        player.sendRawMessage(ChatColor.RED + "You need to specify a lobby!");

                    } else {

                        String name = args[2];

                        List<String> oldList = getConfig().getStringList("Lobbies");

                        if (!oldList.contains(name)) {

                            player.sendRawMessage(ChatColor.RED + "That is not a valid lobby name!");
                            return true;

                        } else {

                            oldList.remove(name);

                            getConfig().set("Lobbies", oldList);

                            saveConfig();

                            reloadConfig();

                            player.sendRawMessage(
                                    ChatColor.YELLOW + name + ChatColor.GREEN + " has successfully been removed!");

                            player.sendRawMessage(
                                    ChatColor.GREEN + "Please reload the plugin for changes to take affect!");

                        }

                    }

                } else if (args[1].equalsIgnoreCase("size")) {

                    if (args.length < 3) {

                        player.sendRawMessage(
                                ChatColor.RED + "You need to specify a lobby to alter! " + ChatColor.YELLOW +
                                        "/sf lobby size <lobby> <size>");

                        return true;

                    } else if (args.length < 4) {

                        player.sendRawMessage(
                                ChatColor.RED + "You need to specify a size! " + ChatColor.YELLOW + "/sf lobby size " +
                                        args[2] + " <size>");

                        return true;

                    } else if (!splatLobbies.contains(args[2])) {

                        player.sendRawMessage(ChatColor.RED + "That is not a valid lobby name!");

                        return true;

                    } else if (!args[3].equalsIgnoreCase("large") && !args[3].equalsIgnoreCase("small") &&
                            !args[3].equalsIgnoreCase("medium")) {

                        player.sendRawMessage(
                                ChatColor.YELLOW + args[3] + ChatColor.RED + " is not a valid lobby size! " +
                                        ChatColor.YELLOW + "Large|Medium|Small");

                        return true;

                    } else {

                        splatLobbiesSize.put(args[2], args[3]);

                        getConfig().set("lobby-size." + args[2], args[3]);

                        saveConfig();

                        reloadConfig();

                        player.sendRawMessage(ChatColor.YELLOW + args[2] + "'s" + ChatColor.GREEN +
                                " size has succesfully been set to " + ChatColor.YELLOW + args[3]);

                        return true;

                    }

                } else if (args[1].equalsIgnoreCase("sign")) {

                    if (args.length < 3) {

                        player.sendRawMessage(ChatColor.RED + "You need to specify a lobby!" + ChatColor.YELLOW +
                                " /sf lobby sign <lobby> <add|remove>");

                        return true;

                    } else if (args.length < 4) {

                        player.sendRawMessage(ChatColor.RED + "You need to specify an action!");

                        return true;

                    } else if (!splatLobbies.contains(args[2])) {

                        player.sendRawMessage(ChatColor.RED + "That is not a valid lobby name!");

                        return true;

                    } else if (!(player.getTargetBlock(mat, 10) instanceof Sign)) {

                        player.sendRawMessage(ChatColor.RED + "You must be targeting a sign!");

                        return true;

                    } else if (!args[3].equalsIgnoreCase("add") && !args[3].equalsIgnoreCase("remove")) {

                        player.sendRawMessage(
                                ChatColor.RED + "That is not a valid action! " + ChatColor.YELLOW + "add|remove");

                        return true;

                    } else if (args[3].equalsIgnoreCase("add")) {

                        Block block = player.getTargetBlock(mat, 10);
                        Location loc = block.getLocation();
                        List<String> oldList = getConfig().getStringList("lobby-signs." + args[2]);

                        String locString = String.valueOf(
                                loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ());

                        oldList.add(locString);

                        getConfig().set("lobby-signs." + args[2], oldList);

                        saveConfig();

                        reloadConfig();

                        player.sendRawMessage(
                                ChatColor.GREEN + "Sign successuflly added for " + ChatColor.YELLOW + args[2] +
                                        ChatColor.GREEN + "!");

                        player.sendRawMessage(
                                ChatColor.GREEN + "Please remember to reload for changes to take affect!");

                        return true;

                    } else if (args[3].equalsIgnoreCase("remove")) {

                        List<String> list = new ArrayList<String>();

                        getConfig().set("lobby-signs." + args[2], list);

                        saveConfig();

                        reloadConfig();

                        player.sendRawMessage(ChatColor.GREEN + "All lobby signs for " + ChatColor.YELLOW + args[2] +
                                ChatColor.GREEN + " have been removed!");

                        player.sendRawMessage(
                                ChatColor.GREEN + "Please remember to reload for changes to take affect!");

                        return true;

                    }

                } else if (args[1].equalsIgnoreCase("spawn")) {

                    if (args.length < 3) {

                        player.sendRawMessage(ChatColor.RED + "You must specify a lobby!");

                        return true;

                    } else if (!splatLobbies.contains(args[1])) {

                        player.sendRawMessage(
                                ChatColor.YELLOW + args[2] + ChatColor.RED + " is not a valid lobby name!");

                        return true;

                    } else {


                        Location loc = player.getLocation();

                        String locString =
                                String.valueOf(loc.getWorld() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ());

                        getConfig().set("lobby-locations." + args[2], locString);

                        saveConfig();

                        reloadConfig();

                        player.sendRawMessage(
                                ChatColor.GREEN + "Spawn location for " + ChatColor.YELLOW + args[2] + ChatColor.GREEN +
                                        " has been set to " + ChatColor.YELLOW + loc.toString() + ChatColor.GREEN +
                                        "!");

                        return true;

                    }


                }

            }


        }

        return false;

    }
}
