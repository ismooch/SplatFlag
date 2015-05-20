package co.obam.ismooch.splatflag;

import co.obam.ismooch.splatflag.event.Click;
import co.obam.ismooch.splatflag.event.PickUp;
import co.obam.ismooch.splatflag.event.ProjectileHit;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.block.Banner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;


public class SplatFlag extends JavaPlugin implements Listener {



    public static List<Location> powerUps = new ArrayList<Location>();
    public static List<Location> team1Spawn = new ArrayList<Location>();
    public static List<Location> team2Spawn = new ArrayList<Location>();
    public static Location team1Ban;
    public static Location team2Ban;
    public static Map<UUID, Integer> powerUpHit = new HashMap<UUID, Integer>();
    public static List<Player> team1 = new ArrayList<Player>();
    public static List<Player> team2 = new ArrayList<Player>();
    public static Map<Player, Player> playerHit = new HashMap<Player, Player>();
    static SplatFlag plugin;
    public static Map<Player, Integer> playerKills = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerAssists = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerStreak = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerReturns = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerDeaths = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerPowerups = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerDefends = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerProtects = new HashMap<Player, Integer>();
    public static Map<Player, Integer> playerCaptures = new HashMap<Player, Integer>();
    public static int team1Score = 0;
    public static int team2Score = 0;
    public static int check = 0;
    public static boolean balls = true;
    public static int team1Respawn = 15;
    public static int team2Respawn = 15;
    public static List<Player> playerShields = new ArrayList<Player>();


    public static boolean sfOn;




