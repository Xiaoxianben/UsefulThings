package com.xiaoxianben.usefulthings.tileEntity.containerTE;

import com.xiaoxianben.usefulthings.tileEntity.TEBase;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import com.xiaoxianben.usefulthings.util.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ContainerTEBase implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    public TEBase te;


    public abstract boolean hasItemStackHandler();

    public abstract boolean hasFluidTank();

    public abstract boolean hasEnergyStorage();

    @Nullable
    public abstract ItemStackHandlerBase[] getItemStackHandler();

    @Nullable
    public abstract FluidTank[] getFluidTank();

    @Nullable
    public abstract EnergyStorageBase getEnergyStorage();

    public void update() {
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && hasItemStackHandler()) ||
                (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && hasFluidTank()) ||
                (capability == CapabilityEnergy.ENERGY && hasEnergyStorage());
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (!hasCapability(capability, facing)) {
            return null;
        }
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) (new CombinedInvWrapper(getItemStackHandler()));
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) (new FluidHandlerConcatenate(getFluidTank()));
        } else if (capability == CapabilityEnergy.ENERGY) {
            return (T) getEnergyStorage();
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound NBT = new NBTTagCompound();
        if (hasItemStackHandler()) {
            for (int i = 0; i < getItemStackHandler().length; i++) {
                NBT.setTag("itemStackHandler" + i, NBTUtil.getNbtCompoundFromItemStackHandler(getItemStackHandler()[i]));
            }
        }
        if (hasFluidTank()) {
            for (int i = 0; i < getFluidTank().length; i++) {
                NBT.setTag("fluidTank" + i, getFluidTank()[i].writeToNBT(new NBTTagCompound()));
            }
        }
        if (hasEnergyStorage()) {
            NBT.setTag("energyStorage", getEnergyStorage().serializeNBT());
        }
        return NBT;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (hasItemStackHandler()) {
            for (int i = 0; i < getItemStackHandler().length; i++) {
                NBTUtil.getItemStackHandlerFromNbtCompound(nbt.getCompoundTag("itemStackHandler" + i), getItemStackHandler()[i]);
            }
        }
        if (hasFluidTank()) {
            for (int i = 0; i < getFluidTank().length; i++) {
                getFluidTank()[i].readFromNBT(nbt.getCompoundTag("fluidTank" + i));
            }
        }
        if (hasEnergyStorage()) {
            getEnergyStorage().deserializeNBT(nbt.getCompoundTag("energyStorage"));
        }
    }
}
