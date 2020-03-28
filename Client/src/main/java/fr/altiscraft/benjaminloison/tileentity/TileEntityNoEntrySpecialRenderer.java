package fr.altiscraft.benjaminloison.tileentity;

import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.model.ModelNoEntry;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityNoEntrySpecialRenderer extends TileEntitySpecialRenderer
{
    public static ModelNoEntry model = new ModelNoEntry();
    public static ResourceLocation texture = new ResourceLocation(AltisCraft.MODID, "textures/models/blocks/No Entry.png");

    public TileEntityNoEntrySpecialRenderer()
    {
        func_147497_a(TileEntityRendererDispatcher.instance);
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialRenderTick)
    {
        renderTileEntityPanneauInterditAt((TileEntityNoEntry)tile, x, y, z, partialRenderTick);
    }

    private void renderTileEntityPanneauInterditAt(TileEntityNoEntry tile, double x, double y, double z, float partialRenderTick)
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