package com.xiaoxianben.usefulthings.tileEntity.energyStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageBase implements IEnergyStorage, INBTSerializable<NBTTagCompound> {
    protected int energy = 0;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorageBase() {
        this(0);
    }

    public EnergyStorageBase(int capacity) {
        this(capacity, capacity);
    }

    public EnergyStorageBase(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public EnergyStorageBase(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int modifyEnergyTrue = Math.min(Math.min(maxReceive, this.maxReceive), capacity - energy);
        if (!simulate) energy += modifyEnergyTrue;
        return modifyEnergyTrue;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int modifyEnergyTrue = Math.min(Math.min(maxExtract, this.maxExtract), energy);
        if (!simulate) energy -= modifyEnergyTrue;
        return modifyEnergyTrue;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }

    /**
     * 修改存储的能量，危险操作，仅建议明确可以使用后，再使用。
     *
     * @param modifyEnergy 要修改的能量。正为接受，负为消耗
     * @return 实际的修改能量量
     */
    public int modifyEnergyStored(int modifyEnergy) {
        return modifyEnergy >= 0 ? this.receiveEnergy(modifyEnergy, false) : this.extractEnergy(-modifyEnergy, false);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("energy", energy);
        nbt.setInteger("capacity", capacity);
        nbt.setInteger("maxReceive", maxReceive);
        nbt.setInteger("maxExtract", maxExtract);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        energy = nbt.getInteger("energy");
        capacity = nbt.getInteger("capacity");
        maxReceive = nbt.getInteger("maxReceive");
        maxExtract = nbt.getInteger("maxExtract");
    }
}
