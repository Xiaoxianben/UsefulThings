package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import com.xiaoxianben.usefulthings.tileEntity.TEAltar;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockAltar extends BlockTEBase {
    public static final AxisAlignedBB AABBBox_altar = new AxisAlignedBB(0, 0, 0, 16, 13, 16);

    public BlockAltar() {
        super("altar", Material.ROCK, ModCreativeTab.creative_tab_main);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEAltar();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) {
            return true;
        }

        ItemStack heldItemMainhand = playerIn.getHeldItemMainhand();
        TEAltar teAltar = (TEAltar) worldIn.getTileEntity(pos);
        if (teAltar == null) {
            return false;
        }
        ItemStackHandlerBase handler = teAltar.containerTEAltar.getHandler();

        if (playerIn.isSneaking() && heldItemMainhand.isEmpty()) {
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.up().getY(), pos.getZ(), handler.getStackInSlot(0).copy()));
            handler.setStackInSlot(0, ItemStack.EMPTY);
            return true;
        }

        if (!handler.getStackInSlot(1).isEmpty()) {
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.up().getY(), pos.getZ(), handler.getStackInSlot(1).copy()));
            handler.setStackInSlot(1, ItemStack.EMPTY);
            return true;
        }

        ItemStack inserted = handler.insertItem(0, heldItemMainhand, false);
        playerIn.setHeldItem(EnumHand.MAIN_HAND, inserted);
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return super.getCollisionBoundingBox(blockState, worldIn, pos);
    }
}
