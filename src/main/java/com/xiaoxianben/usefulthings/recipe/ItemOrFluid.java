package com.xiaoxianben.usefulthings.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ItemOrFluid {

    public ItemStack item = null;
    public FluidStack fluid = null;

    public ItemOrFluid(@Nonnull Item item) {
        this(new ItemStack(item, 1));
    }

    public ItemOrFluid(@Nonnull Fluid fluid) {
        this(new FluidStack(fluid, 1));
    }

    public ItemOrFluid(@Nullable ItemStack item) {
        this.item = item;
    }

    public ItemOrFluid(@Nullable FluidStack fluid) {
        this.fluid = fluid;
    }

    public ItemOrFluid(String name, int amount) {
        if (Item.getByNameOrId(name) != null) {
            this.item = new ItemStack(Objects.requireNonNull(Item.getByNameOrId(name)), amount);
        } else if (FluidRegistry.getFluid(name) != null) {
            this.fluid = new FluidStack(FluidRegistry.getFluid(name), amount);
        } else {
            throw new RuntimeException("Item or Fluid not found: " + name);
        }
    }


    public Object get() {
        return item != null ? item : fluid;
    }

    public int getCount() {
        return item != null ? item.getCount() : fluid.amount;
    }

    public boolean isSame(ItemOrFluid other) {
        return item != null && other.item != null ? item.isItemEqual(other.item) : fluid != null && fluid.isFluidEqual(other.fluid);
    }
}
