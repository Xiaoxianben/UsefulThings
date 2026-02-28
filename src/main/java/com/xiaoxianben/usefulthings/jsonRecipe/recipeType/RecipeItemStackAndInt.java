package com.xiaoxianben.usefulthings.jsonRecipe.recipeType;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.exception.RecipeException;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackAndInt;

public class RecipeItemStackAndInt implements IRecipeType<ItemStackAndInt> {
    @Override
    public Class<ItemStackAndInt> getClassType() {
        return ItemStackAndInt.class;
    }

    @Override
    public JsonObject getRecipeJson(ItemStackAndInt recipe) throws RecipeException {
        JsonObject json = new JsonObject();
        json.add("item", RecipeTypes.recipe_itemStack.getRecipeJson(recipe.itemStack));
        json.addProperty("int_value", recipe.intValue);

        return json;
    }

    @Override
    public ItemStackAndInt getRecipe(JsonObject json) throws RecipeException {
        return new ItemStackAndInt(
                RecipeTypes.recipe_itemStack.getRecipe(json.getAsJsonObject("item")),
                json.get("int_value").getAsInt()
        );
    }

    @Override
    public boolean equals(ItemStackAndInt o1, ItemStackAndInt o2) {
        return RecipeTypes.recipe_itemStack.equals(o1.itemStack, o2.itemStack) &&
                o1.intValue == o2.intValue;
    }
}
