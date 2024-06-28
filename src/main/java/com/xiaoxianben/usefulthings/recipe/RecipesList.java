package com.xiaoxianben.usefulthings.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.config.ConfigValue;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.recipe.recipeType.RecipeTypes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.NullType;
import java.io.*;

public class RecipesList {


    public static Recipes<NullType, FluidStack> lingQiGatherer = new Recipes<>(RecipeTypes.recipe_null, RecipeTypes.recipe_fluid);
    public static Recipes<FluidStack, ItemOrFluid> lingQiCompression = new Recipes<>(RecipeTypes.recipe_fluid, RecipeTypes.recipe_itemOrFluid);
    public static Recipes<ItemOrFluid, Integer> lingQiGenerator = new Recipes<>(RecipeTypes.recipe_itemOrFluid, RecipeTypes.recipe_energy);
    public static Recipes<Integer, NullType> timeWarp = new Recipes<>(RecipeTypes.recipe_level, RecipeTypes.recipe_null);


    public static void init() {
        lingQiCompression.addRecipe(new FluidStack(FluidRegistry.getFluid("lingqi"), 10), new ItemOrFluid(FluidRegistry.getFluid("lingqi_fluid")), 2);
        lingQiCompression.addRecipe(new FluidStack(FluidRegistry.getFluid("lingqi_fluid"), 10), new ItemOrFluid(ModItems.LingQi_ingot), 2);

        lingQiGatherer.addRecipe(null, new FluidStack(FluidRegistry.getFluid("lingqi"), 1), 2);

        lingQiGenerator.addRecipe(new ItemOrFluid(FluidRegistry.getFluid("lingqi")), 1, 0);
        lingQiGenerator.addRecipe(new ItemOrFluid(FluidRegistry.getFluid("lingqi_fluid")), 22, 2);
        lingQiGenerator.addRecipe(new ItemOrFluid(ModItems.LingQi_ingot), 422, 22);

        for (int i = 1; i <= 9999; i++) {
            timeWarp.addRecipe(i, null, (int) (Math.pow(i, 2) * 0.4 + 1));
        }

        // 添加你的配方
        String modRecipeDir = ConfigValue.modConfigurationDirectory + "recipes/";
        lingQiCompression = readJson(modRecipeDir + "lingQiCompression.json", lingQiCompression);
        lingQiGatherer = readJson(modRecipeDir + "lingQiGatherer.json", lingQiGatherer);
        lingQiGenerator = readJson(modRecipeDir + "lingQiGenerator.json", lingQiGenerator);
        timeWarp = readJson(modRecipeDir + "timeWarp.json", timeWarp);
    }


    /**
     * 读取JSON文件，并返回一个recipes对象。
     * <p>如果文件不存在，则返回默认值，并且保存默认值到文件中。
     * <p>如果文件存在，则将文件转化为配方<tt>recipes</tt>。
     */
    @Nonnull
    public static <Input, Output> Recipes<Input, Output> readJson(String path, Recipes<Input, Output> defaultRecipe) {
        JsonObject jsonObject = readFileToJsonArray(path);
        // 如果文件不存在，则返回默认值
        if (jsonObject == null) {
            saveRecipe(path, defaultRecipe);
            return defaultRecipe;
        }
        Recipes<Input, Output> newRecipes = new Recipes<>(defaultRecipe.inputRecipeType, defaultRecipe.outputRecipeType);
        newRecipes.readRecipeJson(jsonObject);
        return newRecipes;
    }

    /**
     * 保存一个recipes对象到JSON文件中。
     *
     * @param path          文件的路径。
     * @param defaultRecipe 默认的配方。
     */
    public static <Input, Output> void saveRecipe(String path, @Nonnull Recipes<Input, Output> defaultRecipe) {
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
        try (FileWriter writer = new FileWriter(file)) {
            // 创建文件（如果不存在）
            if (!file.exists()) {
                file.createNewFile();
            }

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
    private static JsonObject readFileToJsonArray(String filePath) {
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
