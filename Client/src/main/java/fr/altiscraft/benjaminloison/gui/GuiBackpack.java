package fr.altiscraft.benjaminloison.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiBackpack extends InventoryEffectRenderer
{
    private float xSizeFloat, ySizeFloat;

    public GuiBackpack(EntityPlayer p)
    {
        super(p.inventoryContainer);
        allowUserInput = true;
    }

    public void updateScreen()
    {
        if(mc.playerController.isInCreativeMode())
            mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
    }

    public void initGui()
    {
        buttonList.clear();
        if(mc.playerController.isInCreativeMode())
            mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
        else
            super.initGui();
    }

    protected void drawGuiContainerForegroundLayer(int a0, int a1)
    {
        fontRendererObj.drawString(I18n.format("item.backpack.name"), 86, 16, 4210752);
    }

    public void drawScreen(int x, int y, float f)
    {
        super.drawScreen(x, y, f);
        xSizeFloat = (float)x;
        ySizeFloat = (float)y;
    }

    protected void drawGuiContainerBackgroundLayer(float f0, int a0, int a1)
    {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(field_147001_a);
        int k = guiLeft, l = guiTop;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        renderPlayer(k + 51, l + 75, 30, (float)(k + 51) - xSizeFloat, (float)(l + 75 - 50) - ySizeFloat, mc.thePlayer);
    }

    public static void renderPlayer(int a0, int a1, int a2, float b0, float b1, EntityLivingBase e)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)a0, (float)a1, 50);
        GL11.glScalef((float)(-a2), (float)a2, (float)a2);
        GL11.glRotatef(180, 0, 0, 1);
        float f2 = e.renderYawOffset, f3 = e.rotationYaw, f4 = e.rotationPitch, f5 = e.prevRotationYawHead, f6 = e.rotationYawHead;
        GL11.glRotatef(135, 0, 1, 0);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135, 0, 1, 0);
        GL11.glRotatef(-((float)Math.atan((double)(b1 / 40))) * 20, 1, 0, 0);
        e.renderYawOffset = (float)Math.atan((double)(b0 / 40)) * 20;
        e.rotationYaw = (float)Math.atan((double)(b0 / 40)) * 40;
        e.rotationPitch = -((float)Math.atan((double)(b1 / 40))) * 20;
        e.rotationYawHead = e.rotationYaw;
        e.prevRotationYawHead = e.rotationYaw;
        GL11.glTranslatef(0, e.yOffset, 0);
        RenderManager.instance.playerViewY = 180;
        RenderManager.instance.renderEntityWithPosYaw(e, 0, 0, 0, 0, 1);
        e.renderYawOffset = f2;
        e.rotationYaw = f3;
        e.rotationPitch = f4;
        e.prevRotationYawHead = f5;
        e.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}