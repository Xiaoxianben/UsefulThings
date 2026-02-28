package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipe {

    private static int index = 0;

    public static void initRecipes() {
//        OreDictionary.getOres("dustCommon").forEach(dust -> {
//            ModJsonRecipes.recipe_abnormalCraft.getInput(TypeLing.type_ingot).list.forEach(ingot -> recipeAbnormalIngot(ingot, dust));
//            ModJsonRecipes.recipe_abnormalCraft.getInput(TypeLing.type_gem).list.forEach(gem -> recipeAbnormalGem(gem, dust));
//            ModJsonRecipes.recipe_abnormalCraft.getInput(TypeLing.type_dust).list.forEach(dust2 -> recipeAbnormalDust(dust2, dust));
//        });
    }

    public static void recipeAbnormalIngot(ItemStack output, ItemStack input) {
        Object[] recipe = new Object[]{
                "LLL",
                "LIL",
                "LLL",
                'I', output,
                'L', input
        };
        GameRegistry.addShapedRecipe(
                new ResourceLocation(ModInformation.MOD_ID, "recipe_" + index),
                new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                new ItemStack(output.getItem(), 9, output.getItemDamage()),
                recipe
        );
        index++;
    }

    public static void recipeAbnormalGem(ItemStack output, ItemStack input) {
        Object[] recipe = new Object[]{
                "LLL",
                "LGL",
                "LLL",
                'G', output,
                'L', input
        };
        GameRegistry.addShapedRecipe(
                new ResourceLocation(ModInformation.MOD_ID, "recipe_" + index),
                new ResourceLocation(ModInformation.MOD_ID, "gem"),
                new ItemStack(output.getItem(), 9, output.getItemDamage()),
                recipe
        );
        index++;
    }

    public static void recipeAbnormalDust(ItemStack output, ItemStack input) {
        Object[] recipe = new Object[]{
                "LLL",
                "LDL",
                "LLL",
                'D', output,
                'L', input
        };
        GameRegistry.addShapedRecipe(
                new ResourceLocation(ModInformation.MOD_ID, "recipe_" + index),
                new ResourceLocation(ModInformation.MOD_ID, "dust"),
                new ItemStack(output.getItem(), 9, output.getItemDamage()),
                recipe
        );
        index++;
    }
}
