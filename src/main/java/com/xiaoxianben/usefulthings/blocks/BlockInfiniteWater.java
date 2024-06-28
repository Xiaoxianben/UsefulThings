package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.TileEntity.TEInfiniteWater;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class BlockInfiniteWater extends BlockBase implements ITileEntityProvider {
    public BlockInfiniteWater() {
        super("block_infinite_water", Material.IRON);
    }


    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        tooltip.add(I18n.format("block.lqtech-block_infinite_water.information"));
    }


    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
//            ItemStack item = playerIn.getHeldItem(hand);
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof IFluidHandler) {
                FluidUtil.interactWithFluidHandler(playerIn, hand, (IFluidHandler) tileEntity);
            }
        }
        return true;
    }

    @ParametersAreNonnullByDefault
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEInfiniteWater();
    }
}
