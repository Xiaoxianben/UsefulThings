package com.xiaoxianben.usefulthings.TileEntity.machine;

import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.recipe.Recipes;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.lang.model.type.NullType;

public class TEMachineLingQIGatherer extends TEMachineBase implements IFluidTank, IFluidHandler {

    public Recipes<NullType, FluidStack> recipe = RecipesList.lingQiGatherer;

    public FluidTank outputFluidTank = new FluidTank(10 * 1000000);


    public TEMachineLingQIGatherer() {
        super();
        this.outputFluidTank.setCanFill(false);
    }


    @Override
    public void updateState() {
        this.isActive = false;
        if (this.runMachine(true) > 0) {
            this.isActive = true;
            this.runMachine(false);
        }
    }

    /**
     * 返回接受的数量
     */
    protected int runMachine(boolean simulate) {
        int acceptNumber = 0;
        int runNum = 0;

        do {
            acceptNumber += this.outputFluidTank.fillInternal(recipe.getOutput(0).copy(), !simulate);
            if (!simulate && runNum > 0) {
                this.ModifyEnergy(-2);
            } else if (simulate) break;
            ++runNum;
        } while (this.getFluidAmount() < this.getCapacity() && this.getEnergyStored() >= 2 && this.itemComponentHandler.getComponent(ModItems.component_machine) > 0);

        return acceptNumber;
    }

    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }

        return super.getCapability(capability, facing);
    }


    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBT.setTag("fluidTank", outputFluidTank.writeToNBT(new NBTTagCompound()));

        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.outputFluidTank.readFromNBT(compound.getCompoundTag("fluidTank"));
    }


    // IFluidHandler && IFluidTank
    @Nullable
    @Override
    public FluidStack getFluid() {
        return this.outputFluidTank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return this.outputFluidTank.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return this.outputFluidTank.getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return this.outputFluidTank.getInfo();
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return this.outputFluidTank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return this.outputFluidTank.fill(resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return this.outputFluidTank.drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return this.outputFluidTank.drain(maxDrain, doDrain);
    }

}
