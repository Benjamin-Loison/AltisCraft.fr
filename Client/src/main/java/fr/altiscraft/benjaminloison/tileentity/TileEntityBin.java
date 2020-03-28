package fr.altiscraft.benjaminloison.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityBin extends TileEntity implements IInventory
{
    private byte direction;
    private ItemStack[] contents = new ItemStack[27];
    private String customName;

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        direction = compound.getByte("Direction");
        if(compound.hasKey("CustomName", Constants.NBT.TAG_STRING))
            customName = compound.getString("CustomName");
        contents = new ItemStack[getSizeInventory()];
        NBTTagList nbtTagList = compound.getTagList("Items", 10);
        for(int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            int j = nbtTagCompound.getByte("Slot") & 255;
            if(j >= 0 && j < contents.length)
                contents[j] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("Direction", direction);
        if(hasCustomInventoryName())
            compound.setString("CustomName", customName);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < contents.length; ++i)
            if(contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        compound.setTag("Items", nbttaglist);
    }

    public byte getDirection()
    {
        return direction;
    }

    public void setDirection(byte direction)
    {
        this.direction = direction;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    @Override
    public int getSizeInventory()
    {
        return contents.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return contents[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount)
    {
        if(contents[slotIndex] != null)
        {
            ItemStack itemstack;
            if(contents[slotIndex].stackSize <= amount)
            {
                itemstack = contents[slotIndex];
                contents[slotIndex] = null;
                markDirty();
                return itemstack;
            }
            else
            {
                itemstack = contents[slotIndex].splitStack(amount);
                if(contents[slotIndex].stackSize == 0)
                    contents[slotIndex] = null;
                markDirty();
                return itemstack;
            }
        }
        else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if(contents[slotIndex] != null)
        {
            ItemStack itemstack = contents[slotIndex];
            contents[slotIndex] = null;
            return itemstack;
        }
        else
            return null;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack stack)
    {
        contents[slotIndex] = stack;
        if(stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();
        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return hasCustomInventoryName() ? customName : "gui.bin";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return customName != null && !customName.isEmpty();
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void closeInventory()
    {}

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack stack)
    {
        return true;
    }

    @Override
    public void openInventory()
    {}
}