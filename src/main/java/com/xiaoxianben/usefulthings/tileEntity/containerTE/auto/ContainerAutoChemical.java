package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemAndFluid;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoChemical extends ContainerAutoBase {
    private final ItemStackHandlerAutoChemical handler;
    private final FluidTank tankInput;
    private final FluidTank tankOutput;

    public ContainerAutoChemical() {
        handler = new ItemStackHandlerAutoChemical();

        tankInput = new FluidTank(15000);
        tankInput.setCanDrain(false);
        tankInput.setCanFill(true);

        tankOutput = new FluidTank(15000);
        tankOutput.setCanDrain(true);
        tankOutput.setCanFill(false);
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

    @Nullable
    @Override
    public FluidTank[] getFluidTank() {
        return new FluidTank[]{tankInput, tankOutput};
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    public boolean canStart() {
        FluidStack fluid = tankInput.getFluid();
        if(fluid == null) {
            return false;
        }

        ItemAndFluid content = new ItemAndFluid(handler.getItemInput(), fluid);

        // 检查是否有有效的配方
        int index = ModJsonRecipes.recipe_chemical.getIndex(content);
        if(index == -1) {
            return false;
        }

        // 检查输出槽位是否有足够空间
        ItemAndFluid input = ModJsonRecipes.recipe_chemical.getInput(index);
        if (handler.extractItemInternal(0, input.itemStack1.getCount(), true).getCount() != input.itemStack1.getCount()) {
            return false;
        }
        if (input.fluidStack1.amount > tankInput.getFluid().amount) {
            return false;
        }

        // 检查输入物品是否有效
        ItemAndFluid output = ModJsonRecipes.recipe_chemical.getOutput(index);
        boolean b = handler.insertItemInternal(1, output.itemStack1.copy(), true).getCount() == 0;
        FluidStack tankOutputFluid = tankOutput.getFluid();

        if (tankOutputFluid == null) {
            return b;
        }
        return b && tankOutputFluid.amount + output.fluidStack1.amount <= tankOutput.getCapacity();
    }


    public void processFinish() {
        FluidStack fluid = tankInput.getFluid();
        ItemAndFluid content = new ItemAndFluid(handler.getItemInput(), fluid);
        int index = ModJsonRecipes.recipe_chemical.getIndex(content);

        if(index != -1) {
            ItemAndFluid input = ModJsonRecipes.recipe_chemical.getInput(index);
            handler.extractItemInternal(0, input.itemStack1.getCount(), false);
            ItemAndFluid output = ModJsonRecipes.recipe_chemical.getOutput(index);
            handler.insertItemInternal(1, output.itemStack1.copy(), false);

            tankInput.drainInternal(input.fluidStack1.amount, true);
            tankOutput.fillInternal(output.fluidStack1.copy(), true);
        }
    }
    

    static class ItemStackHandlerAutoChemical extends ItemStackHandlerBase {

        public ItemStackHandlerAutoChemical() {
            super(2);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot == 0;
        }

        @Override
        public boolean canExtractItem(int slot) {
            return slot == 1;
        }

        public ItemStack getItemInput() {
            return getStackInSlot(0);
        }

        public ItemStack getItemOutput() {
            return getStackInSlot(1);
        }
    }

}
