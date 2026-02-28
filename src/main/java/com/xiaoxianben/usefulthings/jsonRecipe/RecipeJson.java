package com.xiaoxianben.usefulthings.jsonRecipe;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.jsonRecipe.marker.EnumRecipeMarker;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.IRecipeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RecipeJson<i, o> {

    public final IRecipeType<i> inputRecipeType;
    public final IRecipeType<o> outputRecipeType;

    public final List<String> ids = new ArrayList<>();
    public final List<i> inputs = new ArrayList<>();
    public final List<o> outputs = new ArrayList<>();

    public final String name;
    public String inputKey = "input";
    public String outputKey = "output";


    public RecipeJson(String name, IRecipeType<i> inputRecipeType, IRecipeType<o> outputRecipeType) {
        this.inputRecipeType = inputRecipeType;
        this.outputRecipeType = outputRecipeType;
        this.name = name;
    }


    /**
     * 你不应该对其返回内容进行修改
     */
    @Nullable
    public o getOutput(i input) {
        int i = getIndex(input);
        return i == -1 ? null : outputs.get(i);
    }

    /**
     * 你不应该对其返回内容进行修改
     */
    public o getOutput(int index) {
        return outputs.get(index);
    }

    public o getOutput(String id) {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equals(id)) {
                return outputs.get(i);
            }
        }
        return null;
    }

    /**
     * 你不应该对其返回内容进行修改
     */
    public i getInput(int index) {
        return inputs.get(index);
    }

    public i getInput(String id) {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equals(id)) {
                return inputs.get(i);
            }
        }
        return null;
    }

    public int getIndex(i input) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputRecipeType.equals(inputs.get(i), input)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return ids.size();
    }

    public RecipeJson<i, o> reset() {
        this.ids.clear();
        this.inputs.clear();
        this.outputs.clear();
        return this;
    }
    
    public RecipeJson<i, o> addRecipe(String id, i input, o output) {
        this.inputs.add(input);
        this.outputs.add(output);
        this.ids.add(id);
        return this;
    }
    
    public RecipeJson<i, o> modifyRecipe(JsonObject json) {
        if (json.has("recipe_marker")) {
            EnumRecipeMarker recipeMarker = EnumRecipeMarker.values()[json.get("recipe_marker").getAsInt()];
            return recipeMarker.marker.modifyRecipe(this, json);
        }
        ConfigLoader.logger().error("Recipe: {}, failed modif json.Because it don't has \"recipe_marker\" key", this.name);
        return this;
    }

    public JsonObject getRecipeJson() {
        EnumRecipeMarker recipeMarker = EnumRecipeMarker.values()[EnumRecipeMarker.values().length - 1];
        return recipeMarker.marker.getRecipeJson(this);
    }

    public RecipeJson<i, o> JsonObjectToRecipe(JsonObject json) {
        if (json.has("recipe_marker")) {
            EnumRecipeMarker recipeMarker = EnumRecipeMarker.values()[json.get("recipe_marker").getAsInt()];
            return recipeMarker.marker.JsonObjectToRecipe(this, json);
        }
        ConfigLoader.logger().error("Recipe: {}, failed modif json.Because it don't has \"recipe_marker\" key", this.name);
        return this;
    }
}
