package com.xiaoxianben.usefulthings.recipe;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.recipe.recipeType.IRecipeType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public class Recipes<input, output> {

    protected IRecipeType<input> inputRecipeType;
    protected IRecipeType<output> outputRecipeType;

    protected LinkedHashMap<input, output> recipeMap = new LinkedHashMap<>();
    protected List<Integer> energyDepleteList = new ArrayList<>();


    public Recipes(IRecipeType<input> inputRecipeType, IRecipeType<output> outputRecipeType) {
        this.inputRecipeType = inputRecipeType;
        this.outputRecipeType = outputRecipeType;
    }


    public void addRecipe(input input, output output, int energyDeplete) {
        recipeMap.put(input, output);
        this.energyDepleteList.add(energyDeplete);
    }


    public List<input> getInputs() {
        return new ArrayList<>(this.recipeMap.keySet());
    }

    public input getInput(int i) {
        return getInputs().get(i);
    }

    public output getOutput(input input) {
        return this.recipeMap.get(input);
    }

    public output getOutput(int i) {
        return this.recipeMap.get(new ArrayList<>(this.recipeMap.keySet()).get(i));
    }

    public int getEnergyDeplete(input input) {
        return this.getEnergyDeplete(new ArrayList<>(this.recipeMap.keySet()).indexOf(input));
    }

    public int getEnergyDeplete(int i) {
        return this.energyDepleteList.get(i);
    }

    public List<Integer> getEnergyDepletes() {
        return this.energyDepleteList;
    }


    public JsonObject getRecipeJson() {

        JsonObject json = new JsonObject();

        for (int i = 0; i < this.recipeMap.size(); i++) {

            JsonObject recipe = new JsonObject();

            recipe.add("input", this.inputRecipeType.getRecipeJson(this.getInputs().get(i)));
            recipe.add("output", this.outputRecipeType.getRecipeJson(this.getOutput(i)));
            recipe.addProperty("energyDeplete", this.energyDepleteList.get(i));

            json.add(String.valueOf(i), recipe);
        }

        return json;
    }

    public void readRecipeJson(JsonObject json) {

        for (int i = 0; i < json.size(); i++) {
            JsonObject recipe = json.get(String.valueOf(i)).getAsJsonObject();

            this.addRecipe(this.inputRecipeType.getRecipe(recipe.get("input").getAsJsonObject()),
                    this.outputRecipeType.getRecipe(recipe.get("output").getAsJsonObject()),
                    recipe.get("energyDeplete").getAsInt());
        }

    }
}
