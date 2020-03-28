package fr.altiscraft.benjaminloison.gui;

import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class CustomCenterInvisibleRealButton extends GuiButton
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation(AltisCraft.MODID, "textures/gui/Transparent.png");

    public CustomCenterInvisibleRealButton(int id, int xPosition, int yPosition, String name, int x, int y)
    {
        super(id, xPosition, yPosition, x, y, name);
    }

    public void drawButton(Minecraft mc, int x, int y)
    {
        if(visible)
        {
            mc.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1, 1, 1, 1);
            field_146123_n = x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
            int k = getHoverState(field_146123_n), l = 14737632;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
            drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
            mouseDragged(mc, x, y);
            if(packedFGColour != 0)
                l = packedFGColour;
            else if(!enabled)
                l = 10526880;
            else if(field_146123_n)
                l = 16777120;
            drawCenteredString(mc.fontRenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, l);
        }
    }
}