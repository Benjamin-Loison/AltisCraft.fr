package fr.altiscraft.benjaminloison.proxy;

import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.model.ModelExplosiveVest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderExplosiveVest implements IItemRenderer
{
    protected ModelExplosiveVest model;
    protected static final ResourceLocation texture = new ResourceLocation(AltisCraft.MODID + ":textures/models/armor/Explosive Vest.png");

    public RenderExplosiveVest()
    {
        model = new ModelExplosiveVest();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch(type)
        {
            case EQUIPPED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch(type)
        {
            case EQUIPPED:
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render((Entity)data[1], 0, 0, 0, 0, 0, 0.0625F);
                GL11.glPopMatrix();
                break;
            }
            default:
                break;
        }
    }
}