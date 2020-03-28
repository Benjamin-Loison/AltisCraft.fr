package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.gui.GuiBackpack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Backpack extends Item
{
    public Backpack()
    {
        setUnlocalizedName("backpack");
        setTextureName(AltisCraft.MODID + ":Backpack");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBackpack(Minecraft.getMinecraft().thePlayer));
        return itemstack;
    }
}