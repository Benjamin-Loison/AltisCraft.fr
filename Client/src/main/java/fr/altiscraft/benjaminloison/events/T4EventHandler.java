package fr.altiscraft.benjaminloison.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import fr.altiscraft.benjaminloison.gui.GuiSpawn;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

public class T4EventHandler
{
    @SubscribeEvent
    public void onPlayerTickEvent(PlayerTickEvent e)
    {
        if(e.player.worldObj.getBlock(((int)Math.floor(e.player.posX)), ((int)(e.player.posY - e.player.getYOffset())) - 1, ((int)Math.floor(e.player.posZ))) == Blocks.coal_block && e.player.onGround && ClientProxy.rp && Minecraft.getMinecraft().currentScreen == null)
            Minecraft.getMinecraft().displayGuiScreen(new GuiSpawn());
    }
}
