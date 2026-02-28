package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.init.modRegisterer.RegistererMinecraft;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ContainerAutoWashes extends ContainerAutoBase {
    private final ItemStackHandlerAutoElectrolysis handler;
    private final FluidTank tank;
    public int consumeFluidAmount = 100;

    public ContainerAutoWashes() {
        handler = new ItemStackHandlerAutoElectrolysis();

        tank = new FluidTank(15000) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return Objects.equals(fluid.getFluid().getName(), RegistererMinecraft.washes.getName());
            }
        };
        tank.setCanDrain(false);
        tank.setCanFill(true);
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
        FluidStack fluid = tank.getFluid();
        if(fluid == null || fluid.amount < consumeFluidAmount) {
            return false;
        }

        // 检查是否有有效的配方
        int index = ModJsonRecipes.recipe_washes.getIndex(handler.getItemInput());
        if(index == -1) {
            return false;
        }

        // 检查输出槽位是否有足够空间
        ItemStack input = ModJsonRecipes.recipe_washes.getInput(index);
        if (handler.extractItemInternal(0, input.getCount(), true).getCount() != input.getCount()) {
            return false;
        }

        // 检查输入物品是否有效
        ItemStack output = ModJsonRecipes.recipe_washes.getOutput(index);
        return handler.insertItemInternal(1, output.copy(), true).getCount() == 0;
    }


    public void processFinish() {
        int index = ModJsonRecipes.recipe_washes.getIndex(handler.getItemInput());
        
        if(index != -1) {
            ItemStack input = ModJsonRecipes.recipe_washes.getInput(index);
            handler.extractItemInternal(0, input.getCount(), false);
            ItemStack output = ModJsonRecipes.recipe_washes.getOutput(index);
            handler.insertItemInternal(1, output.copy(), false);

            tank.drainInternal(consumeFluidAmount, true);
        }
    }
    

    static class ItemStackHandlerAutoElectrolysis extends ItemStackHandlerBase {

        public ItemStackHandlerAutoElectrolysis() {
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
