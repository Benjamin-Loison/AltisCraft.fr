package fr.altiscraft.benjaminloison.containers;

import fr.altiscraft.benjaminloison.tileentity.TileEntityBin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBin extends Container
{
    private final TileEntityBin tileBin;

    public ContainerBin(TileEntityBin tile, InventoryPlayer inventory)
    {
        tileBin = tile;
        tile.openInventory();
        for(int i = 0; i < 3; ++i)
            for(int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(tile, j + i * 9, 8 + j * 18, 18 + i * 18)
                {
                    public boolean isItemValid(ItemStack stack)
                    {
                        return true;
                    }
                });
        bindPlayerInventory(inventory);
    }

    private void bindPlayerInventory(InventoryPlayer inventory)
    {
        for(int i = 0; i < 3; ++i)
            for(int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
        for(int i = 0; i < 9; ++i)
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 144));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return tileBin.isUseableByPlayer(player);
    }

    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        tileBin.closeInventory();
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(slotIndex);
        ItemStack itemstack1 = slot.getStack();
        itemstack = itemstack1.copy();
        if(slotIndex < tileBin.getSizeInventory())
        {
            if(!mergeItemStack(itemstack1, tileBin.getSizeInventory(), inventorySlots.size(), true))
                return null;
        }
        else if(!mergeItemStack(itemstack1, 0, tileBin.getSizeInventory(), false))
            return null;
        if(itemstack1.stackSize == 0)
            slot.putStack((ItemStack)null);
        else
            slot.onSlotChanged();
        return itemstack;
    }

    public TileEntityBin getTilePoubelle()
    {
        return tileBin;
    }
}
