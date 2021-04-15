package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockManyNames extends ItemBlock
{
    public ItemBlockManyNames(Block b)
    {
        super(b);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "_" + stack.getItemDamage();
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[] {AltisCraft.altisCraftTab};
    }
}
