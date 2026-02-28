package com.xiaoxianben.usefulthings.jsonRecipe.recipeType;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.exception.RecipeException;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
public interface IRecipeType<T> {

    Class<T> getClassType();

    JsonObject getRecipeJson(T recipe) throws RecipeException;

    T getRecipe(JsonObject json) throws RecipeException;

    @ParametersAreNonnullByDefault
    default boolean equals(T o1, T o2) {
        return o1.equals(o2);
    }
}
