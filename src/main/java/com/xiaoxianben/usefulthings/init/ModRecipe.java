package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.item.ItemComponent;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipe {

    public static void initRecipes() {
        RecipesList.init();
    }

    public static void recipeBlockIngot(Block outputBlock, Item outputItem, String blockOre, String itemOre) {
        Object[] recipe = new Object[]{
                "III",
                "III",
                "III",
                'I', itemOre
        };

        GameRegistry.addShapedRecipe(
                outputBlock.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "block"),
                Item.getItemFromBlock(outputBlock).getDefaultInstance(),
                recipe
        );

        ItemStack outputItemStack = new ItemStack(outputItem, 9);
        GameRegistry.addShapelessRecipe(
                outputItem.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                outputItemStack,
                CraftingHelper.getIngredient(blockOre)
        );
    }

    public static void registerBlockInfiniteWater(Block outputBlock) {
        // 注册一个方块-无限水源的配方
        Object[] recipe = new Object[]{
                "XKX",
                "KGK",
                "XKX",
                'X', ModItems.virtualWaterSource,
                'K', "ingotLingQi",
                'G', "blockDiamond"
        };
        GameRegistry.addShapedRecipe(
                outputBlock.getRegistryName(),
                null,
                Item.getItemFromBlock(outputBlock).getDefaultInstance(),
                recipe
        );
    }

    public static void recipeMachine(Block outputBlock, String oreShell, String centerOre, String ingotOre, Item inputItem, String group) {
        Object[] recipe = new Object[]{
                "SIS",
                "ICI",
                "OIO",
                'I', ingotOre,
                'S', oreShell,
                'C', centerOre,
                'O', inputItem
        };
        GameRegistry.addShapedRecipe(
                outputBlock.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, group),
                Item.getItemFromBlock(outputBlock).getDefaultInstance(),
                recipe
        );
    }

    public static void recipeMachine(Block outputBlock, Item inputItem) {
        recipeMachine(outputBlock, "blockIron", "blockGold", "gemDiamond", inputItem, "machine");
    }

    public static void recipeLingQiMachine(Block outputBlock, Item inputItem) {
        recipeMachine(outputBlock, "blockIron", "blockGold", "ingotLingQi", inputItem, "machine");
    }

    public static void recipeGenerator(Block outputBlock, Item inputItem) {
        recipeMachine(outputBlock, "blockIron", "blockRedstone", "ingotLingQi", inputItem, "generator");
    }

    public static void recipeComponent(ItemComponent outputItem, Item materialItem) {
        Object[] recipe = new Object[]{
                "III",
                "MOM",
                "III",
                'I', "ingotIron",
                'M', materialItem,
                'O', outputItem == ModItems.component_null ? ModItems.LingQi_ingot : ModItems.component_null
        };
        GameRegistry.addShapedRecipe(
                outputItem.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "component"),
                outputItem.getDefaultInstance(),
                recipe
        );
    }
}
