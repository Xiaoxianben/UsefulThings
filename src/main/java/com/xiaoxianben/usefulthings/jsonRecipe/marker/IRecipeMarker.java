package com.xiaoxianben.usefulthings.jsonRecipe.marker;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.jsonRecipe.RecipeJson;

public interface IRecipeMarker {
    <i, o> RecipeJson<i, o> modifyRecipe(RecipeJson<i, o> recipeJson, JsonObject json);
    <i, o> JsonObject getRecipeJson(RecipeJson<i, o> recipeJson);
    <i, o> RecipeJson<i, o> JsonObjectToRecipe(RecipeJson<i, o> recipeJson, JsonObject json);
}
