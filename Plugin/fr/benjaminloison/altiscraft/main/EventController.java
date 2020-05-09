package fr.benjaminloison.altiscraft.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissionAttachment;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import fr.benjaminloison.altiscraft.api.FileAPI;
import fr.benjaminloison.altiscraft.api.MCNBTDataAPI;
import fr.benjaminloison.altiscraft.api.SignAPI;
import fr.benjaminloison.altiscraft.api.WEWGAPI;

public class EventController implements Listener
{
    AltisCraft pl;
    ArrayList<String> regions = new ArrayList<String>();
    String playersData;
    World w;
    Location spawn;
    boolean first = true;
    int rand;
    Random random = new Random();
    Material appleLeaves = Material.getMaterial(pl.ID + "APPLE_LEAVES"), peachLeaves = Material.getMaterial(pl.ID + "PEACH_LEAVES"), methOre = Material.getMaterial(pl.ID + "METH_ORE"), copperOre = Material.getMaterial(pl.ID + "COPPER_ORE"), cocainePlant = Material.getMaterial(pl.ID + "COCAINE_PLANT"), cannabisPlant = Material.getMaterial(pl.ID + "CANNABIS_PLANT"),
            cocoBlock = Material.getMaterial(pl.ID + "COCO_BLOCK");
    ItemStack archeologyHelmet = new ItemStack(Material.getMaterial(pl.ID + "ARCHEO")), tortoise = new ItemStack(Material.getMaterial(pl.ID + "TORTOISE"), 1), gold = new ItemStack(Material.getMaterial(pl.ID + "GOLD"), 1), crate = new ItemStack(Material.getMaterial(pl.ID + "CRATE"), 1), artifact = new ItemStack(Material.getMaterial(pl.ID + "ARTIFACT"), 1),
            diamond = new ItemStack(Material.getMaterial(pl.ID + "DIAMOND"), 1), apple = new ItemStack(Material.APPLE, 1), peach = new ItemStack(Material.getMaterial(pl.ID + "PEACH"), 1), meth = new ItemStack(Material.getMaterial(pl.ID + "METH"), 1), copper = new ItemStack(Material.getMaterial(pl.ID + "COPPER_ORE"), 1),
 cocaine = new ItemStack(Material.getMaterial(pl.ID + "COCAINE_0"), 1),
            cannabis = new ItemStack(Material.getMaterial(pl.ID + "CANNABIS"), 1), coco = new ItemStack(Material.getMaterial(pl.ID + "COCO"), 1), iron = new ItemStack(Material.IRON_ORE, 1);

    public EventController(AltisCraft p)
    {
        p.getServer().getPluginManager().registerEvents(this, p);
        pl = p;
        w = Bukkit.getWorlds().get(0);
        spawn = FileAPI.location(w, new File(FileAPI.spawn + "Spawn.yml"));
        playersData = FileAPI.path + w.getName() + File.separatorChar + "playerdata" + File.separatorChar;
    }

