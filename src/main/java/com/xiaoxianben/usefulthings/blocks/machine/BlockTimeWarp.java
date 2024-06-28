package com.xiaoxianben.usefulthings.blocks.machine;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineTimeWarp;
import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.gui.GUIHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTimeWarp extends BlockMachineBase {
    public BlockTimeWarp() {
        super("timewarp");
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (!super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)) {
                // 打开GUI
                int ID = GUIHandler.GUI_timeWarp;
                playerIn.openGui(UsefulThings.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TEMachineTimeWarp) {
            // 检查红石信号
            ((TEMachineTimeWarp) tileentity).isActive = worldIn.isBlockIndirectlyGettingPowered(pos) <= 0;
        }
    }


    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEMachineTimeWarp();
    }
}
