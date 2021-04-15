package fr.altiscraft.benjaminloison.items;

import java.util.List;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomItem extends Item
{
    String info;

    public CustomItem(String info, String name, String texture, int stackSize)
    {
        this.info = info;
        setCreativeTab(AltisCraft.altisCraftTab);
        setUnlocalizedName(name);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setMaxStackSize(stackSize);
    }

    public CustomItem(String info, String name, String texture)
    {
        this.info = info;
        setCreativeTab(AltisCraft.altisCraftTab);
        setUnlocalizedName(name);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setMaxStackSize(1);
    }

    public CustomItem(String info, String name, String texture, boolean logo)
    {
        this.info = info;
        setUnlocalizedName(name);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setMaxStackSize(1);
    }

    public CustomItem(String name, String texture, int stackSize)
    {
        info = "";
        setCreativeTab(AltisCraft.altisCraftTab);
        setUnlocalizedName(name);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setMaxStackSize(stackSize);
    }

    public CustomItem(String name, String texture)
    {
        info = "";
        setCreativeTab(AltisCraft.altisCraftTab);
        setUnlocalizedName(name);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setMaxStackSize(1);
    }

    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bo)
    {
        if(!info.equals(""))
            list.add(I18n.format(info));
    }
}