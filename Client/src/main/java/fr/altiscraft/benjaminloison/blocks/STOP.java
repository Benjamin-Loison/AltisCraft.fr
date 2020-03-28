package fr.altiscraft.benjaminloison.blocks;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import fr.altiscraft.benjaminloison.tileentity.TileEntitySTOP;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class STOP extends Block
{
    public STOP()
    {
        super(Material.ground);
        setBlockName("stop");
        setCreativeTab(AltisCraft.altisCraftTab);
        setBlockTextureName(AltisCraft.MODID + ":STOP");
        setHardness(1);
        setBlockBounds(0.45F, 0, 0.45F, 0.55F, 3, 0.55F);
    }

    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntitySTOP();
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
            if((tile instanceof TileEntitySTOP))
                ((TileEntitySTOP)tile).setDirection((byte)(MathHelper.floor_double(living.rotationYaw * 4 / 362.5) & 0x3));
        }
    }

    public ForgeDirection[] getValidRotations(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) == 0 ? new ForgeDirection[] {ForgeDirection.UP, ForgeDirection.DOWN} : ForgeDirection.VALID_DIRECTIONS;
    }
}