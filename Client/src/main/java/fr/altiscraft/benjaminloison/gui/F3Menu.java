package fr.altiscraft.benjaminloison.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.events.RenderTickHandler;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class F3Menu extends Gui
{
    private Minecraft mc;
    public static float speed = 0, maxSpeed = 0;
    public static int var = 5, fuel = 100, fuelMax = 200;
    private ResourceLocation logo = new ResourceLocation(AltisCraft.MODID + ":textures/gui/Logo.png");
    public static String vehicle = "", title = "Title", message = "Description", image = "Image";
    ScaledResolution sr;
    int width, height;

    public F3Menu(Minecraft mc)
    {
        super();
        this.mc = mc;
        sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        width = sr.getScaledWidth();
        height = sr.getScaledHeight();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event)
    {
        if(event.type != null)
            if(event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST && ClientProxy.rp)
            {
                mc.thePlayer.sendChatMessage(I18n.format("command.executed.for.player.list"));
                event.setCanceled(true);
                try
                {
                    Thread.sleep(250);
                }
                catch(Exception e)
                {}
            }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderDebugMenu(RenderGameOverlayEvent.Pre e)
    {
        if(e.type == RenderGameOverlayEvent.ElementType.DEBUG)
        {
            e.setCanceled(true);
            mc.renderEngine.bindTexture(logo);
            drawTexturedModalRect(0, 0, 0, 0, 64, 64);
            mc.fontRenderer.drawStringWithShadow(mc.debug.split(",", 2)[0], 15, 69, 14737632);
            mc.fontRenderer.drawString(AltisCraft.NAME, 70, 5, 255);
            mc.fontRenderer.drawString(I18n.format("website.url"), 70, 15, 14737632);
            mc.fontRenderer.drawString(I18n.format("teamspeak.with.ip"), 70, 25, 14737632);
        }
    }

    public static void antvOn(String title, String message, String image)
    {
        F3Menu.title = title;
        F3Menu.message = message;
        F3Menu.image = image;
        ClientProxy.antv = true;
        RenderTickHandler.antv = true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderANTV(RenderGameOverlayEvent.Post event)
    {
        if(event.type != ElementType.ALL)
            if(ClientProxy.antv)
            {
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Red.png"));
                drawTexturedModalRect(0, 0, 0, 0, mc.displayWidth, 68);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Dark Red.png"));
                drawTexturedModalRect(0, 0, 0, 0, mc.displayWidth, 10);
                mc.fontRenderer.drawString(I18n.format("information.alert"), 2, 2, 0xFE9A2E);
                mc.fontRenderer.drawString(title + I18n.format("title.separator"), 67, 2, 0x58D3F7);
                GL11.glColor4f(1, 1, 1, 1);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/ANTV.png"));
                drawTexturedModalRect(2, 14, 0, 0, 256, 61);
                if(image.equals("Diamond"))
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Diamond Mine.png"));
                    drawTexturedModalRect(220, -42, 0, 0, 256, 256);
                }
                if(var < 145)
                    var = width - message.length();
                drawString(mc.fontRenderer, message, var, 2, 0xFFFFFF);
            }
    }

    @SubscribeEvent
    public void onRenderInfo(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.HELMET)
            if(ClientProxy.info && fuelMax != 0)
            {
                float oil = fuel * 100 / fuelMax;
                String realSpeed = Math.round(speed * maxSpeed * 57) + I18n.format("speed");
                int size = Math.round(speed * maxSpeed * 57);
                drawString(mc.fontRenderer, I18n.format("item." + vehicle + ".name"), width - 45, 9, 0xFFFFFF);
                drawCenteredString(mc.fontRenderer, "CAR", width - 36, 28, 0xBDBDBD);
                drawCenteredString(mc.fontRenderer, "PNE", width - 36, 43, 0xBDBDBD);
                drawCenteredString(mc.fontRenderer, "MOT", width - 12, 28, 0xBDBDBD);
                drawCenteredString(mc.fontRenderer, "ESS", width + 12, 28, 0xBDBDBD);
                if(size < 10 && size >= 0)
                    drawString(mc.fontRenderer, realSpeed, width + 15, 43, 0xFFFFFF);
                else if(size >= 10 && size < 100)
                    drawString(mc.fontRenderer, realSpeed, width + 9, 43, 0xFFFFFF);
                else if(size >= 100 && size < 1000)
                    drawString(mc.fontRenderer, realSpeed, width + 3, 43, 0xFFFFFF);
                else if(size < 0 && size > -10)
                    drawString(mc.fontRenderer, realSpeed, width + 17, 43, 0xFFFFFF);
                else if(size <= -10 && size > -100)
                    drawString(mc.fontRenderer, realSpeed, width + 3, 43, 0xFFFFFF);
                else if(size <= -100 && size > -1000)
                    drawString(mc.fontRenderer, realSpeed, width - 3, 43, 0xFFFFFF);
                GL11.glColor4f(1, 1, 1, 1);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                drawTexturedModalRect(width - 50, 5, 0, 0, 100, 50);
                if(oil < 50 && oil >= 20)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Orange Background.png"));
                    drawTexturedModalRect(width - 50, 20, 0, 0, Math.round(oil), 3);
                }
                else if(oil < 20)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Red Background.png"));
                    drawTexturedModalRect(width - 50, 20, 0, 0, Math.round(oil), 3);
                }
                else if(oil >= 50)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                    drawTexturedModalRect(width - 50, 20, 0, 0, Math.round(oil), 3);
                }
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                drawTexturedModalRect(width - 47, 25, 0, 0, 22, 13);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                drawTexturedModalRect(width - 47, 40, 0, 0, 22, 13);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                drawTexturedModalRect(width - 23, 25, 0, 0, 22, 13);
                mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Light Background.png"));
                drawTexturedModalRect(width + 1, 25, 0, 0, 22, 13);
            }
    }

    @SubscribeEvent
    public void onRenderGPS(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.ALL)
        {
            EntityPlayer player = mc.thePlayer;
            if(player.inventory.hasItem(AltisCraft.gps) || !ClientProxy.rp)
                if(ClientProxy.gps)
                {
                    int posX = (int)player.posX, posY = (int)player.posY, posZ = (int)player.posZ, var = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                    String direction = null;
                    switch(var)
                    {
                        case 0:
                            direction = I18n.format("south");
                            break;
                        case 1:
                            direction = I18n.format("west");
                            break;
                        case 2:
                            direction = I18n.format("north");
                            break;
                        case 3:
                            direction = I18n.format("east");
                            break;
                    }
                    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                    int width = sr.getScaledWidth() / 2, height = sr.getScaledHeight() / 2 + 10;
                    drawCenteredString(mc.fontRenderer, I18n.format("coords", posX, posY, posZ), width, 5, 0x01DF01);
                    if(direction != null)
                        drawCenteredString(mc.fontRenderer, I18n.format("direction.is") + direction, width, 15, 0xFF0000);
                }
        }
    }
}