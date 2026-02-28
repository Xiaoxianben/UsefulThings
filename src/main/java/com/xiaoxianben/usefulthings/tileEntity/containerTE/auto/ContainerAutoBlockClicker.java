package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.blocks.BlockAutoMachine;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.fakePlayer.FakePlayerManager;
import com.xiaoxianben.usefulthings.item.ItemPositionAnchor;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoBlockClicker extends ContainerAutoBase {
    public static final int id = 1;

    private final ItemStackHandlerAutoBreakBlock handler;

    public ContainerAutoBlockClicker() {
        handler = new ItemStackHandlerAutoBreakBlock();
    }

    @Override
    public boolean hasItemStackHandler() {
        return true;
    }

    @Override
    public boolean hasFluidTank() {
        return false;
    }

    @Override
    public boolean hasEnergyStorage() {
        return false;
    }

    @Nullable
    @Override
    public ItemStackHandlerBase[] getItemStackHandler() {
        return new ItemStackHandlerBase[]{handler};
    }

    @Nullable
    @Override
    public FluidTank[] getFluidTank() {
        return null;
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    public boolean canStart() {
        final BlockPos placePosition = getPlacePosition();
        if (placePosition == null) {
            return false;
        }

        final IBlockState targetState = te.getWorld().getBlockState(placePosition);
        final Block block = targetState.getBlock();

        return !block.isAir(targetState, te.getWorld(), placePosition);
    }

    public void processFinish() {
        final FakePlayer fakePlayer = FakePlayerManager.get((WorldServer) te.getWorld(), id).get();
        if (fakePlayer == null) {
            ConfigLoader.logger().error("因为异常原因，假玩家创建失败，无法放置方块。Pos: {}, class: {}", te.getPos().toString(), te);
            ConfigLoader.logger().error("Due to an abnormal reason, the fake player failed to create and the block could not be placed. Pos: {}, class: {}", te.getPos().toString(), te);
            return ;
        }

        for (int i = 1; i < handler.getSlots(); i++) {
            ItemStack stackInSlot = handler.getStackInSlot(i).copy();
            if (!stackInSlot.isEmpty()) {
                boolean b = placeBlock(stackInSlot, fakePlayer);
                if (b) {
                    handler.setStackInSlot(i, fakePlayer.getHeldItemMainhand());
                    return;
                }
            }
        }
    }

    public boolean placeBlock(ItemStack itemStack, FakePlayer fakePlayer) {
        final BlockPos placePosition = getPlacePosition();
        if (placePosition == null) {
            return false;
        }

        IBlockState blockState = te.getWorld().getBlockState(te.getPos());
        EnumFacing value = blockState.getValue(BlockAutoMachine.FACING);

        FakePlayerManager.setPosition(fakePlayer, placePosition, placePosition.offset(value, 2));
        fakePlayer.setActiveHand(EnumHand.MAIN_HAND);
        fakePlayer.setHeldItem(EnumHand.MAIN_HAND, itemStack);
        fakePlayer.interactionManager.onBlockClicked(placePosition, EnumFacing.UP);

        return true;
    }

    @Nullable
    public BlockPos getPlacePosition() {
        NBTTagCompound tagCompound = handler.getStackInSlot(0).getTagCompound();
        if (tagCompound == null) {
            return null;
        }

        if (tagCompound.hasKey(ItemPositionAnchor.key_position)) {
            int[] intArray = tagCompound.getIntArray(ItemPositionAnchor.key_position);
            return new BlockPos(intArray[0], intArray[1], intArray[2]);
        }

        return null;
    }


    static class ItemStackHandlerAutoBreakBlock extends ItemStackHandlerBase {

        public ItemStackHandlerAutoBreakBlock() {
            super(2);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == 0) {
                return stack.getItem() instanceof ItemPositionAnchor;
            }
            return slot == 1;
        }
    }

}
