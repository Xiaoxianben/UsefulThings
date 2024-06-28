package com.xiaoxianben.usefulthings.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class TEInfiniteWater extends TEBase implements IFluidTank, IFluidHandler {

    public TEInfiniteWater() {
        super();
    }


    @Override
    public void updateState() {
        for (int i = 0; i < EnumFacing.VALUES.length; i++) {

            EnumFacing facing = EnumFacing.VALUES[i];
            EnumFacing oppositeFacing = EnumFacing.VALUES[i].getOpposite();

            TileEntity adjacentTileEntity = this.getWorld().getTileEntity(this.getPos().offset(facing));

            boolean hasFluidHandlerCapability = adjacentTileEntity != null && adjacentTileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, oppositeFacing);

            if (hasFluidHandlerCapability) {
                Objects.requireNonNull(adjacentTileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, oppositeFacing)).fill(this.getFluid(), true);
            }
        }

    }


    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    // TODO 实现IFluidTank接口 和 IFluidHandler接口
    @Nullable
    @Override
    public FluidStack getFluid() {
        return new FluidStack(FluidRegistry.getFluid("water"), Integer.MAX_VALUE);
    }

    @Override
    public int getFluidAmount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return this.drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return new FluidStack(FluidRegistry.getFluid("water"), maxDrain);
    }

    @Override
    public NBTTagCompound getUpdateNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void updateNBT(NBTTagCompound nbt) {
    }
}
