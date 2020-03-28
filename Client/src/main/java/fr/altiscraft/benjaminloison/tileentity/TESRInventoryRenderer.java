package fr.altiscraft.benjaminloison.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class TESRInventoryRenderer implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if(block == AltisCraft.atm)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityATMSpecialRenderer.texture);
            TileEntityATMSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.fan)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityFanSpecialRenderer.texture);
            TileEntityFanSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.vFloorLamp)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityVFloorLampSpecialRenderer.texture);
            TileEntityVFloorLampSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.vATM)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityVATMSpecialRenderer.texture);
            TileEntityVATMSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.bin)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityBinSpecialRenderer.texture);
            TileEntityBinSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.cocoBlock)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityCocoSpecialRenderer.texture);
            TileEntityCocoSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.fireplace)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityFireplaceSpecialRenderer.texture);
            TileEntityFireplaceSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.emptyFireplace)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityEmptyFireplaceSpecialRenderer.texture);
            TileEntityEmptyFireplaceSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.floorLamp)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityFloorLampSpecialRenderer.texture);
            TileEntityFloorLampSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.fence)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityFenceSpecialRenderer.texture);
            TileEntityFenceSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.stop)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntitySTOPSpecialRenderer.texture);
            TileEntitySTOPSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.pedestrian)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityPedestrianSpecialRenderer.texture);
            TileEntityPedestrianSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.noEntry)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityNoEntrySpecialRenderer.texture);
            TileEntityNoEntrySpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.basket)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityBasketSpecialRenderer.texture);
            TileEntityBasketSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.shutter)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityShutterSpecialRenderer.texture);
            TileEntityShutterSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.frenchFlag)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityFlagSpecialRenderer.texture);
            TileEntityFlagSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
        else if(block == AltisCraft.stool)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(270, 0, 1, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0, -1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityStoolSpecialRenderer.texture);
            TileEntityStoolSpecialRenderer.model.renderAll();
            GL11.glPopMatrix();
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        return false;
    }

    public int getRenderId()
    {
        return ClientProxy.tesrRenderId;
    }

    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
}
