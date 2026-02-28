package com.xiaoxianben.usefulthings.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.config.ConfigValue;
import com.xiaoxianben.usefulthings.init.modRegisterer.RegistererMinecraft;
import com.xiaoxianben.usefulthings.jsonRecipe.RecipeJson;
import com.xiaoxianben.usefulthings.jsonRecipe.RecipeJsonChemical;
import com.xiaoxianben.usefulthings.jsonRecipe.RecipeJsonExtruder;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackAndInt;
import com.xiaoxianben.usefulthings.jsonRecipe.wapper.ItemStackList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

public class ModJsonRecipes {

    private static final String modRecipeDir = ConfigValue.modConfigurationDirectory + "recipes" + File.separator;

    public static RecipeJson<ItemStack, ItemStack> recipe_abnormalCraft = new RecipeJson<>("abnormal_craft", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStackAndInt> recipe_gather = new RecipeJson<>("gather_ling", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStackAndInt);
    public static RecipeJson<ItemStackList, ItemStack> recipe_metallurgy = new RecipeJson<>("craft", RecipeTypes.recipe_itemStackList, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_furnace = new RecipeJson<>("furnace", RecipeTypes.recipe_itemStack_noCheckNBT, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_pulverizer = new RecipeJson<>("pulverizer", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJsonExtruder recipe_extruder = new RecipeJsonExtruder("extruder");
    public static RecipeJson<ItemStack, FluidStack> recipe_crucible = new RecipeJson<>("crucible", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_fluidStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_charger = new RecipeJson<>("charger", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_broken_mine = new RecipeJson<>("broken_mine", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_decompress = new RecipeJson<>("decompress", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_compress = new RecipeJson<>("compress", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJsonChemical recipe_chemical = new RecipeJsonChemical();
    public static RecipeJson<ItemStack, ItemStack> recipe_electrolysis = new RecipeJson<>("electrolysis", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_washes = new RecipeJson<>("washes", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);
    public static RecipeJson<ItemStack, ItemStack> recipe_infusion = new RecipeJson<>("infusion", RecipeTypes.recipe_itemStack, RecipeTypes.recipe_itemStack);


    public static void init() {
        recipe_abnormalCraft.addRecipe("ingot_iron", new ItemStack(Items.IRON_INGOT), new ItemStack(RegistererMinecraft.abnormal_ingot));
        recipe_abnormalCraft.addRecipe("ingot_gold", new ItemStack(Items.GOLD_INGOT), new ItemStack(RegistererMinecraft.abnormal_ingot));
        recipe_abnormalCraft.addRecipe("dust_diamond", new ItemStack(Items.DIAMOND), new ItemStack(RegistererMinecraft.abnormal_gem));
        recipe_abnormalCraft.addRecipe("dust_emerald", new ItemStack(Items.EMERALD), new ItemStack(RegistererMinecraft.abnormal_gem));
        recipe_abnormalCraft.addRecipe("dust_redstone", new ItemStack(Items.REDSTONE), new ItemStack(RegistererMinecraft.abnormal_dust));
        recipe_abnormalCraft.addRecipe("dust_glowstone", new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(RegistererMinecraft.abnormal_dust));

        recipe_gather.addRecipe("1", new ItemStack(Items.GLASS_BOTTLE), new ItemStackAndInt(RegistererMinecraft.bottle_ling, 1000));

        // recipe_auto_metallurgy.addRecipe("craft", new ItemStackList(Items.IRON_INGOT, Items.GOLD_INGOT), new ItemStack(RegistererMinecraft.auto_machine));

        FurnaceRecipes.instance().getSmeltingList().forEach(
                (input, output) -> {
                    ResourceLocation registryName = input.getItem().getRegistryName();
                    if (registryName != null) {
                        recipe_furnace.addRecipe(registryName.toString(), input, output);
                    }
                }
        );

        // 添加你的配方
        readJson(recipe_abnormalCraft);
        readJson(recipe_furnace);
        readJson(recipe_metallurgy);
        readJson(recipe_gather);
        readJson(recipe_pulverizer);
        readJson(recipe_extruder);
        readJson(recipe_crucible);
        readJson(recipe_charger);
        readJson(recipe_broken_mine);
        readJson(recipe_decompress);
        readJson(recipe_compress);
        readJson(recipe_chemical);
        readJson(recipe_electrolysis);
        readJson(recipe_washes);
        readJson(recipe_infusion);
    }


    /**
     * 读取JSON文件，并返回一个recipes对象。
     * <p>如果文件不存在，则返回默认值，并且保存默认值到文件中。
     * <p>如果文件存在，则将文件转化为配方<tt>recipes</tt>。
     */
    @Nonnull
    public static <Input, Output> RecipeJson<Input, Output> readJson(RecipeJson<Input, Output> defaultRecipe) {
        final String path = modRecipeDir + defaultRecipe.name + ".json";
        JsonObject jsonObject = readFileToJsonObject(path);

        // 如果文件不存在，则返回默认值
        if (jsonObject == null) {
            return defaultRecipe;
        }

        return defaultRecipe.modifyRecipe(jsonObject);
    }

    /**
     * 保存一个recipes对象到JSON文件中。
     *
     * @param path          文件的路径。
     * @param defaultRecipe 默认的配方。
     */
    public static <Input, Output> void saveRecipe(String path, @Nonnull RecipeJson<Input, Output> defaultRecipe) {
        JsonObject jsonObject = defaultRecipe.getRecipeJson();
        saveJson(jsonObject, path);
    }

    /**
     * 保存json到文件中。
     *
     * @param jsonObject 要保存的json数组。
     * @param path       保存json数组的文件路径。
     */
    public static void saveJson(JsonObject jsonObject, String path) {
        saveJson(jsonObject, new File(path));
    }

    /**
     * 保存json到文件中。
     *
     * @param jsonObject 要保存的json对象。
     * @param file       保存json数组的文件。
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveJson(JsonObject jsonObject, @Nonnull File file) {
        FileWriter writer;
        try {
            // 创建文件（如果不存在）
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);

        } catch (IOException e) {
            ConfigLoader.logger().throwing(e);
        }
    }

    /**
     * 读取JSON文件，并返回一个JsonArray。
     * <p>如果文件不存在或文件无法解析为<tt>JsonArray</tt>，则返回null。
     * <p>如果文件存在，则将文件转化为<tt>JsonArray</tt>。
     */
    @Nullable
    private static JsonObject readFileToJsonObject(String filePath) {
        // 读取文件内容

        JsonObject jsonObject = null;

        try {
            InputStream in = FileUtils.openInputStream(new File(filePath));
            String content = IOUtils.toString(in, Charsets.toCharset("UTF-8"));
            jsonObject = new JsonParser().parse(content).getAsJsonObject();
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            ConfigLoader.logger().throwing(e);
        }
        // 解析JSON数组
        return jsonObject;
    }

}
