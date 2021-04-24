package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.gui.GuiPhone;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Phone extends Item
{
    public Phone()
    {
        setUnlocalizedName("phone");
        setTextureName(AltisCraft.MODID + ":Phone");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }
    
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer entity)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiPhone(true));
        return item;
    }
}