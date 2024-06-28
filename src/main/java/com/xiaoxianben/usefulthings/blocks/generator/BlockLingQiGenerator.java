package com.xiaoxianben.usefulthings.blocks.generator;

import com.xiaoxianben.usefulthings.TileEntity.generator.TEGeneratorLingQI;
import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.gui.GUIHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockLingQiGenerator extends BlockGeneratorBase {


    public BlockLingQiGenerator() {
        super("lingqi");
    }


    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (!super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)) {
                // 打开GUI
                int ID = GUIHandler.GUI_LingQIGenerator;
                playerIn.openGui(UsefulThings.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEGeneratorLingQI();
    }

}
