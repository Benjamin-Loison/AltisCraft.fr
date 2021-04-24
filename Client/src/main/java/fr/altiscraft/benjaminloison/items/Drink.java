package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class Drink extends ItemFood
{
    public Drink(String unlocalizedName, String texture, int size, int healAmount, float saturationModifier)
    {
        super(healAmount, saturationModifier, true);
        setUnlocalizedName(unlocalizedName);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setAlwaysEdible();
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(size);
    }

    protected void onFoodEaten(ItemStack stack, net.minecraft.world.World world, EntityPlayer player)
    {
        super.onFoodEaten(stack, world, player);
    }

    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.drink;
    }
}