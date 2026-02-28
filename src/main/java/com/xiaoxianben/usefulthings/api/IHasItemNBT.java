package com.xiaoxianben.usefulthings.api;

import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public interface IHasItemNBT {
    String Key = ModInformation.MOD_ID+"_itemNBT";

    @Nonnull
    NBTTagCompound getItemNBT();
    void HandleItemNBT(@Nonnull NBTTagCompound nbt);
}
