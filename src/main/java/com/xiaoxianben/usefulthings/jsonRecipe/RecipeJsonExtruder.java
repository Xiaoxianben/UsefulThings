package com.xiaoxianben.usefulthings.jsonRecipe;

import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.FluidStackList;
import net.minecraft.item.ItemStack;

public class RecipeJsonExtruder extends RecipeJson<ItemStack, FluidStackList> {
    public RecipeJsonExtruder(String name) {
        super(name, RecipeTypes.recipe_itemStack, RecipeTypes.recipe_fluidStackList);
        this.inputKey = "output";
        this.outputKey = "require";
    }
}
