package fr.altiscraft.benjaminloison.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class Armor extends ItemArmor
{
    String texture = "", info;

    public Armor(ArmorMaterial material, int type, String texture, String unlocalizedName, String itemTexture)
    {
        super(material, 0, type);
        this.texture = texture;
        setUnlocalizedName(unlocalizedName);
        setTextureName(AltisCraft.MODID + ":" + itemTexture);
        setCreativeTab(AltisCraft.altisCraftTab);
        info = "";
    }

    public Armor(ArmorMaterial material, int type, String texture, String unlocalizedName, String itemTexture, String info)
    {
        super(material, 0, type);
        this.texture = texture;
        setUnlocalizedName(unlocalizedName);
        setTextureName(AltisCraft.MODID + ":" + itemTexture);
        setCreativeTab(AltisCraft.altisCraftTab);
        this.info = info;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(slot == 2)
            return AltisCraft.MODID + ":textures/models/armor/" + texture + " 2.png";
        return AltisCraft.MODID + ":textures/models/armor/" + texture + " 1.png";
    }

    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean boo)
    {
        if(!info.equals(""))
            list.add(I18n.format(info));
    }
}