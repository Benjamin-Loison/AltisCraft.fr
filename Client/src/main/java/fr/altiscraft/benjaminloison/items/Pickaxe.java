package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.item.ItemPickaxe;

public class Pickaxe extends ItemPickaxe
{
    public Pickaxe()
    {
        super(AltisCraft.tools);
        setUnlocalizedName("pickaxe");
        setTextureName(AltisCraft.MODID + ":Pickaxe");
        setCreativeTab(AltisCraft.altisCraftTab);
    }
}