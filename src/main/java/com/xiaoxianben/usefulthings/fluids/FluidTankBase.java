package com.xiaoxianben.usefulthings.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankBase extends FluidTank {
    public FluidTankBase(int capacity) {
        super(capacity);
    }

    public FluidTank readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.capacity = nbt.getInteger("Capacity");
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt.setInteger("Capacity", this.capacity);
        return nbt;
    }
}
