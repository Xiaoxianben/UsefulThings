package com.xiaoxianben.usefulthings.jsonRecipe.recipeType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xiaoxianben.usefulthings.exception.RecipeException;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.FluidStackList;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemAndFluid;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackAndInt;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackList;
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
            return new JsonObject();
        }

        @Override
        public NullType getRecipe(JsonObject json) {
            return null;
        }

        @Override
        public boolean equals(NullType o1, NullType o2) {
            return o1 == null && o2 == null;
        }
    };
    public static final IRecipeType<ItemStack> recipe_itemStack = new RecipeItem();
    public static final IRecipeType<ItemStack> recipe_itemStack_noCheckNBT = new RecipeItem() {
        @Override
        public boolean equals(ItemStack o1, ItemStack o2) {
            return o2.getItem() == o1.getItem() && (o2.getMetadata() == 32767 || o2.getMetadata() == o1.getMetadata());
        }
    };
    public static final IRecipeType<ItemStackList> recipe_itemStackList = new IRecipeType<ItemStackList>() {
        @Override
        public Class<ItemStackList> getClassType() {
            return ItemStackList.class;
        }

        @Override
        public JsonObject getRecipeJson(ItemStackList recipe) throws RecipeException {
            JsonObject json = new JsonObject();

            JsonArray jsonArray = new JsonArray();

            for (ItemStack stack : recipe.list) {
                jsonArray.add(recipe_itemStack.getRecipeJson(stack));
            }
            json.add("items", jsonArray);

            return json;
        }

        @Override
        public ItemStackList getRecipe(JsonObject json) throws RecipeException {
            ItemStackList list = new ItemStackList();
            for (JsonElement element : json.getAsJsonArray("items")) {
                ItemStack stack = recipe_itemStack.getRecipe(element.getAsJsonObject());
                list.list.add(stack);
            }
            return list;
        }

        @Override
        public boolean equals(ItemStackList o1, ItemStackList o2) {
            o1.list.removeIf(ItemStack::isEmpty);
            o2.list.removeIf(ItemStack::isEmpty);

            if (o1.list.size() != o2.list.size()) {
                return false;
            }

            for (ItemStack itemStack : o1.list) {
                boolean empty = o2.getDafaultItemStack(itemStack).isEmpty();
                if (empty) {
                    return false;
                }
            }
            return true;
        }
    };
    public static final IRecipeType<ItemStackAndInt> recipe_itemStackAndInt = new RecipeItemStackAndInt();

    public static final IRecipeType<FluidStack> recipe_fluidStack = new IRecipeType<FluidStack>() {
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
    public static final IRecipeType<FluidStackList> recipe_fluidStackList = new IRecipeType<FluidStackList>() {
        @Override
        public Class<FluidStackList> getClassType() {
            return FluidStackList.class;
        }

        @Override
        public JsonObject getRecipeJson(FluidStackList recipe) throws RecipeException {
            JsonObject json = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (FluidStack stack : recipe.list) {
                jsonArray.add(recipe_fluidStack.getRecipeJson(stack));
            }
            json.add("fluids", jsonArray);
            return json;
        }

        @Override
        public FluidStackList getRecipe(JsonObject json) throws RecipeException {
            FluidStackList list = new FluidStackList();
            for (JsonElement element : json.getAsJsonArray("fluids")) {
                FluidStack stack = recipe_fluidStack.getRecipe(element.getAsJsonObject());
                list.list.add(stack);
            }
            return list;
        }

        @Override
        public boolean equals(FluidStackList o1, FluidStackList o2) {
            o1.list.removeIf(Objects::isNull);
            o2.list.removeIf(Objects::isNull);

            if (o1.list.size() != o2.list.size()) {
                return false;
            }

            for (FluidStack fluidStack : o1.list) {
                boolean empty = o2.contains(fluidStack);
                if (empty) {
                    return false;
                }
            }
            return true;
        }
    };

    public static final IRecipeType<ItemAndFluid> recipe_itemAndFluid = new IRecipeType<ItemAndFluid>() {

        @Override
        public Class<ItemAndFluid> getClassType() {
            return ItemAndFluid.class;
        }

        @Override
        public JsonObject getRecipeJson(ItemAndFluid recipe) throws RecipeException {
            JsonObject json = new JsonObject();
            json.add("item", recipe_itemStack.getRecipeJson(recipe.itemStack1));
            if (recipe.fluidStack1 != null) {
                json.add("fluid", recipe_fluidStack.getRecipeJson(recipe.fluidStack1));
            }
            return json;
        }

        @Override
        public ItemAndFluid getRecipe(JsonObject json) throws RecipeException {
            if(!json.has("fluid")) {
                return new ItemAndFluid(recipe_itemStack.getRecipe(json.get("item").getAsJsonObject()), null);
            }

            return new ItemAndFluid(recipe_itemStack.getRecipe(json.get("item").getAsJsonObject()), recipe_fluidStack.getRecipe(json.get("fluid").getAsJsonObject()));
        }
    };
}
