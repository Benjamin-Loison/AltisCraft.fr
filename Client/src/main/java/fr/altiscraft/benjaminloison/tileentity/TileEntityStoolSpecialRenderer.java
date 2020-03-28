package fr.altiscraft.benjaminloison.tileentity;

import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.model.ModelStool;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityStoolSpecialRenderer extends TileEntitySpecialRenderer
{
    public static ModelStool model = new ModelStool();
    public static ResourceLocation texture = new ResourceLocation(AltisCraft.MODID, "textures/models/blocks/Stool.png");

    public TileEntityStoolSpecialRenderer()
    {
        func_147497_a(TileEntityRendererDispatcher.instance);
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialRenderTick)
    {
        renderTileEntityTabouretAt((TileEntityStool)tile, x, y, z, partialRenderTick);
    }

    private void renderTileEntityTabouretAt(TileEntityStool tile, double x, double y, double z, float partialRenderTick)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180, 0, 0, 1);
        bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }
}