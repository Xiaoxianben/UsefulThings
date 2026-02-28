package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoInfusion extends ContainerAutoBase {
    private final ItemStackHandlerAutoInfusion handler;
    private final FluidTank tank ;

    public ContainerAutoInfusion() {
        handler = new ItemStackHandlerAutoInfusion();
        tank = new FluidTank(10000);
        tank.setCanFill(true);
        tank.setCanDrain(false);
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
        return stackInSlot.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    }

    @Override
    public void processFinish() {
        // 获取物品槽中的物品
        final ItemStack stackInSlot = handler.getStackInSlot(0);

        // 获取物品的流体处理能力
        IFluidHandlerItem capability = stackInSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (capability == null) {
            return;
        }

        // 尝试将流体从物品填充到容器中，并返回填充的量
        int filled = capability.fill(tank.getFluid(), true);
        // 从容器中排出相应量的流体
        tank.drainInternal(filled, true);

        // 确定容器全满
        for (IFluidTankProperties tankProperty : capability.getTankProperties()) {
            FluidStack contents = tankProperty.getContents();
            if (contents == null || contents.amount < tankProperty.getCapacity())
                return;
        }

        // 将处理后的物品插入到指定槽位
        ItemStack inserted = handler.insertItemInternal(1, stackInSlot, false);
        // 从原始槽位移除相应数量的物品
        handler.extractItemInternal(0, inserted.getCount(), false);
    }


    static class ItemStackHandlerAutoInfusion extends ItemStackHandlerBase {
        private ItemStack tempInput = ItemStack.EMPTY;

        public ItemStackHandlerAutoInfusion() {
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
