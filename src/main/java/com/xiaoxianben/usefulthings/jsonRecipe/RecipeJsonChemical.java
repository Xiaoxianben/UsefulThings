package com.xiaoxianben.usefulthings.jsonRecipe;

import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemAndFluid;

public class RecipeJsonChemical extends RecipeJson<ItemAndFluid, ItemAndFluid> {

    public RecipeJsonChemical() {
        super("chemical", RecipeTypes.recipe_itemAndFluid, RecipeTypes.recipe_itemAndFluid);
    }
}
