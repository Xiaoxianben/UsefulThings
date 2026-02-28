package com.xiaoxianben.usefulthings.blocks.type;

import com.xiaoxianben.usefulthings.api.IHasItemNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum EnumAutoMachineLevel {
    Abnormal(1, "abnormal"),
    AttachedLing(2, "attachedLing"),
    Super(3, "super"),
    Truth(4, "truth")
    ;

    public final int level;
    public final String levelName;

    EnumAutoMachineLevel(int i, String s) {
        this.level = i;
        this.levelName = s;
    }

    public EnumAutoMachineLevel getNextLevel() {
        return (this.level == EnumAutoMachineLevel.values().length) ? null : EnumAutoMachineLevel.values()[this.level];

    }

    public static final String Key_NBT = "level";

    public static EnumAutoMachineLevel getLevel(ItemStack stack) {
        NBTTagCompound subCompound = stack.getSubCompound(IHasItemNBT.Key);
        if (subCompound != null) {
            return EnumAutoMachineLevel.values()[subCompound.getInteger(Key_NBT)];
        }
        return Abnormal;
    }
}
