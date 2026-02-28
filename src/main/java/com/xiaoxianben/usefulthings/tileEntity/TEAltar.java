package com.xiaoxianben.usefulthings.tileEntity;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.tileEntity.containerTE.ContainerAltar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TEAltar extends TEBase {
    public ContainerAltar containerTEAltar;
    public int timeNeed = -1;
    public int timeMax = -1;
    public int upLevel = 0;


    public TEAltar() {
        setStateOff();
        containerTEAltar = new ContainerAltar();
    }


    @Override
    public void updateInSever() {
        ItemStack stackInSlot = this.containerTEAltar.getHandler().getStackInSlot(0);
        int index = ModJsonRecipes.recipe_abnormalCraft.getIndex(stackInSlot);

        if (index == -1) {
            if (isStateOn()) {
                setStateOff();
                sendPacketToClientFromSever();
            }
            return;
        }

        if (!isStateOn()) {
            setStateOn();
        }

        if (timeNeed > 0) {
            timeNeed -= (1 + upLevel);
            if (timeNeed < 0) {
                timeNeed = 0;
            }
        }

        if (timeNeed == 0) {
            this.containerTEAltar.processFinish();
            setStateOff();
        }
        sendPacketToClientFromSever();
    }

    @Override
    public void updateInClient() {
    }


    @Nonnull
    @Override
    public NBTTagCompound getNetworkNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void handlerNetworkNBT(NBTTagCompound nbt) {
    }

    @Nonnull
    @Override
    public NBTTagCompound getCapabilityNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        nbtTagCompound.setTag("containerTEAltar", containerTEAltar.serializeNBT());
        nbtTagCompound.setIntArray("time", new int[]{timeNeed, timeMax});

        return nbtTagCompound;
    }

    @Override
    public void handlerCapabilityNBT(NBTTagCompound nbt) {
        containerTEAltar.deserializeNBT(nbt.getCompoundTag("containerTEAltar"));

        final int[] intArray = nbt.getIntArray("time");
        timeNeed = intArray[0];
        timeMax = intArray[1];
    }

    @Nonnull
    @Override
    public NBTTagCompound getDefaultNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void handlerDefaultNBT(NBTTagCompound nbt) {

    }


    public int getTimeMaxNew() {
        return (world.rand.nextInt(40) + 20) * 200;
    }

    public void setStateOn() {
        this.timeMax = getTimeMaxNew();
        this.timeNeed = timeMax;
    }

    public void setStateOff() {
        this.timeMax = -1;
        this.timeNeed = -1;
    }

    public boolean isStateOn() {
        return timeMax != -1;
    }
}
