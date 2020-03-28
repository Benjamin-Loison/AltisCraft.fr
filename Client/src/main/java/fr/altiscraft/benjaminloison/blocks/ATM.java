package fr.altiscraft.benjaminloison.blocks;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import fr.altiscraft.benjaminloison.tileentity.TileEntityATM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ATM extends Block
{
    public ATM()
    {
        super(Material.ground);
        setBlockName("atm");
        setCreativeTab(AltisCraft.altisCraftTab);
        setBlockTextureName(AltisCraft.MODID + ":ATM");
        setHardness(1);
    }

    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityATM();
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return ClientProxy.tesrRenderId;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        if(stack.getItemDamage() == 0)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if((tile instanceof TileEntityATM))
                ((TileEntityATM)tile).setDirection((byte)(MathHelper.floor_double(living.rotationYaw * 4 / 362.5D) & 0x3));
        }
    }

    public ForgeDirection[] getValidRotations(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) == 0 ? new ForgeDirection[] {ForgeDirection.UP, ForgeDirection.DOWN} : ForgeDirection.VALID_DIRECTIONS;
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int un, float hitX, float hitY, float hitZ)
    {
        return true;
    }
}
