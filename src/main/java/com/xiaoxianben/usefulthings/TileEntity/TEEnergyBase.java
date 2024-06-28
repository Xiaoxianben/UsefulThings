package com.xiaoxianben.usefulthings.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class TEEnergyBase extends TEBase implements IEnergyStorage {

    public boolean isActive = false;
    protected long energy = 0;
    protected long energyCapacity = Integer.MAX_VALUE - 1;
    protected boolean canReceive = true;
    protected boolean canExtract = true;


    // Capability
    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this);
        }

        return super.getCapability(capability, facing);
    }


    // NBT
    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBTTagCompound energyNBT = new NBTTagCompound();
        energyNBT.setLong("energy", this.energy);
        energyNBT.setLong("energyCapacity", this.energyCapacity);
        NBT.setTag("energyStorage", energyNBT);

        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagCompound energyNBT = compound.getCompoundTag("energyStorage");
        this.energy = energyNBT.getLong("energy");
        this.energyCapacity = energyNBT.getLong("energyCapacity");
    }


    // IEnergyStorage
    public void ModifyEnergy(long energy) {
        this.energy += energy;
        if (this.energy < 0) {
            this.energy = 0;
        } else if (this.energy > this.energyCapacity) {
            this.energy = this.energyCapacity;
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receive = this.canReceive ? (int) Math.min(this.energyCapacity - this.energy, maxReceive) : 0;
        if (!simulate) {
            this.energy += receive;
        }
        return receive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extract = this.canExtract ? (int) Math.min(this.energy, maxExtract) : 0;
        if (!simulate) {
            this.energy -= extract;
        }
        return extract;
    }

    @Override
    public int getEnergyStored() {
        return (int) Math.min(this.energy, Integer.MAX_VALUE);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) Math.min(this.energyCapacity, Integer.MAX_VALUE);
    }

    public long getEnergyStoredL() {
        return this.energy;
    }

    public long getMaxEnergyStoredL() {
        return this.energyCapacity;
    }

    @Override
    public boolean canExtract() {
        return this.canExtract;
    }

    @Override
    public boolean canReceive() {
        return this.canReceive;
    }
}
