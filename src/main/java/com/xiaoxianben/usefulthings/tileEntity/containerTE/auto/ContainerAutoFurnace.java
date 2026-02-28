package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoFurnace extends ContainerAutoBase {

    private final ItemStackHandlerAutoFurnace handler;

    public ContainerAutoFurnace() {
        handler = new ItemStackHandlerAutoFurnace();
    }

    @Override
    public boolean hasItemStackHandler() {
        return true;
    }

    @Override
    public boolean hasFluidTank() {
        return false;
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
        return null;
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    @Override
    public boolean canStart() {
        ItemStack result = handler.getResult();
        if (result.isEmpty() || !handler.insertItemInternal(1, result, true).isEmpty()) return false;

        ItemStack recipeInput = handler.getRecipeInput();
        ItemStack output = handler.getOutput();
        return handler.extractItemInternal(0, recipeInput.getCount(), true).getCount() == recipeInput.getCount() &&
                output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    @Override
    public void processFinish() {
        ItemStack recipeInput = handler.getRecipeInput();
        ItemStack result = handler.getResult();
        handler.insertItemInternal(1, result, false);
        handler.extractItemInternal(0, recipeInput.getCount(), false);
    }


    static class ItemStackHandlerAutoFurnace extends ItemStackHandlerBase {
        private ItemStack recipeInput = ItemStack.EMPTY;
        private ItemStack recipeOutput = ItemStack.EMPTY;

        public ItemStackHandlerAutoFurnace() {
            super(2);
        }

        @Override
        public boolean canExtractItem(int slot) {
            return slot == 1;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot == 0;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);

            if (slot == 0) {
                int index = ModJsonRecipes.recipe_furnace.getIndex(getInput());
                if (index != -1) {
                    recipeInput = ModJsonRecipes.recipe_furnace.getInput(index);
                    recipeOutput = ModJsonRecipes.recipe_furnace.getOutput(index);
                } else {
                    recipeInput = ItemStack.EMPTY;
                    recipeOutput = ItemStack.EMPTY;
                }
            }
        }

        public ItemStack getResult() {
            return recipeOutput.copy();
        }

        public ItemStack getRecipeInput() {
            return recipeInput.copy();
        }

        public ItemStack getInput() {
            return getStackInSlot(0);
        }

        public ItemStack getOutput() {
            return getStackInSlot(1);
        }
    }

}