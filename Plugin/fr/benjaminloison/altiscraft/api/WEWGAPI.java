package fr.benjaminloison.altiscraft.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WEWGAPI
{
    public static void sellPermRegion(ProtectedRegion r, World w)
    {
        r.getMembers().removeAll();
        Sign sign = (Sign)w.getBlockAt(new Location(w, SignAPI.x(r.getId()), SignAPI.y(r.getId()), SignAPI.z(r.getId()))).getState();
        sign.setLine(0, FileAPI.translate("house.to.sell.sign"));
        sign.setLine(1, FileAPI.translate("at") + " " + FileAPI.number(SignAPI.price(r.getId())) + " " + FileAPI.translate("currency"));
        sign.setLine(2, "/house " + FileAPI.translate("for"));
        sign.setLine(3, FileAPI.translate("interact"));
        sign.update();
    }

    public static void sellLocaRegion(ProtectedRegion r, World w)
    {
        r.getMembers().removeAll();
        Sign sign = (Sign)w.getBlockAt(new Location(w, SignAPI.x(r.getId()), SignAPI.y(r.getId()), SignAPI.z(r.getId()))).getState();
        sign.setLine(0, FileAPI.translate("house.to.rent.sign"));
        sign.setLine(1, FileAPI.translate("at") + " " + FileAPI.number(SignAPI.price(r.getId())) + " " + FileAPI.translate("currency"));
        sign.setLine(2, FileAPI.translate("the.day"));
        sign.setLine(3, "");
        sign.update();
    }

    public static boolean hasHouse(WorldGuardPlugin wg, World w, Player p)
    {
        Iterator<Entry<String, ProtectedRegion>> itRgs = wg.getRegionManager(w).getRegions().entrySet().iterator();
        while(itRgs.hasNext())
        {
            Entry<String, ProtectedRegion> rg = itRgs.next();
            if(rg.getValue().getId().contains("_") && !(rg.getValue()).getId().startsWith("g") && (rg.getValue()).isMember(p.getName()))
                return true;
        }
        return false;
    }

    public static String nameOfGang(WorldGuardPlugin wg, World w, String m)
    {
        if(w.getBlockAt(new Location(w, SignAPI.x(m), SignAPI.y(m), SignAPI.z(m))) != null)
            return ((Sign)w.getBlockAt(new Location(w, SignAPI.x(m), SignAPI.y(m), SignAPI.z(m))).getState()).getLine(2);
        return "";
    }

    public static boolean hasHouseGang(WorldGuardPlugin g, World w, Player p, String gang)
    {
        Iterator<Entry<String, ProtectedRegion>> i = g.getRegionManager(w).getRegions().entrySet().iterator();
        while(i.hasNext())
        {
            Entry<String, ProtectedRegion> r = i.next();
            String id = r.getValue().getId();
            if(id.contains("_") && id.startsWith("g") && id.split("_")[0].replaceFirst("g", "").equalsIgnoreCase(gang))
                return true;
        }
        return false;
    }

    public static ProtectedRegion houseOf(WorldGuardPlugin g, World w, Player p)
    {
        Iterator<Entry<String, ProtectedRegion>> i = g.getRegionManager(w).getRegions().entrySet().iterator();
        while(i.hasNext())
        {
            Entry<String, ProtectedRegion> r = i.next();
            if(r.getValue().getId().contains("_") && r.getValue().isMember(p.getName()))
                return r.getValue();
        }
        return null;
    }

    public static ProtectedRegion houseGangOf(WorldGuardPlugin g, World w, Player p, String gang)
    {
        Iterator<Entry<String, ProtectedRegion>> i = g.getRegionManager(w).getRegions().entrySet().iterator();
        while(i.hasNext())
        {
            Entry<String, ProtectedRegion> r = i.next();
            String id = r.getValue().getId();
            if(id.contains("_") && id.contains("g") && id.split("_")[0].replaceFirst("g", "").equalsIgnoreCase(gang))
                return r.getValue();
        }
        return null;
    }

    public static ProtectedRegion houseContains(WorldGuardPlugin g, World w, String m)
    {
        Iterator<Entry<String, ProtectedRegion>> i = g.getRegionManager(w).getRegions().entrySet().iterator();
        while(i.hasNext())
        {
            Entry<String, ProtectedRegion> r = i.next();
            if(StringUtils.containsIgnoreCase(r.getValue().getId(), m))
                return r.getValue();
        }
        return null;
    }

    public static boolean isInARegion(WorldGuardPlugin g, World w, Location l)
    {
        if(g.getRegionManager(w).getApplicableRegions(l).size() != 0)
            return true;
        return false;
    }

    public static ProtectedRegion smallRegion(WorldGuardPlugin g, World w, Location l)
    {
        Iterator<Entry<String, ProtectedRegion>> r = g.getRegionManager(w).getRegions().entrySet().iterator();
        ArrayList<ProtectedRegion> rgs = new ArrayList<ProtectedRegion>();
        while(r.hasNext())
        {
            ProtectedRegion rg = r.next().getValue();
            if(rg.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ()))
                rgs.add(rg);
        }
        ProtectedRegion rei = null;
        Iterator<ProtectedRegion> reg = rgs.iterator();
        int y = 1000;
        while(reg.hasNext())
        {
            ProtectedRegion rg = reg.next();
            int ny = rg.getMaximumPoint().getBlockY();
            if(ny < y)
            {
                y = ny;
                rei = rg;
            }
        }
        return rei;
    }
}