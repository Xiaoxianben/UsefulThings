package com.xiaoxianben.usefulthings.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IUpdateNBT {
    NBTTagCompound getUpdateNBT();

    void updateNBT(NBTTagCompound nbt);
}
