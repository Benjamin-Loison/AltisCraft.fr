package fr.benjaminloison.altiscraft.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteStreams;
import com.sk89q.jnbt.CompoundTag;

import fr.benjaminloison.altiscraft.api.FileAPI;
import fr.benjaminloison.altiscraft.api.MCNBTDataAPI;

public class PacketListener implements PluginMessageListener
{
    AltisCraft pl;

    public PacketListener(AltisCraft plugin)
    {
        this.pl = plugin;
    }

    @Override
    public void onPluginMessageReceived(String msg, Player p, byte[] message)
    {
        String name = p.getName(), inf = ByteStreams.newDataInput(message).readUTF();
        if(msg.equals("ChestCommands"))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chestcommands open " + inf + " " + name);
        else if(msg.equals("Gang"))
        {
            String gang = FileAPI.findGang(name);
            if(inf.startsWith("Leave"))
            {
                if(inf.replace("Leave", "").equals("false"))
                    return;
                MCNBTDataAPI data = new MCNBTDataAPI(new File(FileAPI.path + Bukkit.getWorlds().get(0).getName() + File.separatorChar + "playerdata" + File.separatorChar + p.getUniqueId() + ".dat"));
                if(data != null)
                    if(data.hasCompoundTag(AltisCraft.NAME))
                    {
                        CompoundTag cmpt = data.getCompoundTag(AltisCraft.NAME);
                        gang = "Civil";
                        if(data.getBooleanFromByte(data.valueKeyInCompoundTag(cmpt, "VIP")))
                            gang = "VIP";
                        if(data.getBooleanFromByte(data.valueKeyInCompoundTag(cmpt, "VIPP")))
                            gang = "VIP+";
                    }
            }
            else if(!FileAPI.isOfficial(gang))
                return;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + name + " " + gang);
        }
    }
}