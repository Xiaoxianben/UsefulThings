package com.xiaoxianben.usefulthings.tileEntity.containerTE;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

public class ContainerAltar extends ContainerTEBase {

    protected final ItemStackHandlerAltar handler;

    public ContainerAltar() {
        handler = new ItemStackHandlerAltar();
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

    @Override
    @Nonnull
    public ItemStackHandlerBase[] getItemStackHandler() {
        return new ItemStackHandlerBase[]{handler};
    }

    @Override
    public FluidTank[] getFluidTank() {
        return null;
    }

    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    public void processFinish() {
        ItemStack stackInSlot = handler.getStackInSlot(0);
        int index = ModJsonRecipes.recipe_abnormalCraft.getIndex(stackInSlot);

        if (index != -1) {
            ItemStack recipe_input = ModJsonRecipes.recipe_abnormalCraft.getInput(index);
            ItemStack recipe_output = ModJsonRecipes.recipe_abnormalCraft.getOutput(index);
            ItemStack inserted = handler.insertItemInternal(1, recipe_output, true);
            ItemStack extracted = handler.extractItemInternal(0, recipe_input.getCount(), true);
            if (inserted.getCount() == recipe_output.getCount() && recipe_input.getCount() == extracted.getCount()) {
                handler.insertItemInternal(1, recipe_output, false);
                handler.extractItemInternal(0, recipe_input.getCount(), false);
            }
        }
    }

    public ItemStackHandlerBase getHandler() {
        return handler;
    }

    public static class ItemStackHandlerAltar extends ItemStackHandlerBase {
        public ItemStackHandlerAltar() {
            super(2);
        }

        @Override
        public boolean canExtractItem(int slot) {
            switch (slot) {
                case 0:
                case 1:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case 0:
                    for (ItemStack input : ModJsonRecipes.recipe_abnormalCraft.inputs) {
                        if (RecipeTypes.recipe_itemStack.equals(input, stack)) {
                            return true;
                        }
                    }
                    return false;
                case 1:
                default:
                    return false;
            }
        }
    }
}
