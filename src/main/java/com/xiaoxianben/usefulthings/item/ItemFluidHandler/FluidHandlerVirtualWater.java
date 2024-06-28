package com.xiaoxianben.usefulthings.item.ItemFluidHandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

public class FluidHandlerVirtualWater extends FluidHandlerItemStack {
    /**
     * @param container The container itemStack, data is stored on it directly as NBT.
     * @param capacity  The maximum energyCapacity of this fluid outputFluidTank.
     */
    public FluidHandlerVirtualWater(@Nonnull ItemStack container, int capacity) {
        super(container, capacity);
        this.setFluid(new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE));
    }

    @Nonnull
    @Override
    public FluidStack getFluid() {
        return new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }

        return new FluidStack(this.getFluid(), maxDrain);
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return false;
    }

}
