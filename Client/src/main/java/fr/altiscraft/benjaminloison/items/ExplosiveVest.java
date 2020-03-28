package fr.altiscraft.benjaminloison.items;

import java.util.List;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.model.ModelExplosiveVest;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ExplosiveVest extends ItemArmor
{
    public ExplosiveVest()
    {
        super(AltisCraft.vest, 0, 1);
        setUnlocalizedName("explosive_vest");
        setTextureName(AltisCraft.MODID + ":Explosive Vest");
        setCreativeTab(AltisCraft.altisCraftTab);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(stack.getItem().equals(AltisCraft.explosiveVest))
            return AltisCraft.MODID + ":textures/models/armor/Explosive Vest.png";
        return "";
    }

    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean boo)
    {
        list.add(I18n.format("rebel"));
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityliving, ItemStack stack, int armor)
    {
        return new ModelExplosiveVest();
    }
}