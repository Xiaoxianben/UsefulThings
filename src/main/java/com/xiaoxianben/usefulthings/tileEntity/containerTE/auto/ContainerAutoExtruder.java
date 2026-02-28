package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.FluidStackList;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoExtruder extends ContainerAutoBase {
    private final ItemStackHandlerAutoExtruder handler;
    private final ItemStack result = ItemStack.EMPTY;
    private final FluidTank tankWater;
    private final FluidTank tankLava;

    public ContainerAutoExtruder() {
        handler = new ItemStackHandlerAutoExtruder();
        tankWater = new FluidTank(5000) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return fluid.equals(new FluidStack(FluidRegistry.WATER, 1));
            }
        };
        tankLava = new FluidTank(5000) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return fluid.equals(new FluidStack(FluidRegistry.LAVA, 1));
            }
        };
    }

    @Override
    public boolean hasItemStackHandler() {
        return true;
    }

    @Override
    public boolean hasFluidTank() {
        return true;
    }

    @Override
    public boolean hasEnergyStorage() {
        return false;
    }

    @Nullable
    @Override
    public ItemStackHandlerBase[] getItemStackHandler() {
        return new ItemStackHandlerBase[]{handler};
    }

    @Nonnull
    @Override
    public FluidTank[] getFluidTank() {
        return new FluidTank[]{tankWater, tankLava};
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    public boolean canStart() {
        if (result.isEmpty()) {
            return false;
        }
        int index = ModJsonRecipes.recipe_extruder.getIndex(result);
        FluidStackList output = ModJsonRecipes.recipe_extruder.getOutput(index);

        for (FluidStack fluidStack : output) {
            boolean hasFluid = false;
            for (FluidTank tank : getFluidTank()) {
                FluidStack fluid = tank.getFluid();
                if (fluid != null && fluid.isFluidEqual(fluidStack) && tank.getFluidAmount() - fluidStack.amount >= 0) {
                    hasFluid = true;
                    break;
                }
            }
            if (!hasFluid) {
                return false;
            }
        }

        return true;
    }


    public void processFinish() {
        handler.insertItemInternal(0, result.copy(), false);
        for (FluidStack fluidStack : ModJsonRecipes.recipe_extruder.getOutput(result)) {
            for (FluidTank tank : getFluidTank()) {
                FluidStack fluid = tank.getFluid();
                if (fluid != null && fluid.isFluidEqual(fluidStack) && tank.getFluidAmount() - fluidStack.amount >= 0) {
                    tank.drainInternal(fluidStack.amount, true);
                }
            }
        }
    }


    static class ItemStackHandlerAutoExtruder extends ItemStackHandlerBase {


        public ItemStackHandlerAutoExtruder() {
            super(1);
        }

        @Override
        public boolean canExtractItem(int slot) {
            return slot == 0;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    }

}
