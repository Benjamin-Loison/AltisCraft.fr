package fr.benjaminloison.altiscraft.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Throwables;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.benjaminloison.altiscraft.api.FileAPI;

public class AltisCraft extends JavaPlugin
{
    public static final String MODID = "altiscraft", NAME = "AltisCraft", ID = "ALTISCRAFT_";
    public File config = new File(FileAPI.plac + "config.yml");
    public static Map<String, PermissionAttachment> perm = new HashMap<String, PermissionAttachment>();
    public ArrayList<Player> handcuff = new ArrayList<Player>();
    public HashMap<Player, Player> escort = new HashMap<Player, Player>();
    static AltisCraft plugin;

    public WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null || !(plugin instanceof WorldGuardPlugin))
            return null;
        return (WorldGuardPlugin)plugin;
    }

    public WorldEditPlugin getWorldEdit()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if(plugin == null || !(plugin instanceof WorldEditPlugin))
            return null;
        return (WorldEditPlugin)plugin;
    }

    @Override
    public void onEnable()
    {
        plugin = this;
        print("Launching...");
        if(!new File("").getAbsolutePath().contains((char)65 + "" + (char)108 + "" + (char)116 + "" + (char)105 + "" + (char)115 + "" + (char)67 + "" + (char)114 + "" + (char)97 + "" + (char)102 + "" + (char)116 + "" + (char)46 + "" + (char)102 + "" + (char)114))
            Throwables.propagate(new Throwable("Server non authorized !"));
        FileAPI.languageFile.mkdirs();
        new File(FileAPI.spawn).mkdirs();
        new EventController(this);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "ChestCommands", new PacketListener(this));
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "Gang", new PacketListener(this));
        Bukkit.addRecipe(new FurnaceRecipe(new ItemStack(Material.getMaterial(ID + "COPPER"), 1), Material.getMaterial(ID + "COPPER_ORE")));
        Material mCannabis = Material.getMaterial(ID + "CANNABIS_BAG"), cannabis0 = Material.getMaterial(ID + "CANNABIS");
        ShapedRecipe cocaine = new ShapedRecipe(new ItemStack(Material.getMaterial(ID + "COCAINE_BAG"), 1)), cannabis1 = new ShapedRecipe(new ItemStack(mCannabis, 1)), lsd = new ShapedRecipe(new ItemStack(Material.getMaterial(ID + "LSD_FLASK"), 1));
        cocaine.shape("#X#", "%Y%", "VHV");
        cocaine.setIngredient('#', Material.getMaterial(ID + "COCAINE_0"));
        cocaine.setIngredient('X', Material.STRING);
        cocaine.setIngredient('Y', Material.PAPER);
        cocaine.setIngredient('%', Material.SLIME_BALL);
        cocaine.setIngredient('V', Material.getMaterial(ID + "COCAINE_1"));
        cocaine.setIngredient('H', Material.getMaterial(ID + "COCAINE_2"));
        cocaine.shape("#X#", "%Y%", "###");
        cocaine.setIngredient('#', cannabis0);
        cocaine.setIngredient('X', Material.STRING);
        cocaine.setIngredient('%', Material.PAPER);
        cocaine.setIngredient('Y', Material.SLIME_BALL);
        cannabis1.shape("%%%", "#Y#", "%%%");
        cannabis1.setIngredient('#', cannabis0);
        cannabis1.setIngredient('%', Material.PAPER);
        cannabis1.setIngredient('Y', mCannabis);
        lsd.shape("#X#", "#Y#", "###");
        lsd.setIngredient('#', Material.GLASS);
        lsd.setIngredient('X', Material.LOG);
        lsd.setIngredient('Y', Material.getMaterial(ID + "LSD"));
        Bukkit.addRecipe(cannabis1);
        Bukkit.addRecipe(cocaine);
        Bukkit.addRecipe(lsd);
        FileAPI.ifNotExisteWrite(config, new Date().toLocaleString());
        print("Launched !");
    }

    @Override
    public void onDisable()
    {
        Bukkit.savePlayers();
        short received = 0;
        if(FileAPI.translate("autorestart").equalsIgnoreCase("true"))
        {
            while(received == 0)
                try
                {
                    String message = "O " + FileAPI.config("restart.time.millis");
                    DatagramSocket socket = new DatagramSocket();
                    DatagramPacket receivedData = new DatagramPacket(new byte[1024], 1024);
                    socket.setSoTimeout(Integer.parseInt(FileAPI.translate("restart.time.millis.timeout.packet")));
                    socket.send(new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(FileAPI.translate("authentification.server.ip")), Integer.parseInt(FileAPI.translate("authentification.server.port"))));
                    print("Packet restart send");
                    socket.receive(receivedData);
                    print("Packet restart received");
                    received = Short.parseShort(new String(receivedData.getData(), 0, receivedData.getLength()));
                    if(received == 1)
                        break;
                    socket.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            kickAll(FileAPI.translate("restart.message"));
        }
    }

    public void kickAll(String message)
    {
        for(Player player : getServer().getOnlinePlayers())
            player.kickPlayer(message);
    }

    public static void print(Object object)
    {
        plugin.getLogger().info(object.toString());
    }
}