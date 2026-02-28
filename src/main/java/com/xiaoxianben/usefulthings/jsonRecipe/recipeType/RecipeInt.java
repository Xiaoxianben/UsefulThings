package com.xiaoxianben.usefulthings.jsonRecipe.recipeType;

import com.google.gson.JsonObject;

public class RecipeInt implements IRecipeType<Integer> {

    public String name;

    public RecipeInt(String name) {
        this.name = name;
    }

    @Override
    public Class<Integer> getClassType() {
        return Integer.class;
    }

    @Override
    public JsonObject getRecipeJson(Integer integer) {
        JsonObject json = new JsonObject();

        json.addProperty(this.name, integer.toString());

        return json;
    }

    @Override
    public Integer getRecipe(JsonObject json) {
        return Integer.decode(json.get(this.name).getAsString());
    }

    @Override
    public boolean equals(Integer o1, Integer o2) {
        return o1.equals(o2);
    }
}
