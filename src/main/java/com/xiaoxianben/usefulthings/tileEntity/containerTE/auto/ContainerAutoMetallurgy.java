package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackList;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoMetallurgy extends ContainerAutoBase {
    private final ItemStackHandlerAutoMetallurgy handler;

    public ContainerAutoMetallurgy() {
        handler = new ItemStackHandlerAutoMetallurgy();
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

    public boolean canStart() {
        if (handler.recipe_output.isEmpty()) {
            return false;
        }

        ItemStack inserted = handler.insertItemInternal(3, handler.recipe_output, true);
        if (!inserted.isEmpty()) {
            return false;
        }

        for (ItemStack itemStack : handler.recipe_inputs) {
            boolean isTrueItem = false;

            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack extracted = handler.extractItemInternal(i, itemStack.getCount(), true);
                if (extracted.getCount() == itemStack.getCount()) {
                    isTrueItem = true;
                    break;
                }
            }
            if (!isTrueItem) {
                return false;
            }
        }

        return true;
    }


    public void processFinish() {
        handler.insertItemInternal(3, handler.recipe_output.copy(), false);

        for (ItemStack itemStack : handler.recipe_inputs) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack extracted = handler.extractItemInternal(i, itemStack.getCount(), true);
                if (extracted.getCount() == itemStack.getCount()) {
                    handler.extractItemInternal(i, itemStack.getCount(), false);
                    break;
                }
            }
        }
    }


    static class ItemStackHandlerAutoMetallurgy extends ItemStackHandlerBase {
        public ItemStack recipe_output = ItemStack.EMPTY;
        public ItemStackList recipe_inputs = new ItemStackList();

        public ItemStackHandlerAutoMetallurgy() {
            super(4);
        }

        @Override
        public boolean canExtractItem(int slot) {
            return slot == 3;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot >= 0 && slot < 3;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            if (slot != 3) {
                ItemStackList input = new ItemStackList(getStackInSlot(0), getStackInSlot(1), getStackInSlot(2));
                int i = ModJsonRecipes.recipe_metallurgy.getIndex(input);
                if (i == -1) {
                    recipe_output = ItemStack.EMPTY;
                    recipe_inputs = new ItemStackList();
                    return;
                }
                recipe_output = ModJsonRecipes.recipe_metallurgy.getOutput(i).copy();
                recipe_inputs = ModJsonRecipes.recipe_metallurgy.getInput(i).copy();
            }
        }

    }

}