    public static SplatFlag getPlugin(){

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
                    if (team == 1 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) || loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if(new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);
                            loc.getBlock().setData((byte) 4);
                        }

                    } else if (team == 2 && (loc.getBlock().getType().equals(Material.QUARTZ_BLOCK) || loc.getBlock().getType().equals(Material.STAINED_CLAY))) {

                        if(new Random().nextInt(100) < 50) {
                            loc.getBlock().setType(Material.STAINED_CLAY);
                            loc.getBlock().setData((byte) 3);
                        }
                    }
                }


            }
        }
        return tempList;
    }


    private static void flagStone(Location l, int radius) {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int yCoord = (int) l.getY();
        powerUps.clear();
        team1Spawn.clear();
        team2Spawn.clear();
        System.out.println("[SplatFlag] Flagstone initialized..");

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {

                    if(new Location(w,xCoord + x, yCoord + y, zCoord + z).getBlock().getType().equals(Material.EMERALD_BLOCK)){

                        powerUps.add(new Location(w, xCoord + x, yCoord + y, zCoord + z));

                    }

                    if(new Location(w,xCoord + x, yCoord + y, zCoord + z).getBlock().getType().equals(Material.PUMPKIN)){

                        team1Spawn.add(new Location(w, xCoord + x, yCoord + y, zCoord + z));

                    }
                    if(new Location(w,xCoord + x, yCoord + y, zCoord + z).getBlock().getType().equals(Material.IRON_BLOCK)){

                        team1Ban = new Location(w, xCoord + x, yCoord + y, zCoord + z);

                    }

                    if(new Location(w,xCoord + x, yCoord + y, zCoord + z).getBlock().getType().equals(Material.LAPIS_BLOCK)){

                       team2Spawn.add(new Location(w, xCoord + x, yCoord + y, zCoord + z));

                    }

                    if(new Location(w,xCoord + x, yCoord + y, zCoord + z).getBlock().getType().equals(Material.SAND)){

                        team2Ban = new Location(w, xCoord + x, yCoord + y, zCoord + z);

                    }


                }

            }
        }

    }


    public static void powerUpSpawn() {

        Location loc = powerUps.get(new Random().nextInt(powerUps.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

        int ran = new Random().nextInt(100);
        if(ran < 25) {

            Cow power = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
            power.setCustomName(ChatColor.YELLOW + "Speed Buff");
            powerUpHit.put(power.getUniqueId(), 0);

            for(Player p : Bukkit.getOnlinePlayers()){

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Speed " + ChatColor.GOLD + "powerup has spawned!");

            }

        }else if(ran >= 25 && ran < 50){

            Chicken power = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
            power.setCustomName(ChatColor.YELLOW + "Shield");
            powerUpHit.put(power.getUniqueId(), 0);

            for(Player p : Bukkit.getOnlinePlayers()){

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Shield " + ChatColor.GOLD + "powerup has spawned!");

            }

        }else if(ran >= 50 && ran < 75){

            Pig power = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
            power.setCustomName(ChatColor.YELLOW + "Reduced Respawn");
            powerUpHit.put(power.getUniqueId(), 0);

            for(Player p : Bukkit.getOnlinePlayers()){

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Respawn " + ChatColor.GOLD + "powerup has spawned!");

            }

        }else if(ran >= 75 && ran <= 100){

            IronGolem power = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
            power.setCustomName(ChatColor.YELLOW + "Mega Reload");
            powerUpHit.put(power.getUniqueId(), 0);

            for(Player p : Bukkit.getOnlinePlayers()){

                p.sendRawMessage(ChatColor.GOLD + "A " + ChatColor.YELLOW + "Reload " + ChatColor.GOLD + "powerup has spawned!");

            }


        }




    }

    public static void spawnTeam1(Player p){


        int red = 255;
        int green = 195;
        int blue = 53;

        Location loc = team1Spawn.get(new Random().nextInt(team1Spawn.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());


        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta)lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta)llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta)lboots.getItemMeta();
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

    public static void respawn(final Player p, final int team){

        if(team == 1) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(SplatFlag.getPlugin(), new Runnable() {
                @Override
                public void run() {


                        spawnTeam1(p);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendRawMessage(ChatColor.GREEN + "You have respawned!");


                }
            }, team1Respawn * 20);
        }else if(team == 2) {


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

    public static void banRespawn(final int team){


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

    public static String teamReturn(Player p){

        if(team1.contains(p)){

            return "team1";

        }else if(team2.contains(p)){

            return "team2";

        }else{

            return null;
        }

    }

    public static void spawnTeam2(Player p){

        int red = 0;
        int green = 128;
        int blue = 255;

        Location loc = team2Spawn.get(new Random().nextInt(team2Spawn.size()));
        loc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());

        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lhelmet.setItemMeta(lam);

        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        lam = (LeatherArmorMeta)lchest.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        lchest.setItemMeta(lam);

        ItemStack llegs = new ItemStack(Material.LEATHER_LEGGINGS);
        lam = (LeatherArmorMeta)llegs.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        llegs.setItemMeta(lam);

        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
        lam = (LeatherArmorMeta)lboots.getItemMeta();
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

    public static void spawnBanner(int i){


            if (i == 1) {

                Location loc = new Location(team1Ban.getWorld(), team1Ban.getX(), team1Ban.getY() + 1, team1Ban.getZ());;
                Banner ban = (Banner) loc.getBlock().getState();
                ban.setBaseColor(DyeColor.BLUE);
                ban.update();

                for (Player player : team2){

                    player.sendRawMessage(ChatColor.GREEN + "Your flag has returned!");

                }

                for (Player player : team1){

                    player.sendRawMessage(ChatColor.GREEN + "The enemy's flag has returned!");

                }

            } else {

                Location loc = new Location(team2Ban.getWorld(), team2Ban.getX(), team2Ban.getY() + 1, team2Ban.getZ());
                Banner ban = (Banner) loc.getBlock().getState();
                ban.setBaseColor(DyeColor.YELLOW);
                ban.update();


                for (Player player : team1){

                    player.sendRawMessage(ChatColor.GREEN + "Your flag has returned!");

                }

                for (Player player : team2){

                    player.sendRawMessage(ChatColor.GREEN + "The enemy's flag has returned!");

                }
            }

    }



    @EventHandler
    public static void onLaunch(ProjectileLaunchEvent e){

        if(!balls){

            e.setCancelled(true);
            if(e.getEntity().getShooter() instanceof Player){

                ((Player) e.getEntity().getShooter()).sendRawMessage(ChatColor.RED + "Projectiles can not be launched right now!");

            }

        }

    }
    @EventHandler
    public static void onHit(ProjectileHitEvent e){

        if(team1.contains(e.getEntity().getShooter())){


            splash(e.getEntity().getLocation(), 1);

        }else if(team2.contains(e.getEntity().getShooter())){

            splash(e.getEntity().getLocation(), 2);

        }


    }
    @EventHandler
    public static void onDamage(EntityDamageEvent e){

        if(!e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){

            e.setCancelled(true);

        }


    }


    @EventHandler
    public static void onDrop(PlayerDropItemEvent e){

        if(sfOn){

            e.setCancelled(true);

        }


    }

    public void launchYellowFirework(Entity e, int speed) {
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

    public void launchBlueFirework(Entity e, int speed) {
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

    public void launchRedFirework(Entity e){

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


    @EventHandler
    public static void onPlace(BlockPlaceEvent e){

        if(!e.getPlayer().hasPermission("obam.smod")) {
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
    public void onBreak(BlockBreakEvent e){

        if(!e.getPlayer().hasPermission("obam.smod")){

            if(sfOn) {
                e.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){

        if(sfOn){

            e.setCancelled(true);

        }

    }




    @Override
    public void onEnable(){

        plugin = this;
        ScoreHandler.startScores();

        getServer().getPluginManager().registerEvents(new ScoreHandler(), this);
        getServer().getPluginManager().registerEvents(new PickUp(), this);
        getServer().getPluginManager().registerEvents(new Click(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHit(),this);


        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

                for(Entity e : Bukkit.getWorld("splatflag").getEntities()){

                    if(e instanceof Player){

                        Player p = (Player) e;



                        if(playerShields.contains(p)){

                            p.getWorld().playEffect(p.getEyeLocation(), Effect.SMOKE, 20, 5);

                        }

                    }

                    if(e instanceof Cow){

                        if(powerUpHit.containsKey(e.getUniqueId())) {

                            launchRedFirework(e);

                        }

                    }else if(e instanceof Pig){

                        if(powerUpHit.containsKey(e.getUniqueId())){

                            launchRedFirework(e);

                        }

                    }else if(e instanceof Chicken){

                        if(powerUpHit.containsKey(e.getUniqueId())){

                            launchRedFirework(e);

                        }


                    }else if(e instanceof IronGolem){

                        if(powerUpHit.containsKey(e.getUniqueId())){

                            launchRedFirework(e);

                        }

                    }
                    if (e instanceof Item){

                        ItemStack is = ((Item) e).getItemStack();

                        if(is.equals(new ItemStack(Material.BANNER, 1, (short) 4))){


                            launchBlueFirework(e, 10);

                        }else if(is.equals(new ItemStack(Material.BANNER, 1, (short) 11))){

                            launchYellowFirework(e, 10);

                        }

                    }

                }

                if(sfOn){

                    for(Player player : Bukkit.getWorld("splatflag").getPlayers()){

                        int amount = 0;
                        for(ItemStack is : player.getInventory().all(Material.SNOW_BALL).values())
                        {
                            amount=amount+is.getAmount();
                        }
                        if(amount < 64) {

                            player.getInventory().addItem(new ItemStack(Material.SNOW_BALL));

                        }





                    }

                }
            }
        }, 0L, 2 * 20L);

        getServer().getPluginManager().registerEvents(this, this);

        this.saveDefaultConfig();

        Location loc = new Location(Bukkit.getWorld("splatflag"), this.getConfig().getDouble("Flagstone.x") , this.getConfig().getDouble("Flagstone.y") , this.getConfig().getDouble("Flagstone.z"));

        flagStone(loc, 100);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

                check ++;
                if(check == 120){

                    check = 0;
                    if(sfOn) {
                        powerUpSpawn();
                    }

                }
                for(Player p : Bukkit.getWorld("splatflag").getPlayers()){

                    p.setFoodLevel(20);

                }
            }
        }, 0L, 20L);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player;

        if(sender instanceof Player){

            player = (Player) sender;

        }else{

            sender.sendMessage("You need be a player to do that!");
            return false;

        }
        if(cmd.getName().equalsIgnoreCase("sf")){

            if(args.length < 1){

                player.sendRawMessage(ChatColor.RED + "You need to use an argument!");
                return true;

            }else if(args[0].equalsIgnoreCase("point")){


                if(!player.hasPermission("obam.smod")){

                    player.sendRawMessage(ChatColor.RED + "You do not have permission to do this!");
                    return true;

                }
                this.getConfig().set("Flagstone.x", player.getLocation().getBlockX());
                this.getConfig().set("Flagstone.y", player.getLocation().getBlockY());
                this.getConfig().set("Flagstone.z", player.getLocation().getBlockZ());

                flagStone(player.getLocation(), 100);

                player.sendRawMessage(ChatColor.GREEN + "You have set the flagstone to " + ChatColor.YELLOW + "X: " + player.getLocation().getX() + " Y: " + player.getLocation().getY() + " Z: " + player.getLocation().getZ() + ChatColor.GREEN + "!");
                this.saveConfig();
                this.reloadConfig();

                return true;

            }else if(args[0].equalsIgnoreCase("start")){

                team1.clear();
                team2.clear();
                playerAssists.clear();
                playerKills.clear();
                playerStreak.clear();
                playerHit.clear();
                team1Score = 0;
                team2Score = 0;
                playerReturns.clear();
                playerDefends.clear();
                playerDeaths.clear();
                playerProtects.clear();
                playerCaptures.clear();
                playerShields.clear();
                for(Player p : Bukkit.getWorld("splatflag").getPlayers()){

                    p.sendRawMessage(ChatColor.translateAlternateColorCodes('&',"&3 &6 &3 &6 &3 &6 &e"));
                    p.sendRawMessage(ChatColor.GREEN + "A SplatFlag game has started!");
                    p.getInventory().clear();

                    if(team1.size() == team2.size()){

                        if(new Random().nextInt(100) < 50){

                            team1.add(p);
                            spawnTeam1(p);

                        }else{

                            team2.add(p);
                            spawnTeam2(p);

                        }

                    }else if(team1.size() < team2.size()){


                        team1.add(p);
                        spawnTeam1(p);

                    }else if(team2.size() < team1.size()){


                        team2.add(p);
                        spawnTeam2(p);

                    }



                }

                spawnBanner(1);
                spawnBanner(2);

                sfOn = true;

                return true;

            }else if(args[0].equalsIgnoreCase("cease")){

                balls = false;
                for(Player p : Bukkit.getOnlinePlayers()){

                    p.sendRawMessage(ChatColor.RED + "Projectile launching has been stopped.");

                }

            }else if(args[0].equals("off")){


                sfOn = false;
                for(Player p : Bukkit.getOnlinePlayers()){

                    p.sendRawMessage(ChatColor.GREEN + "The SplatFlag game has ended!");

                    if(team1Score > team2Score){

                        if(team1.contains(p)){

                            p.sendRawMessage(ChatColor.GREEN + "Your team has won!");

                        }else if(team2.contains(p)){

                            p.sendRawMessage(ChatColor.RED + "Your team has lost!");

                        }else {

                            p.sendRawMessage(ChatColor.GOLD + "Team 1 has won!");

                        }

                    }else if(team1Score < team2Score){

                        if(team2.contains(p)){

                            p.sendRawMessage(ChatColor.GREEN + "Your team has won!");

                        }else if(team1.contains(p)){

                            p.sendRawMessage(ChatColor.RED + "Your team has lost!");

                        }else {
                            p.sendRawMessage(ChatColor.GOLD + "Team 2 has won!");
                        }

                    }else if(team1Score == team2Score){

                        p.sendRawMessage(ChatColor.GOLD + "The match was a draw!");

                    }

                    team1.clear();
                    team2.clear();

                    return true;


                }

            }else if(args[0].equalsIgnoreCase("team")){

                player.sendRawMessage(teamReturn(player));
                return true;

            }else if(args[0].equalsIgnoreCase("score")){


                player.sendRawMessage(ChatColor.GOLD + "Team 1: " + team1Score);
                player.sendRawMessage(ChatColor.GOLD + "Team 2: " + team2Score);
                return true;

            }else if(args[0].equalsIgnoreCase("hide")){

                for(Player p : Bukkit.getOnlinePlayers()){

                    if(p.getScoreboard().getPlayerTeam(p) != null) {
                        p.getScoreboard().getPlayerTeam(p).setNameTagVisibility(NameTagVisibility.NEVER);
                    }else{

                        p.getScoreboard().registerNewTeam(p.getDisplayName()).setNameTagVisibility(NameTagVisibility.NEVER);
                        p.getScoreboard().getTeam(p.getDisplayName()).addPlayer(p);
                    }
                }
                Team t = player.getScoreboard().getPlayerTeam(player);
                player.sendRawMessage(String.valueOf(t.getNameTagVisibility()));
                player.sendRawMessage("Hid");
                return true;
            }else if(args[0].equalsIgnoreCase("show")){

                for(Player p : Bukkit.getOnlinePlayers()){

                    p.getScoreboard().getPlayerTeam(p).setNameTagVisibility(NameTagVisibility.ALWAYS);

                }
                Team t = player.getScoreboard().getPlayerTeam(player);
                player.sendRawMessage(String.valueOf(t.getNameTagVisibility()));
                player.sendRawMessage("Unhid");
                return true;

            }else if(args[0].equalsIgnoreCase("status")){

                Team t = player.getScoreboard().getPlayerTeam(player);
                player.sendRawMessage(String.valueOf(t.getNameTagVisibility()));
                player.sendRawMessage(t.getDisplayName());
                player.sendRawMessage(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName());

                player.sendRawMessage("stats");

            }



            }

        return false;

    }
}
