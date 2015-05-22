package co.obam.ismooch.splatflag;

import co.obam.ismooch.splatflag.event.Click;
import co.obam.ismooch.splatflag.event.PickUp;
import co.obam.ismooch.splatflag.event.ProjectileHit;
import org.bukkit.*;
import org.bukkit.block.Banner;
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
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class SplatFlag extends JavaPlugin implements Listener {


    public static List<Location> powerUps = new ArrayList<Location>();
    public static List<Location> team1Spawn = new ArrayList<Location>();
    public static List<Location> team2Spawn = new ArrayList<Location>();
    public static List<String> splatLobbies = new ArrayList<String>();
    public static Map<String, String> splatLobbiesSize = new HashMap<String, String>();
    public static Map<String, Location> splatLobbiesSpawns = new HashMap<String, Location>();
    public static Map<String, List<Location>> splatLobbySigns = new HashMap<String, List<Location>>();
    public static Location team1Ban;
    public static Location team2Ban;
    public static Map<UUID, Integer> powerUpHit = new HashMap<UUID, Integer>();
    public static List<Player> team1 = new ArrayList<Player>();
    public static List<Player> team2 = new ArrayList<Player>();
    public static Map<Player, Player> playerHit = new HashMap<Player, Player>();
    static SplatFlag plugin;
    public static int check = 0;
    public static boolean balls = true;
    public static int team1Respawn = 15;
    public static int team2Respawn = 15;
    public static List<Player> playerShields = new ArrayList<Player>();


    public static boolean sfOn;


    public static SplatFlag getPlugin() {

        return plugin;

    }


    private static List<Location> splash(Location l, Integer team) {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int yCoord = (int) l.getY();

        List<Location> tempList = new ArrayList<Location>();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {

                    Location loc = new Location(w, xCoord + x, yCoord + y, zCoord + z);
                    if (team == 1 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) ||
                            loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if (new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);
                            loc.getBlock().setData((byte) 4);
                        }

                    } else if (team == 2 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) ||
                            loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if (new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);
                            loc.getBlock().setData((byte) 3);
                        }
                    }
                }


            }
        }
        return tempList;
    }


    public static void spawnTeam1(Player p) {


        int red = 255;
        int green = 195;
        int blue = 53;

        Location loc = team1Spawn.get(new Random().nextInt(team1Spawn.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());


        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta) lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta) llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta) lboots.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lboots.setItemMeta(lam);

        p.getInventory().setHelmet(lhelmet);
        p.getInventory().setChestplate(lchest);
        p.getInventory().setLeggings(llegs);
        p.getInventory().setBoots(lboots);

        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));

        p.teleport(loc);


    }

    public static void respawn(final Player p, final int team) {

        if (team == 1) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {


                    spawnTeam1(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendRawMessage(ChatColor.GREEN + "You have respawned!");


                }
            }, team1Respawn * 20);
        } else if (team == 2) {


            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {


                    spawnTeam2(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendRawMessage(ChatColor.GREEN + "You have respawned!");


                }
            }, team2Respawn * 20);
        }

    }

    public static void banRespawn(final int team) {


        Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (team == 1) {

                    spawnBanner(2);

                } else if (team == 2) {

                    spawnBanner(1);

                }
            }
        }, 30 * 20L);

    }

    public static String teamReturn(Player p) {

        if (team1.contains(p)) {

            return "team1";

        } else if (team2.contains(p)) {

            return "team2";

        } else {

            return null;
        }

    }

    public static void spawnTeam2(Player p) {

        int red = 0;
        int green = 128;
        int blue = 255;

        Location loc = team2Spawn.get(new Random().nextInt(team2Spawn.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta) lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta) llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta) lboots.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lboots.setItemMeta(lam);

        p.getInventory().setHelmet(lhelmet);
        p.getInventory().setChestplate(lchest);
        p.getInventory().setLeggings(llegs);
        p.getInventory().setBoots(lboots);


        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));

        p.teleport(loc);


    }

    public static void spawnBanner(int i) {


        if (i == 1) {

            Location loc = new Location(team1Ban.getWorld(), team1Ban.getX(), team1Ban.getY() + 1, team1Ban.getZ());
            ;
            Banner ban = (Banner) loc.getBlock().getState();
            ban.setBaseColor(DyeColor.BLUE);
            ban.update();

            for (Player player : team2) {

                player.sendRawMessage(ChatColor.GREEN + "Your flag has returned!");

            }

            for (Player player : team1) {

                player.sendRawMessage(ChatColor.GREEN + "The enemy's flag has returned!");

            }

        } else {

            Location loc = new Location(team2Ban.getWorld(), team2Ban.getX(), team2Ban.getY() + 1, team2Ban.getZ());
            Banner ban = (Banner) loc.getBlock().getState();
            ban.setBaseColor(DyeColor.YELLOW);
            ban.update();


            for (Player player : team1) {

                player.sendRawMessage(ChatColor.GREEN + "Your flag has returned!");

            }

            for (Player player : team2) {

                player.sendRawMessage(ChatColor.GREEN + "The enemy's flag has returned!");

            }
        }

    }


    @EventHandler
    public static void onLaunch(ProjectileLaunchEvent e) {

        if (!balls) {

            e.setCancelled(true);
            if (e.getEntity().getShooter() instanceof Player) {

                ((Player) e.getEntity().getShooter()).sendRawMessage(
                        ChatColor.RED + "Projectiles can not be launched right now!");

            }

        }

    }

    @EventHandler
    public static void onHit(ProjectileHitEvent e) {

        if (team1.contains(e.getEntity().getShooter())) {


            splash(e.getEntity().getLocation(), 1);

        } else if (team2.contains(e.getEntity().getShooter())) {

            splash(e.getEntity().getLocation(), 2);

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

        if (sfOn) {

            e.setCancelled(true);

        }


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

            if (sfOn) {
                e.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (sfOn) {

            e.setCancelled(true);

        }

    }


    @Override
    public void onEnable() {

        plugin = this;

        getServer().getPluginManager().registerEvents(new PickUp(), this);
        getServer().getPluginManager().registerEvents(new Click(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHit(), this);

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

        this.saveDefaultConfig();

        Location loc =
                new Location(Bukkit.getWorld("splatflag"), this.getConfig().getDouble("Flagstone.x"), this.getConfig().getDouble("Flagstone.y"), this.getConfig().getDouble("Flagstone.z"));


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