    @EventHandler
    public void onCommandServer(ServerCommandEvent e)
    {
        String args[] = e.getCommand().split(" ");
        if(args[0].equalsIgnoreCase("uuid"))
        {
            if(args.length > 1)
            {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                OfflinePlayer offTarget = Bukkit.getServer().getOfflinePlayer(args[1]);
                if(target != null)
                    pl.print(args[1] + ": " + target.getUniqueId());
                else if(offTarget != null)
                    pl.print(args[1] + ": " + offTarget.getUniqueId());
                else
                    pl.print(args[1] + FileAPI.translate("uuid.not.found"));
            }
        }
        else if(args[0].equals("manuadd"))
            if(args.length > 2)
                ts("$ts->serverGroupClientAdd($ts->serverGroupGetByName(\"" + args[2] + "\"), $ts->clientFindDb(\"" + args[1].replace("_", " ") + "\"));");
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        String m = e.getMessage(), args[] = m.split(" ");
        Player p = e.getPlayer();
        Location l = e.getPlayer().getLocation();
        int x = l.getBlockX(), y = l.getBlockY(), z = l.getBlockZ();
        World w = e.getPlayer().getWorld();
        p.saveData();
        if(args[0].startsWith("//"))
            pl.print(p.getName() + " " + x + " " + y + " " + z + " " + m.replaceFirst("//", ""));
        else if(args[0].equalsIgnoreCase("/sync"))
        {
            e.setCancelled(true);
            p.loadData();
            p.sendMessage(FileAPI.translate("sync.done"));
        }
        else if(args[0].equalsIgnoreCase("/carshop"))
        {
            e.setCancelled(true);
            if(p.hasPermission("staff"))
                if(args.length > 3)
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        FileAPI.addLign(args[3].replace(":", " "), new File(FileAPI.carShop + args[2].replace(":", " ") + ".txt"));
                        p.sendMessage(args[3].replace(":", " ") + FileAPI.translate("added.to") + args[2].replace(":", " "));
                    }
                    else if(args[1].equalsIgnoreCase("remove"))
                    {
                        FileAPI.removeLign(args[3].replace(":", " "), new File(FileAPI.carShop + args[2].replace(":", " ") + ".txt"));
                        p.sendMessage(args[3].replace(":", " ") + FileAPI.translate("removed.to") + args[2].replace(":", " "));
                    }
                }
                else
                {
                    String[] files = new File(FileAPI.carShop).list();
                    for(int i = 0; i < files.length; i++)
                        p.sendMessage("[" + i + "] " + files[i]);
                }
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/spawns") && 100 > spawn.distance(l))
        {
            e.setCancelled(true);
            if(!p.hasPlayedBefore())
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money " + p.getName() + " " + FileAPI.config("money.new.player"));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName() + " altiscraft_backpack");
            }
            if(args.length > 1)
            {
                File dataFile = new File(playersData + p.getUniqueId() + ".dat");
                boolean isPolice = false, isRebel = false;
                String gang = "";
                if(dataFile.exists())
                {
                    MCNBTDataAPI data = new MCNBTDataAPI(dataFile);
                    if(data != null)
                        if(data.hasCompoundTag(AltisCraft.NAME))
                        {
                            CompoundTag cmpt = data.getCompoundTag(AltisCraft.NAME);
                            isPolice = data.getBooleanFromByte(data.valueKeyInCompoundTag(cmpt, "Police"));
                            isRebel = data.getBooleanFromByte(data.valueKeyInCompoundTag(cmpt, "Rebel"));
                            gang = data.valueKeyInCompoundTag(cmpt, "Gang");
                        }
                }
                if(args[1].equalsIgnoreCase("gang") && isRebel)
                    if(WEWGAPI.hasHouseGang(pl.getWorldGuard(), w, p, gang))
                    {
                        ProtectedRegion r = WEWGAPI.houseGangOf(pl.getWorldGuard(), w, p, gang);
                        p.teleport(new Location(w, SignAPI.x(r.getId()), SignAPI.y(r.getId()), SignAPI.z(r.getId())));
                        p.sendMessage(FileAPI.translate("you.have.spawn.to.your.gang"));
                    }
                    else
                        p.sendMessage(FileAPI.translate("you.have.no.gang.house"));
                else if(args[1].equalsIgnoreCase("house"))
                    if(WEWGAPI.hasHouse(pl.getWorldGuard(), w, p))
                    {
                        ProtectedRegion r = WEWGAPI.houseOf(pl.getWorldGuard(), w, p);
                        p.teleport(new Location(w, SignAPI.x(r.getId()), SignAPI.y(r.getId()), SignAPI.z(r.getId())));
                        p.sendMessage(FileAPI.translate("you.have.spawn.to.your.house"));
                    }
                    else
                        p.sendMessage(FileAPI.translate("you.have.no.house"));
                else if(args.length == 2)
                {
                    spawn(p, args[1], isPolice, isRebel);
                    return;
                }
                else if(args.length > 2 && p.hasPermission("staff"))
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        boolean find = false;
                        for(String file : new File(FileAPI.spawn).list())
                            if(StringUtils.containsIgnoreCase(file, args[2]))
                            {
                                find = true;
                                FileAPI.addLign(x + "," + y + "," + z, new File(FileAPI.spawn + file));
                                p.sendMessage(x + "," + y + "," + z + FileAPI.translate("added.to") + args[2]);
                                break;
                            }
                        if(!find)
                        {
                            p.sendMessage(FileAPI.translate("no.spawn.for.this.creating") + args[2]);
                            FileAPI.addLign(x + "," + y + "," + z, new File(FileAPI.spawn + args[2] + ".yml"));
                            p.sendMessage(x + "," + y + "," + z + FileAPI.translate("added.to") + args[2]);
                        }
                        return;
                    }
                    else if(args[1].equalsIgnoreCase("remove"))
                    {
                        boolean find = false;
                        for(String file : new File(FileAPI.spawn).list())
                            if(StringUtils.containsIgnoreCase(file, args[2]))
                            {
                                find = true;
                                FileAPI.removeLign(args[3] + "," + args[4] + "," + args[5], new File(FileAPI.spawn + file));
                                p.sendMessage(x + "," + y + "," + z + FileAPI.translate("removed.to") + args[2]);
                                break;
                            }
                        if(!find)
                            p.sendMessage(FileAPI.translate("no.spawn.for.this"));
                        return;
                    }
                    else if(args[1].equalsIgnoreCase("spawns"))
                    {
                        boolean find = false;
                        for(String file : new File(FileAPI.spawn).list())
                            if(StringUtils.containsIgnoreCase(file, args[2]))
                            {
                                find = true;
                                String spawns[] = FileAPI.readAll(new File(FileAPI.spawn + file));
                                for(int i = 0; i < spawns.length; i++)
                                    p.sendMessage(args[2] + " [" + i + "] : " + spawns[i]);
                                break;
                            }
                        if(!find)
                            p.sendMessage(FileAPI.translate("no.spawn.for.this"));
                        return;
                    }
                    else
                    {
                        String files[] = new File(FileAPI.spawn).list();
                        for(int i = 0; i < files.length; i++)
                            p.sendMessage("[" + i + "] " + FileAPI.afterPoint(files[i].replace(".yml", "")));
                        return;
                    }
                }
            }
            else
                p.sendMessage(FileAPI.translate("arguments.error"));
        }
        else if(args[0].equalsIgnoreCase("/seeinv"))
        {
            e.setCancelled(true);
            if(p.hasPermission("police"))
                if(args.length > 1)
                {
                    Player target = Bukkit.getServer().getPlayer(UUID.fromString(args[1]));
                    if(target == null)
                    {
                        p.sendMessage(FileAPI.translate("player.not.found"));
                        return;
                    }
                    if(l.distance(target.getLocation()) < 8)
                    {
                        PermissionAttachment attachment = p.addAttachment(pl);
                        AltisCraft.perm.put(p.getName(), attachment);
                        attachment.setPermission("essentials.invsee", true);
                        Bukkit.dispatchCommand(p, "invsee " + target.getName());
                        attachment.setPermission("essentials.invsee", false);
                    }
                    else
                        p.sendMessage(FileAPI.translate("you.are.too.far.from") + target.getName() + FileAPI.translate("end.of.sentence"));
                }
                else
                    p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/uuid"))
        {
            e.setCancelled(true);
            if(args.length > 1)
            {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                OfflinePlayer offTarget = Bukkit.getServer().getOfflinePlayer(args[1]);
                if(target != null)
                    p.sendMessage(args[1] + ": " + target.getUniqueId());
                else if(offTarget != null)
                    p.sendMessage(args[1] + ": " + offTarget.getUniqueId());
                else
                    p.sendMessage(args[1] + FileAPI.translate("uuid.not.found"));
            }
        }
        else if(args[0].equalsIgnoreCase("/name"))
        {
            e.setCancelled(true);
            if(args.length > 1)
            {
                Player target = Bukkit.getServer().getPlayer(UUID.fromString(args[1]));
                OfflinePlayer offTarget = Bukkit.getServer().getOfflinePlayer(UUID.fromString(args[1]));
                if(target != null)
                    p.sendMessage(args[1] + ": " + target.getUniqueId());
                else if(offTarget != null)
                    p.sendMessage(args[1] + ": " + offTarget.getUniqueId());
                else
                    p.sendMessage(args[1] + FileAPI.translate("name.not.found"));
            }
        }
        else if(args[0].equalsIgnoreCase("/house"))
        {
            e.setCancelled(true);
            boolean isHouseHere = false;
            String argsError = FileAPI.translate("arguments.error") + " /house <info/buy/sell/add/remove> [player]";
            if(args.length == 1)
                p.sendMessage(argsError);
            else
            {
                Iterator<Entry<String, ProtectedRegion>> itRgs = pl.getWorldGuard().getRegionManager(w).getRegions().entrySet().iterator();
                ProtectedRegion region = null;
                boolean isOwner = false;
                while(itRgs.hasNext())
                {
                    Entry<String, ProtectedRegion> enter = itRgs.next();
                    if(enter.getValue().contains(l.getBlockX(), l.getBlockY(), l.getBlockZ()) && enter.getValue().getId().length() >= 7 && enter.getValue().getId().contains("_"))
                    {
                        isHouseHere = true;
                        region = enter.getValue();
                        if(enter.getValue().isMember(p.getName()))
                            isOwner = true;
                    }
                }
                if(!isHouseHere)
                {
                    long distance = 99999;
                    itRgs = pl.getWorldGuard().getRegionManager(w).getRegions().entrySet().iterator();
                    while(itRgs.hasNext())
                    {
                        ProtectedRegion v = itRgs.next().getValue();
                        if(!v.hasMembersOrOwners() && v.getId().startsWith("l"))
                        {
                            BlockVector b = v.getMinimumPoint();
                            long form = (long)l.distance(new Location(w, b.getBlockX(), b.getBlockY(), b.getBlockZ()));
                            if(form < distance)
                                distance = form;
                        }
                    }
                    if(distance == 99999)
                        p.sendMessage(FileAPI.translate("no.house"));
                    else
                        p.sendMessage(FileAPI.translate("no.house.here.nearest.house.at") + FileAPI.numberNormal(distance) + FileAPI.translate("cubs"));
                    return;
                }
                if(args[1].equalsIgnoreCase("info"))
                    if(region.hasMembersOrOwners())
                        if(region.getMembers().size() >= 2)
                            if(region.getId().startsWith("g"))
                                p.sendMessage(FileAPI.translate("house.of.gang") + SignAPI.nameGang(region.getId()) + FileAPI.translate("directed.by") + SignAPI.nameGangChief(w, region.getId()) + FileAPI.translate("unavailable.members") + region.getMembers().toPlayersString());
                            else if(region.getId().startsWith("l"))
                                p.sendMessage(FileAPI.translate("house.to.rent.unavailable.owners") + region.getMembers().toPlayersString());
                            else
                                p.sendMessage(FileAPI.translate("house.unavailable.owners") + region.getMembers().toPlayersString());
                        else if(region.getId().startsWith("g"))
                            p.sendMessage(FileAPI.translate("house.of.gang") + SignAPI.nameGang(region.getId()) + FileAPI.translate("directed.by") + SignAPI.nameGangChief(w, region.getId()) + FileAPI.translate("unavailable.member") + region.getMembers().toPlayersString());
                        else if(region.getId().startsWith("l"))
                            p.sendMessage(FileAPI.translate("house.to.rent.unavailable.owner") + region.getMembers().toPlayersString());
                        else
                            p.sendMessage(FileAPI.translate("house.unavailable.owner") + region.getMembers().toPlayersString());
                    else if(region.getId().startsWith("g"))
                        Bukkit.broadcastMessage(FileAPI.translate("house.of.gang") + region.getId() + FileAPI.translate("with.out.owner.fatal.error"));
                    else if(region.getId().startsWith("l"))
                        p.sendMessage(FileAPI.translate("house.to.rent.available.no.owner.price") + FileAPI.number(SignAPI.price(region.getId())) + " " + FileAPI.translate("the.day") + FileAPI.translate("end.of.sentence"));
                    else
                        p.sendMessage(FileAPI.translate("house.available.no.owner.price") + FileAPI.number(SignAPI.price(region.getId())));
                else if(args[1].equalsIgnoreCase("buy"))
                {
                    if(!isOwner)
                        if(!region.hasMembersOrOwners())
                        {
                            if(!WEWGAPI.hasHouse(pl.getWorldGuard(), w, p))
                                buy(p, region.getId());
                            else
                                p.sendMessage(FileAPI.translate("you.cannot.have.more.than.one.house"));
                        }
                        else if(region.getMembers().size() >= 2)
                            p.sendMessage(FileAPI.translate("house.unavailable.owners") + region.getMembers().toPlayersString());
                        else
                            p.sendMessage(FileAPI.translate("house.unavailable.owner") + region.getMembers().toPlayersString());
                    else if(region.getId().startsWith("l") && !region.getId().startsWith("g"))
                    {
                        // TODO Erreur WTF translation
                        if(StringUtils.isNumeric(((Sign)w.getBlockAt(new Location(w, SignAPI.x(region.getId()), SignAPI.y(region.getId()), SignAPI.z(region.getId()))).getState()).getLine(2).replace("loué ", "").replace("j à ", "")))
                            if(14 < Integer.parseInt(((Sign)w.getBlockAt(new Location(w, SignAPI.x(region.getId()), SignAPI.y(region.getId()), SignAPI.z(region.getId()))).getState()).getLine(2).replace("loué ", "").replace("j à ", "")))
                            {
                                p.sendMessage(FileAPI.translate("you.cannot.rent.this.time"));
                                return;
                            }
                        buy(p, region.getId());
                    }
                    else
                        p.sendMessage(FileAPI.translate("this.house.is.already.yours"));
                }
                else if(args[1].equalsIgnoreCase("sell"))
                {
                    if(!isOwner)
                    {
                        p.sendMessage(FileAPI.translate("you.are.not.owner"));
                        return;
                    }
                    if(!region.getId().startsWith("g"))
                        if(region.getId().startsWith("l"))
                            WEWGAPI.sellLocaRegion(region, w);
                        else
                            WEWGAPI.sellPermRegion(region, w);
                    else
                    {
                        p.sendMessage(FileAPI.translate("you.cannot.sell.a.gang.house"));
                        return;
                    }
                    p.sendMessage(FileAPI.translate("you.have.sold.your.house"));
                }
                else if(args[1].equalsIgnoreCase("add"))
                {
                    if(args.length < 3)
                    {
                        p.sendMessage(argsError);
                        return;
                    }
                    if(!isOwner)
                    {
                        p.sendMessage(FileAPI.translate("you.are.not.owner"));
                        return;
                    }
                    region.getMembers().addPlayer(args[2]);
                    p.sendMessage(FileAPI.translate("you.have.added") + args[2] + FileAPI.translate("to.your.house"));
                }
                else if(args[1].equalsIgnoreCase("remove"))
                {
                    if(args.length < 3)
                    {
                        p.sendMessage(argsError);
                        return;
                    }
                    if(!isOwner)
                    {
                        p.sendMessage(FileAPI.translate("you.are.not.owner"));
                        return;
                    }
                    if(region.getId().startsWith("g") && args[2].toLowerCase().startsWith(SignAPI.nameGangChief(w, region.getId()).toLowerCase()))
                    {
                        p.sendMessage(FileAPI.translate("you.are.cannot.remove.the.chief.of.the.gang"));
                        return;
                    }
                    else if(region.getMembers().size() == 1)
                    {
                        p.sendMessage(FileAPI.translate("you.cannot.removed.yourself.you.are.the.last.owner.sell.the.house.if.you.want"));
                        return;
                    }
                    region.getMembers().removePlayer(args[2]);
                    p.sendMessage(FileAPI.translate("you.have.removed") + args[2] + FileAPI.translate("of.your.house"));
                }
                else
                    p.sendMessage(argsError);
            }
        }
        else if(args[0].equalsIgnoreCase("/antv"))
        {
            e.setCancelled(true);
            if(p.hasPermission("antv"))
            {
                if(args.length < 4)
                {
                    p.sendMessage("/antv <title> <message> <image>");
                    return;
                }
                Bukkit.broadcastMessage("!ANTV!" + args[1] + " " + args[2] + " " + args[3]);
            }
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/cuff"))
        {
            e.setCancelled(true);
            if(p.hasPermission("police"))
                cuff(p, Bukkit.getServer().getPlayer(UUID.fromString(args[1])));
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/uncuff"))
        {
            e.setCancelled(true);
            if(p.hasPermission("police"))
                uncuff(p, Bukkit.getServer().getPlayer(UUID.fromString(args[1])));
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/escort"))
        {
            e.setCancelled(true);
            if(p.hasPermission("police"))
                escort(p, Bukkit.getServer().getPlayer(UUID.fromString(args[1])));
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/unescort"))
        {
            e.setCancelled(true);
            if(p.hasPermission("police"))
                unescort(p, Bukkit.getServer().getPlayer(UUID.fromString(args[1])));
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/friend"))
        {
            e.setCancelled(true);
            friend(p, Bukkit.getServer().getPlayer(UUID.fromString(args[1])), Boolean.parseBoolean(args[2]));
        }
        else if(args[0].equalsIgnoreCase("/crash"))
        {
            e.setCancelled(true);
            if(p.hasPermission("Benjamin LOISON"))
                if(args.length == 1)
                    p.sendMessage(FileAPI.translate("enter.the.name.of.a.player"));
                else
                {
                    p.sendMessage(FileAPI.translate("shutdown.sent"));
                    Bukkit.getServer().getPlayer(args[1]).sendMessage("!SHUTDOWN!");
                }
            else
                p.sendMessage(FileAPI.translate("no.permission"));
        }
        else if(args[0].equalsIgnoreCase("/pl") || args[0].equalsIgnoreCase("/plugin") || args[0].equalsIgnoreCase("/plugins") || args[0].equalsIgnoreCase("/help") || args[0].equalsIgnoreCase("/h") || args[0].equalsIgnoreCase("/?") && !p.isOp())
            e.setCancelled(true);
    }

    public void spawn(Player p, String spawn, boolean isPolice, boolean isRebel)
    {
        boolean find = false;
        String grade = "";
        if(isPolice)
            grade = "Police";
        else if(isRebel)
            grade = "Rebel";
        for(String file : new File(FileAPI.spawn).list())
            if(StringUtils.containsIgnoreCase(file, spawn + grade))
            {
                find = true;
                String[] spawns = FileAPI.readAll(new File(FileAPI.spawn + file));
                rand = random.nextInt(spawns.length);
                p.teleport(new Location(w, Integer.parseInt(spawns[rand].split(",")[0]), Integer.parseInt(spawns[rand].split(",")[1]), Integer.parseInt(spawns[rand].split(",")[2])));
                break;
            }
        if(!find)
            for(String file : new File(FileAPI.spawn).list())
                if(StringUtils.containsIgnoreCase(file, spawn))
                {
                    find = true;
                    String[] spawns = FileAPI.readAll(new File(FileAPI.spawn + file));
                    rand = random.nextInt(spawns.length);
                    p.teleport(new Location(w, Integer.parseInt(spawns[rand].split(",")[0]), Integer.parseInt(spawns[rand].split(",")[1]), Integer.parseInt(spawns[rand].split(",")[2])));
                    break;
                }
        if(!find)
            p.sendMessage(FileAPI.translate("no.spawn.for.this"));
    }

    // TODO Comme Annihilation faire respawn automatique

    @EventHandler
    public void onPlayerSpawnEvent(PlayerRespawnEvent e)
    {
        // TODO: Called when despawn by force respawn ? -> Ban temp 5 minutes
        e.getPlayer().teleport(spawn);
        pl.handcuff.remove(e.getPlayer());
        pl.escort.remove(e.getPlayer());
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e)
    {
        pl.print(e.getPlayer().getName() + FileAPI.translate("has.placed") + " " + e.getBlock().getType() + " (" + e.getBlock().getTypeId() + ") " + e.getBlock().getX() + " " + e.getBlock().getY() + " " + e.getBlock().getZ());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Block b = e.getBlock();
        Material m = b.getType();
        World w = b.getWorld();
        Player p = e.getPlayer();
        int x = b.getX(), y = b.getY(), z = b.getZ();
        if(m == Material.SIGN || m == Material.SIGN_POST || m == Material.WALL_SIGN)
        {
            ProtectedRegion h = WEWGAPI.houseContains(pl.getWorldGuard(), w, x + "_" + y + "_" + z);
            if(h != null)
            {
                Bukkit.dispatchCommand(p, "rg remove " + h.getId());
                pl.print(p.getName() + FileAPI.translate("has.destroyed") + m + " (" + b.getTypeId() + ") " + x + " " + y + " " + z + " destroying region " + h.getId());
                p.sendMessage(FileAPI.translate("you.have.deleted.the.region") + h.getId() + FileAPI.translate("end.of.sentence"));
            }
        }
        else
        {
            Location l = b.getLocation();
            PlayerInventory i = p.getInventory();
            if(m == Material.DIAMOND_ORE)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                p.getInventory().addItem(diamond);
            }
            else if(m == peachLeaves)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(peach);
                i.addItem(peach);
                i.addItem(peach);
            }
            else if(m == appleLeaves)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(apple);
                i.addItem(apple);
                i.addItem(apple);
            }
            else if(m == cannabisPlant)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(cannabis);
                i.addItem(cannabis);
            }
            else if(m == cocainePlant)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(cocaine);
                i.addItem(cocaine);
            }
            else if(m == methOre)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(meth);
            }
            else if(m == copperOre)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(copper);
            }
            else if(m == cocoBlock)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(coco);
            }
            else if(m == Material.IRON_ORE)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(iron);
            }
            else if(m == Material.CACTUS)
            {
                e.setCancelled(true);
                w.getBlockAt(l).setType(m);
                i.addItem(new ItemStack(Material.CACTUS));
            }
            else
                pl.print(p.getName() + FileAPI.translate("has.destroyed") + m + " (" + b.getTypeId() + ") " + x + " " + y + " " + z);
        }
    }

    String ts(String code)
    {
        try
        {
            Scanner scan = new Scanner(new File(new File("").getAbsolutePath() + File.separatorChar + "plugins" + File.separatorChar + "TeamSpeak" + File.separatorChar + "Config.yml"));
            String message = "PHP " + scan.nextLine().replace("ip=", "") + " " + scan.nextLine().replace("port=", "") + " " + scan.nextLine().replace("query_admin=", "") + " " + scan.nextLine().replace("query_password=", "") + " " + scan.nextLine().replace("query_port=", "") + " " + scan.nextLine().replace("query_user_nick=", "").replace(" ", "%20") + " " + code;
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            socket.setSoTimeout(5000);
            socket.send(new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("altiscraft.fr"), 59722));
            socket.receive(packet);
            pl.print(message);
            String result = new String(packet.getData(), 0, packet.getLength()), results[] = result.split("\n");
            pl.print("Result: " + results[0]);
            for(int i = 1; i < results.length; i++)
                pl.print(results[i]);
            socket.close();
            scan.close();
            return result;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    void checkTS()
    {
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(FileAPI.translate("ts.viewer.url")).openConnection().getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder a = new StringBuilder();
            while((inputLine = in.readLine()) != null)
                a.append(inputLine);
            in.close();
            for(Player player : Bukkit.getOnlinePlayers())
            {
                String name = player.getName().replace("_", " ");
                if(!a.toString().contains("] " + name))
                    player.kickPlayer(FileAPI.translate("ts.kick.reason.plus.username") + name);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        String pName = p.getName(), ip = p.getAddress().getAddress().toString().replace("/", "");
        try
        {
            String message = "S " + pName + " " + ip;
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket receivedData = new DatagramPacket(new byte[1024], 1024);
            socket.setSoTimeout(10000);
            socket.send(new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(FileAPI.translate("authentification.server.ip")), Integer.parseInt(FileAPI.translate("authentification.server.port"))));
            socket.receive(receivedData);
            if(Integer.parseInt(new String(receivedData.getData(), 0, receivedData.getLength())) != 1)
            {
                ban(p);
                pl.print("[FATAL ALERT] Potential hacker: " + pName + " " + ip + " this bug has been protected !");
                socket.close();
                return;
            }
            else
                pl.print(FileAPI.translate("connection.authorized.for") + pName + " (" + ip + ") !");
            socket.close();
        }
        catch(SocketTimeoutException ex)
        {
            p.kickPlayer(FileAPI.translate("authentification.not.treated.in.time"));
            pl.print("[FATAL ALERT] Timed out for connecting to authenfication server !");
            ex.printStackTrace();
            return;
        }
        catch(Exception ex)
        {
            p.kickPlayer("Authentification error !");
            pl.print("[FATAL ALERT] Error in authentification system !");
            ex.printStackTrace();
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "speed run 0.1 " + pName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect " + pName + " clear");
        p.teleport(spawn);
        pl.handcuff.remove(p);
        pl.escort.remove(p);
        checkTS();
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e)
    {
        // TODO: Refaire le système
        // if(!e.getPlayer().isOp())
        // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + e.getPlayer().getName() + " 300");
    }

    public void ban(Player p)
    {
        Bukkit.getBanList(Type.NAME).addBan(p.getName(), "", new Date(), "");
        Bukkit.banIP(p.spigot().getRawAddress().getAddress().toString().replace("/", ""));
        p.kickPlayer("");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getPlayer().getLocation().distance(new Location(e.getPlayer().getWorld(), -7959, 14, -3849)) < 20 && e.getPlayer().getEquipment().getHelmet().equals(archeologyHelmet))
        {
            rand = random.nextInt(50);
            if(rand == 1)
            {
                e.getPlayer().getInventory().addItem(tortoise);
                e.getPlayer().sendMessage(FileAPI.translate("turtles.area") + FileAPI.translate("you.have.farmed") + "1" + FileAPI.translate("tortoise"));
            }
            else if(rand == 2)
            {
                e.getPlayer().getInventory().addItem(tortoise);
                e.getPlayer().getInventory().addItem(tortoise);
                e.getPlayer().sendMessage(FileAPI.translate("turtles.area") + FileAPI.translate("you.have.farmed") + "2" + FileAPI.translate("turtles"));
            }
            else
                e.getPlayer().sendMessage(FileAPI.translate("turtles.area") + FileAPI.translate("nothing.found"));
        }
        else if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getPlayer().getLocation().distance(new Location(e.getPlayer().getWorld(), -9015, 11, -4042)) < 15 && e.getPlayer().getEquipment().getHelmet().equals(archeologyHelmet))
        {
            rand = random.nextInt(100);
            if(rand == 1)
            {
                e.getPlayer().getInventory().addItem(crate);
                e.getPlayer().sendMessage(FileAPI.translate("archeology.area") + FileAPI.translate("you.have.farmed") + "1" + FileAPI.translate("box"));
            }
            else if(rand == 2)
            {
                e.getPlayer().getInventory().addItem(gold);
                e.getPlayer().sendMessage(FileAPI.translate("archeology.area") + FileAPI.translate("you.have.farmed") + "1" + FileAPI.translate("gold"));
            }
            else if(rand == 2)
            {
                e.getPlayer().getInventory().addItem(artifact);
                e.getPlayer().sendMessage(FileAPI.translate("archeology.area") + FileAPI.translate("you.have.farmed") + "1" + FileAPI.translate("artifact"));
            }
            else
                e.getPlayer().sendMessage(FileAPI.translate("archeology.area") + FileAPI.translate("nothing.found"));
        }
    }

    @EventHandler
    public void onSignPlaced(SignChangeEvent e)
    {
        String srg = WEWGAPI.smallRegion(pl.getWorldGuard(), e.getBlock().getWorld(), e.getBlock().getLocation()).getId();
        if(!e.getLine(0).equalsIgnoreCase(""))
            if(StringUtils.isNumeric(e.getLine(0)))
                try
                {
                    pl.getWorldEdit().getSession(e.getPlayer()).getRegionSelector().getRegion().getHeight();
                    int price = Integer.parseInt(e.getLine(0)), x = e.getBlock().getX(), y = e.getBlock().getY(), z = e.getBlock().getZ();
                    String region = price + "_" + x + "_" + y + "_" + z;
                    Bukkit.dispatchCommand(e.getPlayer(), "rg define " + region);
                    Bukkit.dispatchCommand(e.getPlayer(), "rg setparent " + region + " " + srg);
                    Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " use deny");
                    Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " construct deny");
                    e.setLine(0, FileAPI.translate("house.to.sell.sign"));
                    e.setLine(1, FileAPI.translate("at.sign") + FileAPI.number(price));
                    e.setLine(2, "/house " + FileAPI.translate("for"));
                    e.setLine(3, FileAPI.translate("interaction"));
                    Bukkit.broadcastMessage(FileAPI.translate("a.new.house.is.available.in.x") + x + FileAPI.translate("y") + y + FileAPI.translate("z") + z + FileAPI.translate("at.the.price.of") + FileAPI.number(price) + FileAPI.translate("has.been.added.by") + e.getPlayer().getName() + FileAPI.translate("end.of.sentence"));
                }
                catch(IncompleteRegionException ei)
                {
                    e.getBlock().setType(Material.AIR);
                    e.getPlayer().sendMessage(FileAPI.translate("you.need.to.select.a.region"));
                }
            else if(e.getLine(0).contains("g"))
                if(!e.getLine(1).equalsIgnoreCase(""))
                    try
                    {
                        pl.getWorldEdit().getSession(e.getPlayer()).getRegionSelector().getRegion().getHeight();
                        String gang = e.getLine(0).replaceFirst("g", ""), mainPlayer = e.getLine(1), region = "g" + gang + "_" + e.getBlock().getX() + "_" + e.getBlock().getY() + "_" + e.getBlock().getZ();
                        Bukkit.dispatchCommand(e.getPlayer(), "rg define " + region);
                        Bukkit.dispatchCommand(e.getPlayer(), "rg setparent " + region + " " + srg);
                        Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " use deny");
                        Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " construct deny");
                        Bukkit.dispatchCommand(e.getPlayer(), "rg addmember " + region + " g:" + gang);
                        e.setLine(0, FileAPI.translate("house.of.gang"));
                        e.setLine(1, FileAPI.translate("of"));
                        e.setLine(2, gang);
                        e.setLine(3, "(" + mainPlayer.subSequence(0, 12) + ")");
                        Bukkit.broadcastMessage(FileAPI.translate("the.house.of.gang.of") + gang + FileAPI.translate("directed.by") + mainPlayer + FileAPI.translate("has.been.added.by") + e.getPlayer().getName() + FileAPI.translate("end.of.sentence"));
                    }
                    catch(IncompleteRegionException ei)
                    {
                        e.getBlock().setType(Material.AIR);
                        e.getPlayer().sendMessage(FileAPI.translate("you.need.to.select.a.region"));
                    }
                else
                {
                    e.getBlock().setType(Material.AIR);
                    e.getPlayer().sendMessage(FileAPI.translate("need.to.enter.at.the.second.line.the.chief.of.gang.name"));
                }
            else if(e.getLine(0).contains("l"))
                if(StringUtils.isNumeric(e.getLine(0).replace("l", "")))
                    try
                    {
                        pl.getWorldEdit().getSession(e.getPlayer()).getRegionSelector().getRegion().getHeight();
                        int prix = Integer.parseInt(e.getLine(0).replace("l", "")), x = e.getBlock().getX(), y = e.getBlock().getY(), z = e.getBlock().getZ();
                        String region = "l" + prix + "_" + x + "_" + y + "_" + z;
                        Bukkit.dispatchCommand(e.getPlayer(), "rg define " + region);
                        Bukkit.dispatchCommand(e.getPlayer(), "rg setparent " + region + " " + srg);
                        Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " use deny");
                        Bukkit.dispatchCommand(e.getPlayer(), "rg flag " + region + " construct deny");
                        e.setLine(0, FileAPI.translate("house.to.rent.sign"));
                        e.setLine(1, FileAPI.translate("at.sign") + FileAPI.number(prix));
                        e.setLine(2, FileAPI.translate("the.day"));
                        Bukkit.broadcastMessage(FileAPI.translate("a.new.house.to.rent.is.available.in.x") + x + FileAPI.translate("y") + y + FileAPI.translate("z") + z + FileAPI.translate("at.the.price.of") + FileAPI.number(prix) + FileAPI.translate("the.day.this.house.has.been.added.by") + e.getPlayer().getName() + FileAPI.translate("end.of.sentence"));
                    }
                    catch(IncompleteRegionException ei)
                    {
                        e.getBlock().setType(Material.AIR);
                        e.getPlayer().sendMessage(FileAPI.translate("you.need.to.select.a.region"));
                    }
                else
                    pl.print(e.getPlayer().getName() + FileAPI.translate("has.placed") + FileAPI.translate("a.sign") + e.getBlock().getX() + " " + e.getBlock().getY() + " " + e.getBlock().getZ());
    }

    public void checkHouses(Player p) throws Exception
    {
        first = false;
        boolean checkLoca = false;
        pl.print(FileAPI.translate("house") + FileAPI.translate("beginning.of.solds.by.absence.of.sign"));
        Iterator<Entry<String, ProtectedRegion>> r = pl.getWorldGuard().getRegionManager(p.getWorld()).getRegions().entrySet().iterator();
        while(r.hasNext())
        {
            Entry<String, ProtectedRegion> e = r.next();
            if(e.getValue().getId().contains("_") && !(e.getValue().getId().startsWith("f") || e.getValue().getId().startsWith("r")))
            {
                Material bloc = p.getWorld().getBlockAt(new Location(p.getWorld(), SignAPI.x(e.getValue().getId()), SignAPI.y(e.getValue().getId()), SignAPI.z(e.getValue().getId()))).getType();
                if(bloc != Material.SIGN && bloc != Material.SIGN_POST && bloc != Material.WALL_SIGN)
                {
                    regions.add(e.getValue().getId());
                    Bukkit.broadcastMessage(FileAPI.translate("region") + e.getValue().getId() + FileAPI.translate("has.been.removed.because.absence.of.sign"));
                }
            }
        }
        for(int i = 0; i < regions.size(); i++)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg remove " + regions.get(i) + " -w " + Bukkit.getWorlds().get(0).getName());
        pl.print(FileAPI.translate("house") + FileAPI.translate("end.of.removing.regions.without.sign"));
        pl.print(FileAPI.translate("house") + FileAPI.translate("beginning.of.solds.by.inactivity"));
        File config = new File(FileAPI.plac + "config.txt");
        Scanner scan = new Scanner(config);
        String time = scan.nextLine();
        Date actualDate = new Date(), oldDate = FileAPI.stringToDate(time);
        scan.close();
        oldDate.setDate(oldDate.getDate() + 1);
        if(actualDate.after(oldDate))
        {
            FileWriter fw = new FileWriter(config);
            fw.write(actualDate.toLocaleString());
            fw.close();
            checkLoca = true;
            pl.print(FileAPI.translate("house") + FileAPI.translate("verification.of.rents.in.progress"));
        }
        else
            pl.print(FileAPI.translate("house") + FileAPI.translate("no.verification.of.rents.to.do"));
        r = pl.getWorldGuard().getRegionManager(p.getWorld()).getRegions().entrySet().iterator();
        while(r.hasNext())
        {
            Entry<String, ProtectedRegion> er = r.next();
            if(er.getValue().hasMembersOrOwners() && er.getValue().getId().length() >= 7 && er.getValue().getId().contains("_"))
                if(!er.getValue().getId().startsWith("l") && !er.getValue().getId().startsWith("g") && !er.getValue().getId().startsWith("r"))
                {
                    Iterator<String> i = er.getValue().getMembers().getPlayers().iterator();
                    long ple = 0;
                    while(i.hasNext())
                    {
                        long t = Bukkit.getOfflinePlayer(i.next()).getLastPlayed();
                        if(t > ple)
                            ple = t;
                    }
                    Date j = new Date();
                    j.setTime(ple);
                    j.setDate((j.getDate() + 14));
                    if(actualDate.after(j))
                    {
                        WEWGAPI.sellPermRegion(er.getValue(), p.getWorld());
                        pl.print(FileAPI.translate("house") + er.getValue().getId() + FileAPI.translate("sold.because.inactivity.of.members") + er.getValue().getMembers().toPlayersString() + FileAPI.translate("old.player.time") + j.toLocaleString() + FileAPI.translate("actual.time") + actualDate.toLocaleString());
                    }
                }
                else if(checkLoca && !er.getValue().getId().startsWith("g"))
                {
                    if(((Sign)p.getWorld().getBlockAt(new Location(p.getWorld(), SignAPI.x(er.getValue().getId()), SignAPI.y(er.getValue().getId()), SignAPI.z(er.getValue().getId()))).getState()).getLine(2).equalsIgnoreCase(FileAPI.translate("the.day")) || ((Sign)p.getWorld().getBlockAt(new Location(p.getWorld(), SignAPI.x(er.getValue().getId()), SignAPI.y(er.getValue().getId()), SignAPI.z(er.getValue().getId()))).getState()).getLine(2).equalsIgnoreCase("/house " + FileAPI.translate("for")))
                    {
                        pl.print(FileAPI.translate("house.to.rent") + er.getValue().getId() + FileAPI.translate("sold.because.defective.sign.members") + er.getValue().getMembers().toPlayersString());
                        WEWGAPI.sellLocaRegion(er.getValue(), p.getWorld());
                    }
                    else if(SignAPI.day(p.getWorld(), er.getValue().getId()) <= 1)
                    {
                        WEWGAPI.sellLocaRegion(er.getValue(), p.getWorld());
                        pl.print(FileAPI.translate("house.to.rent") + er.getValue().getId() + FileAPI.translate("sold.because.renewal.of.the.lease.members") + er.getValue().getMembers().toPlayersString());
                    }
                }
        }
        pl.print(FileAPI.translate("house") + FileAPI.translate("end.of.sales.by.inactivity"));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) throws Exception
    {
        if(first)
            checkHouses(e.getPlayer());
        if((e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) && pl.handcuff.contains(e.getPlayer()))
        {
            if(isEscorted(e.getPlayer()))
            {
                if(e.getPlayer().getVehicle() == null)
                {
                    e.getPlayer().sendMessage(FileAPI.translate("you.cannot.do.this.while.you.are.escorted"));
                    pl.escort.get(e.getPlayer()).sendMessage(FileAPI.translate("msg.bad") + e.getPlayer().getName() + FileAPI.translate("tries.to.leave"));
                    pl.escort.get(e.getPlayer()).setPassenger(e.getPlayer());
                }
                return;
            }
            e.getPlayer().sendMessage(FileAPI.translate("you.cannot.do.this.while.you.are.cuffed"));
            e.setCancelled(true);
            e.getPlayer().teleport(e.getFrom());
        }
    }

    public boolean isCuffed(Player p)
    {
        return pl.handcuff.contains(p);
    }

    public boolean isEscorted(Player p)
    {
        return pl.escort.containsKey(p);
    }

    public void cuff(Player i, Player p)
    {
        if(p != null)
            if(!p.equals(i))
                if(!isCuffed(p))
                    if(i.getLocation().distance(p.getLocation()) < 8)
                    {
                        pl.handcuff.add(p);
                        p.sendMessage("!SAFE!");
                        p.sendMessage(FileAPI.translate("you.have.been.cuffed.by") + i.getName() + FileAPI.translate("end.of.sentence"));
                        i.sendMessage("!CUFF! " + p.getName());
                        i.sendMessage(FileAPI.translate("you.have.cuffed") + p.getName() + FileAPI.translate("end.of.sentence"));
                    }
                    else
                        i.sendMessage(FileAPI.translate("you.are.too.far.from") + p.getName() + FileAPI.translate("end.of.sentence"));
                else
                    i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.already.cuffed"));
            else
                i.sendMessage(FileAPI.translate("you.cannot.cuff.yourself"));
        else
            i.sendMessage(FileAPI.translate("player.not.found"));
    }

    public void uncuff(Player i, Player p)
    {
        if(p != null)
            if(!p.equals(i))
                if(isCuffed(p))
                    if(i.getLocation().distance(p.getLocation()) < 8)
                    {
                        pl.handcuff.remove(p);
                        p.sendMessage("!NOTSAFE!");
                        p.sendMessage(FileAPI.translate("you.have.been.uncuffed.by") + i.getName() + FileAPI.translate("end.of.sentence"));
                        i.sendMessage("!UNCUFF! " + p.getName());
                        i.sendMessage(FileAPI.translate("you.have.uncuffed") + p.getName() + FileAPI.translate("end.of.sentence"));
                    }
                    else
                        i.sendMessage(FileAPI.translate("you.are.too.far.from") + p.getName() + FileAPI.translate("end.of.sentence"));
                else
                    i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.not.cuffed"));
            else
                i.sendMessage(FileAPI.translate("you.cannot.uncuff.yourself"));
        else
            i.sendMessage(FileAPI.translate("player.not.found"));
    }

    public void escort(Player i, Player p)
    {
        if(p != null)
            if(!p.equals(i))
                if(isCuffed(p))
                    if(!isEscorted(p))
                        if(i.getLocation().distance(p.getLocation()) < 8)
                        {
                            pl.escort.put(p, i);
                            i.setPassenger(p);
                            p.sendMessage(FileAPI.translate("you.are.escorted.by") + i.getName() + FileAPI.translate("end.of.sentence"));
                            i.sendMessage("!ESCORT! " + p.getName());
                            i.sendMessage(FileAPI.translate("you.escort") + p.getName() + FileAPI.translate("end.of.sentence"));
                        }
                        else
                            i.sendMessage(FileAPI.translate("you.are.too.far.from") + p.getName() + FileAPI.translate("end.of.sentence"));
                    else
                        i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.already.escroted"));
                else
                    i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.not.cuffed"));
            else
                i.sendMessage(FileAPI.translate("you.cannot.unescort.yourself"));
        else
            i.sendMessage(FileAPI.translate("player.not.found"));
    }

    public void unescort(Player i, Player p)
    {
        if(p != null)
            if(!p.equals(i))
                if(isCuffed(p))
                    if(isEscorted(p))
                        if(i.getLocation().distance(p.getLocation()) < 8)
                        {
                            pl.escort.remove(p);
                            p.leaveVehicle();
                            p.teleport(i);
                            p.sendMessage(FileAPI.translate("you.have.been.unescorted.by") + i.getName() + FileAPI.translate("end.of.sentence"));
                            i.sendMessage("!UNESCORT! " + p.getName());
                            i.sendMessage(FileAPI.translate("you.have.unescorted") + p.getName() + FileAPI.translate("end.of.sentence"));
                        }
                        else
                            i.sendMessage(FileAPI.translate("you.are.too.far.from") + p.getName() + FileAPI.translate("end.of.sentence"));
                    else
                        i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.not.escorted"));
                else
                    i.sendMessage(FileAPI.translate("msg.bad") + p.getName() + FileAPI.translate("is.not.cuffed"));
            else
                i.sendMessage(FileAPI.translate("you.cannot.unescorte.yourself"));
        else
            i.sendMessage(FileAPI.translate("player.not.found"));
    }

    public void friend(Player i, Player p, boolean isFriend)
    {
        if(p != null)
            if(i.getLocation().distance(p.getLocation()) < 8)
                if(isFriend)
                {
                    p.sendMessage("!FRIEND! " + i.getUniqueId() + " " + isFriend);
                    p.sendMessage(i.getName() + FileAPI.translate("has.destroyed.the.friendship.with.you"));
                    i.sendMessage(FileAPI.translate("you.are.not.anymore.the.friend.of") + p.getName() + FileAPI.translate("end.of.sentence"));
                }
                else
                {
                    p.sendMessage("!FRIEND! " + i.getUniqueId() + " " + isFriend);
                    p.sendMessage(i.getName() + FileAPI.translate("has.founded.a.friendship.with.you"));
                    i.sendMessage(FileAPI.translate("you.have.become.the.friend.of") + p.getName() + FileAPI.translate("end.of.sentence"));
                }
            else
                i.sendMessage(FileAPI.translate("you.are.too.far.from") + p.getName() + FileAPI.translate("end.of.sentence"));
    }

    void buy(Player p, String region)
    {
        File dataFile = new File(playersData + p.getUniqueId() + ".dat");
        long money = 0, moneyNeeded = SignAPI.price(region);
        if(dataFile.exists())
        {
            MCNBTDataAPI data = new MCNBTDataAPI(dataFile);
            if(data != null)
                if(data.hasCompoundTag(AltisCraft.NAME))
                    money = Long.parseLong(data.valueKeyInCompoundTag(data.getCompoundTag(AltisCraft.NAME), "Money"));
        }
        if(money >= moneyNeeded)
        {
            p.chat("!HOUSE!" + moneyNeeded);
            ProtectedRegion rg = WEWGAPI.smallRegion(pl.getWorldGuard(), p.getWorld(), p.getLocation());
            Sign sign = (Sign)p.getWorld().getBlockAt(new Location(p.getWorld(), SignAPI.x(rg.getId()), SignAPI.y(rg.getId()), SignAPI.z(rg.getId()))).getState();
            sign.setLine(0, FileAPI.translate("house.of"));
            sign.setLine(1, p.getName());
            if(rg.getId().startsWith("l"))
            {
                int day = 1;
                if(rg.isMember(p.getName()))
                    day = SignAPI.day(p.getWorld(), rg.getId()) + 1;
                sign.setLine(2, FileAPI.translate("rented") + day + FileAPI.translate("abbreviation.day.to"));
                p.sendMessage(FileAPI.translate("you.have.rented.this.house"));
            }
            else
            {
                sign.setLine(2, FileAPI.translate("bought.at"));
                p.sendMessage(FileAPI.translate("you.have.bought.this.house"));
            }
            sign.setLine(3, FileAPI.number(SignAPI.price(rg.getId())));
            sign.update();
            rg.getMembers().addPlayer(p.getName());
        }
        else
            p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent !");
    }
}