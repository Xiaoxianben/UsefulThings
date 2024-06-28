package com.xiaoxianben.usefulthings.TileEntity.machine;

import com.xiaoxianben.usefulthings.TileEntity.TEEnergyBase;
import com.xiaoxianben.usefulthings.TileEntity.itemStackHandler.ItemComponentHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class TEMachineBase extends TEEnergyBase {

    public ItemComponentHandler itemComponentHandler;


    public ItemComponentHandler getItemComponentHandler() {
        return itemComponentHandler;
    }


    public TEMachineBase() {
        super();
        this.itemComponentHandler = new ItemComponentHandler(1, ItemComponentHandler.machine);
        this.canReceive = true;
        this.canExtract = false;
    }


    // Capability
    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemComponentHandler);
        }

        return super.getCapability(capability, facing);
    }


    // updateNBT
    public NBTTagCompound getUpdateNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("isActive", this.isActive);
        return nbt;
    }

    public void updateNBT(NBTTagCompound nbt) {
        this.isActive = nbt.getBoolean("isActive");
    }

    // NBT
    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBT.setTag("itemComponentHandler", itemComponentHandler.serializeNBT());

        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemComponentHandler.deserializeNBT(compound.getCompoundTag("itemComponentHandler"));
    }

}
