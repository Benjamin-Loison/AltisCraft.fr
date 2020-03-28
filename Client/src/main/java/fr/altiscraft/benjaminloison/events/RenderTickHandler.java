package fr.altiscraft.benjaminloison.events;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.common.driveables.EntitySeat;
import fr.altiscraft.benjaminloison.entity.EEP;
import fr.altiscraft.benjaminloison.gui.F3Menu;
import fr.altiscraft.benjaminloison.gui.GuiInv;
import fr.altiscraft.benjaminloison.packets.PacketSound;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class RenderTickHandler extends GuiScreen
{
    String message;
    int time, antvTime, var;
    public static int siren;
    public static boolean antv;
    private final ResourceLocation backGround = new ResourceLocation(AltisCraft.MODID, "textures/gui/Altis.png");

    @SubscribeEvent
    public void onRenderTick(TickEvent.ClientTickEvent event)
    {
        if(Minecraft.getMinecraft().thePlayer != null)
        {
            message = I18n.format("server.crashed.reconnecting");
            ClientProxy.crash = false;
            F3Menu.var--;
            time++;
            if(time % 60 == 0)
                AltisCraft.proxy.oneTime = true;
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            EEP props = EEP.get(player);
            if(AltisCraft.proxy.siren)
            {
                if(player.ridingEntity != null)
                {
                    if(siren % 800 == 0)
                    {
                        AltisCraft.instance.network.sendToServer(new PacketSound());
                        siren = 0;
                    }
                    siren++;
                }
                else
                {
                    AltisCraft.proxy.siren = false;
                    siren = 0;
                    player.addChatMessage(new ChatComponentText(I18n.format("siren.desactivated")));
                }
            }
            if(antv)
            {
                if(antvTime > 1000)
                {
                    ClientProxy.antv = false;
                    antvTime = 0;
                    antv = false;
                }
                antvTime++;
            }
            if(player.inventory.getCurrentItem() != null)
                if(player.inventory.getCurrentItem().getItem().equals(AltisCraft.map))
                    if(player.ridingEntity instanceof EntitySeat && Minecraft.getMinecraft().ingameGUI != null)
                        Minecraft.getMinecraft().displayGuiScreen(new GuiInv());
            Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
            for(int i = 0; i < ca.length; i++)
                if(ca[i].getType().equals(Type.STICK))
                {
                    ca[i].poll();
                    Component[] co = ca[i].getComponents();
                    float yaw = player.rotationYaw, pitch = player.rotationPitch;
                    for(int j = 0; j < co.length; j++)
                        if(co[j].getIdentifier().equals(Identifier.Axis.Y))
                            pitch -= (double)Math.round(co[j].getPollData() * 10) / 10;
                        else if(co[j].getIdentifier().equals(Identifier.Axis.RZ))
                            yaw += (double)Math.round(co[j].getPollData() * 10) / 10;
                    player.setPositionAndRotation(player.posX, player.posY, player.posZ, yaw, pitch);
                }
        }
        if(ClientProxy.crash)
        {
            GL11.glViewport(0, 0, 256, 256);
            Minecraft.getMinecraft().getTextureManager().bindTexture(backGround);
            GL11.glDisable(3553);
            GL11.glEnable(3553);
            GL11.glViewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            tessellator.setColorRGBA_F(1, 1, 1, 1);
            int k = width, l = height;
            tessellator.addVertexWithUV(0, 0, zLevel, 0, 0);
            tessellator.addVertexWithUV(0, l, zLevel, 0, 1);
            tessellator.addVertexWithUV(k, l, zLevel, 1, 1);
            tessellator.addVertexWithUV(k, 0, zLevel, 1, 0);
            tessellator.draw();
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Red.png"));
            drawTexturedModalRect(0, 0, 0, 0, Minecraft.getMinecraft().displayWidth, 68);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Dark Red.png"));
            drawTexturedModalRect(0, 0, 0, 0, Minecraft.getMinecraft().displayWidth, 10);
            Minecraft.getMinecraft().fontRenderer.drawString(I18n.format("information.alert"), 2, 2, 0xFE9A2E);
            Minecraft.getMinecraft().fontRenderer.drawString("CRASH" + I18n.format("title.separator"), 67, 2, 0x58D3F7);
            GL11.glColor4f(1, 1, 1, 1);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/ANTV.png"));
            drawTexturedModalRect(2, 14, 0, 0, 256, 61);
            if(var < 105)
                var = width - message.length();
            var--;
            drawString(Minecraft.getMinecraft().fontRenderer, message, var, 2, 0xFFFFFF);
        }
    }
}