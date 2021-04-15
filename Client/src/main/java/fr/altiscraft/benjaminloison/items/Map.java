package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Map extends Item
{
    public Map()
    {
        setUnlocalizedName("map");
        setTextureName(AltisCraft.MODID + ":Map");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        ClientProxy.map();
        return itemstack;
    }
}