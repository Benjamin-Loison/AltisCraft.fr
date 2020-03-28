package fr.altiscraft.benjaminloison.items;

import java.util.ArrayList;
import java.util.List;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Food extends ItemFood
{
    private List<PotionEffect> effects = new ArrayList();
    private List<Double> propabilities = new ArrayList();
    String alpha;

    public Food(String list, String unlocalizedName, String texture, int healAmount, float saturationModifier, int size)
    {
        super(healAmount, saturationModifier, true);
        setUnlocalizedName(unlocalizedName);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setCreativeTab(AltisCraft.altisCraftTab);
        setAlwaysEdible();
        setMaxStackSize(size);
        alpha = list;
    }

    public Food(String unlocalizedName, String texture, int healAmount, float saturationModifier, int size)
    {
        super(healAmount, saturationModifier, true);
        setUnlocalizedName(unlocalizedName);
        setTextureName(AltisCraft.MODID + ":" + texture);
        setCreativeTab(AltisCraft.altisCraftTab);
        setAlwaysEdible();
        setMaxStackSize(size);
        alpha = "";
    }

    public void addInformation(ItemStack item, EntityPlayer entity, List list, boolean bo)
    {
        if(!alpha.equals(""))
            list.add(I18n.format(alpha));
    }

    public Food addPotionEffect(PotionEffect effect, double propability)
    {
        effects.add(effect);
        propabilities.add(Double.valueOf(propability));
        return this;
    }

    public Food addPotionEffect(PotionEffect effect)
    {
        return addPotionEffect(effect, 1);
    }

    public Food removePotionEffect(PotionEffect effect)
    {
        int index = effects.indexOf(effect);
        if(index == -1)
            return this;
        effects.remove(index);
        propabilities.remove(index);
        return this;
    }
}