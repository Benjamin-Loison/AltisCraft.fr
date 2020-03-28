package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GPS extends Item
{
    public GPS()
    {
        setUnlocalizedName("gps");
        setTextureName(AltisCraft.MODID + ":GPS");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        ClientProxy.gps = !ClientProxy.gps;
        return itemstack;
    }
}
