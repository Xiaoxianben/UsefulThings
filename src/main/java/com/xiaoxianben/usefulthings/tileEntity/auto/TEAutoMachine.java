package com.xiaoxianben.usefulthings.tileEntity.auto;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.api.IHasItemNBT;
import com.xiaoxianben.usefulthings.blocks.type.EnumAutoMachineLevel;
import com.xiaoxianben.usefulthings.blocks.type.EnumTypeMachineCommon;
import com.xiaoxianben.usefulthings.tileEntity.TEBase;
import com.xiaoxianben.usefulthings.tileEntity.containerTE.auto.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEAutoMachine extends TEBase implements IHasItemNBT {
    public EnumAutoMachineLevel machineLevel = EnumAutoMachineLevel.Abnormal;
    private ContainerAutoBase container = null;
    public int progressRemaining = -1;
    public int progressMax = -1;

    public EnumTypeMachineCommon typeCraft = null;

    public TEAutoMachine() {

    }

    @Nonnull
    @Override
    public NBTTagCompound getDefaultNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (this.typeCraft == null) {
            nbt.setInteger("type", -1);
        } else {
            nbt.setInteger("type", this.typeCraft.id);
        }
        return nbt;
    }

    @Override
    public void handlerDefaultNBT(NBTTagCompound nbt) {
        this.typeCraft = EnumTypeMachineCommon.getType(nbt.getInteger("type"));
        if (this.typeCraft != null) {
            updateContainer(this.typeCraft);
        }
    }

    @Override
    public void updateInSever() {
        if (container == null) {
            return;
        }

        container.update();

        if (!container.canStart()) {
            progressRemaining = -1;
            progressMax = -1;
            container.processOff();
            return;
        }

        if (this.progressMax == -1) {
            progressMax = 100;
            progressRemaining = progressMax;
            container.processStart();
        }

        progressRemaining -= UsefulThings.randomer.nextInt(30) + 20; // 20-50

        if (progressRemaining <= 0) {
            container.processFinish();
            progressRemaining = -1;
            progressMax = -1;
        }

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
        NBTTagCompound nbt = new NBTTagCompound();
        if (container != null) {
            nbt.setTag("container", container.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void handlerCapabilityNBT(NBTTagCompound nbt) {
        if (container != null) {
            container.deserializeNBT(nbt.getCompoundTag("container"));
        }
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (container != null) {
            return container.getCapability(capability, facing);
        }
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return container != null && container.hasCapability(capability, facing);
    }

    public void updateContainer(EnumTypeMachineCommon typeCraft) {
        switch (typeCraft) {
            case AutoCraft:
                container = new ContainerAutoCraft();
                break;
            case AutoMetallurgy:
                container = new ContainerAutoMetallurgy();
                break;
            case AutoFurnace:
                container = new ContainerAutoFurnace();
                break;
            case AutoPulverizer:
                container = new ContainerAutoPulverizer();
                break;
            case AutoExtruder:
                container = new ContainerAutoExtruder();
                break;
            case AutoCrucible:
                container = new ContainerAutoCrucible();
                break;
            case AutoCharger:
                container = new ContainerAutoCharger();
                break;
            case AutoBrokenMiner:
                container = new ContainerAutoBrokenMiner();
                break;
            case AutoDecompress:
                container = new ContainerAutoDecompress();
                break;
            case AutoCompress:
                container = new ContainerAutoCompress();
                break;
            case AutoChemical:
                container = new ContainerAutoChemical();
                break;
            case AutoElectrolysis:
                container = new ContainerAutoElectrolysis();
                break;
            case AutoWashes:
                container = new ContainerAutoWashes();
                break;
            case AutoBlockPlacer:
                container = new ContainerAutoBlockPlacer();
                break;
            case AutoBlockClicker:
                container = new ContainerAutoBlockClicker();
                break;
            case AutoInfusion:
                container = new ContainerAutoInfusion();
                break;
            default:
                container = null;
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound getItemNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger(EnumAutoMachineLevel.Key_NBT, machineLevel.level);
        return nbtTagCompound;
    }

    @Override
    public void HandleItemNBT(@Nonnull NBTTagCompound nbt) {
        machineLevel = EnumAutoMachineLevel.values()[nbt.getInteger(EnumAutoMachineLevel.Key_NBT)];
    }

}
