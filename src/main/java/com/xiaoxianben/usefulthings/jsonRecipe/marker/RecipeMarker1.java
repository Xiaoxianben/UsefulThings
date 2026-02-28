package com.xiaoxianben.usefulthings.jsonRecipe.marker;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.exception.RecipeException;
import com.xiaoxianben.usefulthings.jsonRecipe.RecipeJson;

import javax.lang.model.type.NullType;
import java.util.Map;

public class RecipeMarker1 implements IRecipeMarker {
    public <i, o> RecipeJson<i, o> modifyRecipe(RecipeJson<i, o> recipeJson, JsonObject json) {
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            final int index = recipeJson.ids.indexOf(entry.getKey());
            final JsonObject value = entry.getValue().getAsJsonObject();

            try {
                if (index == -1) {
                    recipeJson.addRecipe(entry.getKey(),
                            recipeJson.inputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.inputKey)),
                            recipeJson.outputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.outputKey)));
                    continue;
                }

                if (value.has("enable") && !value.get("enable").getAsBoolean()) {
                    recipeJson.ids.remove(index);
                    recipeJson.inputs.remove(index);
                    recipeJson.outputs.remove(index);
                    continue;
                }

                i input = recipeJson.inputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.inputKey));
                o output = recipeJson.outputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.outputKey));

                recipeJson.inputs.set(index, input);
                recipeJson.outputs.set(index, output);
            } catch (Exception e) {
                ConfigLoader.logger().error("Recipe: {}, failed loading file. id: {}", recipeJson.name, entry.getKey());
                ConfigLoader.logger().error(e);
            }
        }
        return recipeJson;
    }
    public <i, o> JsonObject getRecipeJson(RecipeJson<i, o> recipeJson) {

        JsonObject json = new JsonObject();

        for (int i = 0; i < recipeJson.size(); i++) {
            JsonObject recipe = new JsonObject();

            try {
                if (recipeJson.inputRecipeType.getClassType() != NullType.class) {
                    recipe.add(recipeJson.inputKey, recipeJson.inputRecipeType.getRecipeJson(recipeJson.getInput(i)));
                }
                if (recipeJson.outputRecipeType.getClassType() != NullType.class) {
                    recipe.add(recipeJson.outputKey, recipeJson.outputRecipeType.getRecipeJson(recipeJson.getOutput(i)));
                }
            } catch (RecipeException e) {
                ConfigLoader.logger().error("Recipe: {}, failed get json. id: {}", recipeJson.name, i);
                ConfigLoader.logger().error(e);
                continue;
            }

            json.add(recipeJson.ids.get(i), recipe);
        }

        json.addProperty("recipe_marker", EnumRecipeMarker.READER1.index);

        return json;
    }
    public <i, o> RecipeJson<i, o> JsonObjectToRecipe(RecipeJson<i, o> recipeJson, JsonObject json) {
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            final JsonObject value = entry.getValue().getAsJsonObject();

            try {
                i input = recipeJson.inputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.inputKey));
                o output = recipeJson.outputRecipeType.getRecipe(value.getAsJsonObject(recipeJson.outputKey));

                recipeJson.ids.add(entry.getKey());
                recipeJson.inputs.add(input);
                recipeJson.outputs.add(output);
            } catch (Exception e) {
                ConfigLoader.logger().error("Recipe: {}, failed loading file. id: {}", recipeJson.name, entry.getKey());
                ConfigLoader.logger().error(e);
            }
        }

        return recipeJson;
    }

}
