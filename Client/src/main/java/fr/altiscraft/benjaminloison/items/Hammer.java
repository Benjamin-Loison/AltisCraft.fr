package fr.altiscraft.benjaminloison.items;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.entity.EEP;
import fr.altiscraft.benjaminloison.entity.EntityCustomizableNPC;
import fr.altiscraft.benjaminloison.gui.GuiNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Hammer extends Item
{
    public Hammer()
    {
        setUnlocalizedName("hammer");
        setTextureName(AltisCraft.MODID + ":Hammer");
        setCreativeTab(AltisCraft.altisCraftTab);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        Entity target = Minecraft.getMinecraft().objectMouseOver.entityHit;
        if(target instanceof EntityCustomizableNPC)
            if(EEP.get(player).staff)
                Minecraft.getMinecraft().displayGuiScreen(new GuiNPC((EntityCustomizableNPC)target));
            else
                player.addChatComponentMessage(new ChatComponentText(I18n.format("you.are.not.staff")));
        return itemstack;
    }
}