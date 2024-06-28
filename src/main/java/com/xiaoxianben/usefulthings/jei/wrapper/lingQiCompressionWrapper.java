package com.xiaoxianben.usefulthings.jei.wrapper;

import com.xiaoxianben.usefulthings.recipe.ItemOrFluid;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class lingQiCompressionWrapper implements IRecipeWrapper {
    private final FluidStack input;
    private final ItemOrFluid output;

    public lingQiCompressionWrapper(FluidStack input, ItemOrFluid output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInput(VanillaTypes.FLUID, this.input);

        if (this.output.get() instanceof FluidStack) {
            ingredients.setOutput(VanillaTypes.FLUID, (FluidStack) this.output.get());
        } else {
            ingredients.setOutput(VanillaTypes.ITEM, (ItemStack) this.output.get());
        }
    }
}