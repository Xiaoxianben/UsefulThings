package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoCompress extends ContainerAutoBase {
    private final ItemStackHandlerAutoCompress handler;

    public ContainerAutoCompress() {
        handler = new ItemStackHandlerAutoCompress();
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
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        if (stackInSlot.isEmpty()) {
            return false;
        }
        
        int index = ModJsonRecipes.recipe_compress.getIndex(stackInSlot);
        if(index != -1) {
            ItemStack output = ModJsonRecipes.recipe_compress.getOutput(index);
            ItemStack input = ModJsonRecipes.recipe_compress.getInput(index);

            return handler.extractItemInternal(0, input.getCount(), true).getCount() == input.getCount() &&
                    handler.insertItemInternal(1, output.copy(), true).isEmpty();
        }

        return false;
    }


    public void processFinish() {
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        
        int index = ModJsonRecipes.recipe_compress.getIndex(stackInSlot);
        if (index != -1) {
            ItemStack output = ModJsonRecipes.recipe_compress.getOutput(index);
            ItemStack input = ModJsonRecipes.recipe_compress.getInput(index);

            handler.extractItemInternal(0, input.getCount(), false);
            handler.insertItemInternal(1, output.copy(), false);
        }
    }


    static class ItemStackHandlerAutoCompress extends ItemStackHandlerBase {

        public ItemStackHandlerAutoCompress() {
            super(2);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot != 0) {
                return false;
            }
            
            return ModJsonRecipes.recipe_compress.getIndex(stack) != -1;
        }
    }

}
