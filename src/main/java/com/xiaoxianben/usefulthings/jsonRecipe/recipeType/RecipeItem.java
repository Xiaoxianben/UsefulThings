package com.xiaoxianben.usefulthings.jsonRecipe.recipeType;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.exception.RecipeException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;

public class RecipeItem implements IRecipeType<ItemStack> {

    @Override
    public Class<ItemStack> getClassType() {
        return ItemStack.class;
    }


    @Override
    public JsonObject getRecipeJson(ItemStack recipe) throws RecipeException {
        ResourceLocation registryName = recipe.getItem().getRegistryName();
        if (registryName == null) {
            throw new RecipeException("item: %d don't have registry name", recipe.getItem().toString());
        }
        JsonObject jsonObject1 = new JsonObject();

        jsonObject1.addProperty("item", registryName.toString());
        jsonObject1.addProperty("count", recipe.getCount());
        jsonObject1.addProperty("meta", recipe.getMetadata());

        return jsonObject1;
    }

    @Override
    public ItemStack getRecipe(JsonObject json) throws RecipeException {
        String registryName = json.get("item").getAsString();
        Item item = Item.getByNameOrId(registryName);
        if (item == null) {
            throw new RecipeException("item: %s don't exist", registryName);
        }
        return new ItemStack(item, json.get("count").getAsInt(), json.get("meta").getAsInt());
    }

    @Override
    public boolean equals(ItemStack o1, ItemStack o2) {
        return ItemHandlerHelper.canItemStacksStack(o1, o2);
    }
}
