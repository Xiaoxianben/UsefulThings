package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.blocks.BlockAutoMachine;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.fakePlayer.FakePlayerManager;
import com.xiaoxianben.usefulthings.item.ItemPositionAnchor;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
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

public class ContainerAutoBlockPlacer extends ContainerAutoBase {
    public static final int id = 0;

    private final ItemStackHandlerAutoPlaceBlock handler;

    public ContainerAutoBlockPlacer() {
        handler = new ItemStackHandlerAutoPlaceBlock();
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
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        if (stackInSlot.isEmpty()) {
            return false;
        }
        final BlockPos placePosition = getPlacePosition();
        if (placePosition == null) {
            return false;
        }

        final IBlockState targetState = te.getWorld().getBlockState(placePosition);
        final Block block = targetState.getBlock();
        if(!block.isAir(targetState, te.getWorld(), placePosition) && !block.isReplaceable(te.getWorld(), placePosition)) {
            return false;
        }

        for (int i = 1; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty())
                return true;
        }

        return false;
    }

    public void processFinish() {
        FakePlayer fakePlayer = FakePlayerManager.get((WorldServer) te.getWorld(), id).get();
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
        BlockPos placePosition = getPlacePosition();
        if (placePosition == null) {
            return false;
        }

        IBlockState blockState = te.getWorld().getBlockState(te.getPos());
        EnumFacing value = blockState.getValue(BlockAutoMachine.FACING);

        FakePlayerManager.setPosition(fakePlayer, placePosition, placePosition.offset(value, 2));
        fakePlayer.setActiveHand(EnumHand.MAIN_HAND);
        fakePlayer.setHeldItem(EnumHand.MAIN_HAND, itemStack);
        itemStack.onItemUse(fakePlayer, te.getWorld(), placePosition, EnumHand.MAIN_HAND, EnumFacing.UP, 0.5f, 0.5f, 0.5f);

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


    static class ItemStackHandlerAutoPlaceBlock extends ItemStackHandlerBase {

        public ItemStackHandlerAutoPlaceBlock() {
            super(10);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            final Item item = stack.getItem();
            if (slot == 0) {
                return item instanceof ItemPositionAnchor;
            }

            return item instanceof ItemBlock || item instanceof ItemBlockSpecial;
        }
    }

}
