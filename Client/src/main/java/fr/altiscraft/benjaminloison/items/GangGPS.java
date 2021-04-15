package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.entity.EEP;
import fr.altiscraft.benjaminloison.packets.PacketGPS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GangGPS extends Item
{
    public GangGPS()
    {
        setUnlocalizedName("gang_gps");
        setTextureName(AltisCraft.MODID + ":Gang GPS");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }
    
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int a, float b, float c, float o)
    {
        if(!EEP.get(player).gang.equals(""))
            AltisCraft.instance.network.sendToServer(new PacketGPS());
        else
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(I18n.format("you.are.not.in.a.gang")));
        return false;
    }
}