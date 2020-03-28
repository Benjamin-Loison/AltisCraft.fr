package fr.altiscraft.benjaminloison.tileentity;

import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.model.ModelBin;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBinSpecialRenderer extends TileEntitySpecialRenderer
{
    public static ModelBin model = new ModelBin();
    public static ResourceLocation texture = new ResourceLocation(AltisCraft.MODID, "textures/models/blocks/Bin.png");

    public TileEntityBinSpecialRenderer()
    {
        func_147497_a(TileEntityRendererDispatcher.instance);
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialRenderTick)
    {
        renderTileEntityPoubelleAt((TileEntityBin)tile, x, y, z, partialRenderTick);
    }

    private void renderTileEntityPoubelleAt(TileEntityBin tile, double x, double y, double z, float partialRenderTick)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glRotatef(90 * tile.getDirection() + 180, 0, 1, 0);
        bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }
}