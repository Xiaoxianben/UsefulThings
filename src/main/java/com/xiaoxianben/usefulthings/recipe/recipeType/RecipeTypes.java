package com.xiaoxianben.usefulthings.recipe.recipeType;

import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.recipe.ItemOrFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.lang.model.type.NullType;
import java.util.Objects;

public class RecipeTypes {
    public static final IRecipeType<NullType> recipe_null = new IRecipeType<NullType>() {
        @Override
        public Class<NullType> getClassType() {
            return NullType.class;
        }

        @Override
        public JsonObject getRecipeJson(NullType nullType) {
            JsonObject json = new JsonObject();

            json.addProperty("name", "null");

            return json;
        }

        @Override
        public NullType getRecipe(JsonObject json) {
            return null;
        }
    };
    public static final IRecipeType<ItemStack> recipe_item = new IRecipeType<ItemStack>() {
        @Override
        public Class<ItemStack> getClassType() {
            return ItemStack.class;
        }

        @Override
        public JsonObject getRecipeJson(ItemStack itemStack) {
            JsonObject json = new JsonObject();

            String name = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
            String count = Integer.toString(itemStack.getCount());

            json.addProperty("name", name);
            json.addProperty("count", count);

            return json;
        }

        @Override
        public ItemStack getRecipe(JsonObject json) {
            return new ItemStack(Objects.requireNonNull(Item.getByNameOrId(json.get("name").getAsString())), json.get("count").getAsInt());
        }
    };
    public static final IRecipeType<FluidStack> recipe_fluid = new IRecipeType<FluidStack>() {
        @Override
        public Class<FluidStack> getClassType() {
            return FluidStack.class;
        }

        @Override
        public JsonObject getRecipeJson(FluidStack fluidStack) {
            JsonObject json = new JsonObject();

            String name = Objects.requireNonNull(fluidStack.getFluid().getName());
            String count = Integer.toString(fluidStack.amount);

            json.addProperty("name", name);
            json.addProperty("count", count);

            return json;
        }

        @Override
        public FluidStack getRecipe(JsonObject json) {
            return new FluidStack(FluidRegistry.getFluid(json.get("name").getAsString()), json.get("count").getAsInt());
        }
    };
    public static final IRecipeType<ItemOrFluid> recipe_itemOrFluid = new IRecipeType<ItemOrFluid>() {
        @Override
        public Class<ItemOrFluid> getClassType() {
            return ItemOrFluid.class;
        }

        @Override
        public JsonObject getRecipeJson(ItemOrFluid itemOrFluid) {
            JsonObject json;

            if (itemOrFluid.get() instanceof ItemStack) {
                json = recipe_item.getRecipeJson((ItemStack) itemOrFluid.get());
            } else {
                json = recipe_fluid.getRecipeJson((FluidStack) itemOrFluid.get());
            }

            return json;
        }

        @Override
        public ItemOrFluid getRecipe(JsonObject json) {
            return new ItemOrFluid(json.get("name").getAsString(), json.get("count").getAsInt());
        }
    };
    public static final IRecipeType<Integer> recipe_energy = new RecipeInt("energy");
    public static final IRecipeType<Integer> recipe_level = new RecipeInt("level");
}
