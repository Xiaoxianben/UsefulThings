package com.xiaoxianben.usefulthings.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;

public class lingQiGathererWrapper implements IRecipeWrapper {
    private final FluidStack output;

    public lingQiGathererWrapper(FluidStack output) {
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setOutput(VanillaTypes.FLUID, this.output);

    }
}