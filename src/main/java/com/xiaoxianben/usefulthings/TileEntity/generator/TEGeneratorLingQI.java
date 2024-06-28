package com.xiaoxianben.usefulthings.TileEntity.generator;

import com.xiaoxianben.usefulthings.TileEntity.itemStackHandler.ItemStackHandlerInput;
import com.xiaoxianben.usefulthings.fluids.FluidTankBase;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.recipe.ItemOrFluid;
import com.xiaoxianben.usefulthings.recipe.Recipes;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TEGeneratorLingQI extends TEGeneratorBase {

    public Recipes<ItemOrFluid, Integer> recipe = RecipesList.lingQiGenerator;

    public FluidTank LingQiFluidTank = new FluidTankBase(1000 * 1000) {
        public boolean canFillFluidType(FluidStack fluid) {
            return FluidRegistry.getFluid("lingqi_fluid") == fluid.getFluid();
        }
    };
    public FluidTank LingQiTank = new FluidTankBase(1000 * 1000) {
        public boolean canFillFluidType(FluidStack fluid) {
            return FluidRegistry.getFluid("lingqi") == fluid.getFluid();
        }
    };
    public ItemStackHandlerInput inputItemHandler = new ItemStackHandlerInput(1, ModItems.LingQi_ingot);


    public TEGeneratorLingQI() {
        super();
        this.LingQiFluidTank.setCanDrain(false);
        this.LingQiTank.setCanDrain(false);
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

        List<ItemOrFluid> inputFluidTypes = this.getNextInputType();

        for (ItemOrFluid inputType : inputFluidTypes) {
            do {

                acceptNumber += this.drainInput(inputType, simulate);

                if (!simulate && acceptNumber > 0) {
                    this.ModifyEnergy(-this.recipe.getEnergyDeplete(inputType));
                    this.ModifyEnergy(recipe.getOutput(inputType));
                    if (this.energy > this.energyCapacity) this.energy = this.energyCapacity;
                } else {
                    break;
                }
            } while (this.hasValidInput(this.recipe.getInputs().indexOf(inputType)) && this.energy >= this.recipe.getEnergyDeplete(inputType) && this.itemComponentHandler.getComponent(ModItems.component_machine) > 0);
        }
        return acceptNumber;
    }

    // 辅助方法简化逻辑判断和操作
    private boolean hasValidInput(int i) {
        switch (i) {
            case 2:
                return !this.inputItemHandler.getStackInSlot(0).isEmpty();
            case 1:
                return LingQiFluidTank.getFluidAmount() > 0;
            case 0:
                return LingQiTank.getFluidAmount() > 0;
            default:
                return false;
        }
    }

    @Nonnull
    private List<ItemOrFluid> getNextInputType() {
        // 优先级：物品 > LingQiFluidTank中的流体 > LingQiTank中的流体
        return Stream.of(
                        recipe.getInputs().stream()
                                .filter(itemOrFluid -> itemOrFluid.isSame(new ItemOrFluid(this.inputItemHandler.getStackInSlot(0))))
                                .findFirst(),
                        recipe.getInputs().stream()
                                .filter(itemOrFluid -> itemOrFluid.isSame(new ItemOrFluid(LingQiFluidTank.getFluid())))
                                .findFirst(),
                        recipe.getInputs().stream()
                                .filter(itemOrFluid -> itemOrFluid.isSame(new ItemOrFluid(LingQiTank.getFluid())))
                                .findFirst()
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private int drainInput(@Nullable ItemOrFluid inputType, boolean simulate) {
        if (inputType == null || this.energy < this.recipe.getEnergyDeplete(inputType)) {
            return 0;
        }
        if (inputType.item != null) {
            return this.inputItemHandler.extractItemPrivate(0, inputType.getCount(), simulate).getCount();
        } else {
            FluidStack drained = inputType.fluid.isFluidEqual(LingQiFluidTank.getFluid())
                    ? LingQiFluidTank.drainInternal(inputType.getCount(), !simulate)
                    : LingQiTank.drainInternal(inputType.getCount(), !simulate);
            return drained == null ? 0 : drained.amount;
        }
    }


    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new FluidHandlerConcatenate(this.LingQiFluidTank, this.LingQiTank));
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new CombinedInvWrapper(this.inputItemHandler, (IItemHandlerModifiable) super.getCapability(capability, facing)));
        }

        return super.getCapability(capability, facing);
    }


    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBT.setTag("LingQiFluidTank", LingQiFluidTank.writeToNBT(new NBTTagCompound()));
        NBT.setTag("LingQiTank", LingQiTank.writeToNBT(new NBTTagCompound()));
        NBT.setTag("outputFluidTank", inputItemHandler.serializeNBT());

        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.LingQiFluidTank.readFromNBT(compound.getCompoundTag("LingQiFluidTank"));
        this.LingQiTank.readFromNBT(compound.getCompoundTag("LingQiTank"));
        inputItemHandler.deserializeNBT(compound.getCompoundTag("outputFluidTank"));

    }

}
