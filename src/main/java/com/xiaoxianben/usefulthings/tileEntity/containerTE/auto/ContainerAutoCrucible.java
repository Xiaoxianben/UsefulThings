package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoCrucible extends ContainerAutoBase {
    private final ItemStackHandlerAutoCrucible handler;
    private final FluidTank tank ;

    public ContainerAutoCrucible() {
        handler = new ItemStackHandlerAutoCrucible();
        tank = new FluidTank(10000);
        tank.setCanFill(false);
        tank.setCanDrain(true);
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
        return new FluidTank[]{tank};
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    public boolean canStart() {
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        final int index = ModJsonRecipes.recipe_crucible.getIndex(stackInSlot);
        if (index == -1) {
            return false;
        }

        final ItemStack input = ModJsonRecipes.recipe_crucible.getInput(index);
        if (input.getCount() > stackInSlot.getCount()) {
            return false;
        }

        final FluidStack result = ModJsonRecipes.recipe_crucible.getOutput(index);
        if(result == null || result.amount > tank.getCapacity()) {
            return false;
        }

        return !stackInSlot.isEmpty();
    }


    public void processFinish() {
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        final int index = ModJsonRecipes.recipe_crucible.getIndex(stackInSlot);
        final ItemStack input = ModJsonRecipes.recipe_crucible.getInput(index);
        final FluidStack result = ModJsonRecipes.recipe_crucible.getOutput(index);

        tank.fill(result, true);
        handler.extractItemInternal(0, input.getCount(), false);
    }


    static class ItemStackHandlerAutoCrucible extends ItemStackHandlerBase {
        private ItemStack tempInput = ItemStack.EMPTY;

        public ItemStackHandlerAutoCrucible() {
            super(1);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (RecipeTypes.recipe_itemStack.equals(tempInput, stack)) {
                return true;
            }
            if (ModJsonRecipes.recipe_crucible.inputs.stream().anyMatch(input -> RecipeTypes.recipe_itemStack.equals(input, stack))) {
                tempInput = stack;
                return true;
            }
            return false;
        }
    }

}
