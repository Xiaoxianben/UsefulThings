package com.xiaoxianben.usefulthings.jsonRecipe.wapper;

import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class ItemAndFluid {
    public final ItemStack itemStack1;
    public final FluidStack fluidStack1;

    public ItemAndFluid(ItemStack itemStack1,@Nullable FluidStack fluidStack1) {
        this.itemStack1 = itemStack1;
        this.fluidStack1 = fluidStack1;
    }

    public ItemAndFluid(Item item, @Nullable FluidStack fluidStack1) {
        this.itemStack1 = new ItemStack(item);
        this.fluidStack1 = fluidStack1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemAndFluid) {
            ItemAndFluid itemAndFluid = (ItemAndFluid) obj;

            if (!RecipeTypes.recipe_itemStack.equals(itemAndFluid.itemStack1, itemStack1)) {
                return false;
            }

            boolean isSameFluid = false;
            if (itemAndFluid.fluidStack1 == null && fluidStack1 == null) {
                isSameFluid = true;
            } else if (itemAndFluid.fluidStack1 != null && fluidStack1 != null) {
                isSameFluid = RecipeTypes.recipe_fluidStack.equals(itemAndFluid.fluidStack1, fluidStack1);
            }
            return isSameFluid;
        }
        return false;
    }
}
