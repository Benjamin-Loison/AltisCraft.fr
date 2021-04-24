package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.item.ItemSpade;

public class Spade extends ItemSpade
{
    public Spade()
    {
        super(AltisCraft.tools);
        setUnlocalizedName("spade");
        setTextureName(AltisCraft.MODID + ":Spade");
        setCreativeTab(AltisCraft.altisCraftTab);
    }
}