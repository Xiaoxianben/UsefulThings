package com.xiaoxianben.usefulthings.jei;

import com.xiaoxianben.usefulthings.blocks.machine.BlockLingQiCompression;
import com.xiaoxianben.usefulthings.blocks.machine.BlockLingQiGatherer;
import com.xiaoxianben.usefulthings.gui.gui.GUICompression;
import com.xiaoxianben.usefulthings.gui.gui.GUIGatherer;
import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.jei.advancedGuiHandlers.AdvancedGuiBaseHandler;
import com.xiaoxianben.usefulthings.jei.recipeCategory.lingQiCompressionRecipeCategory;
import com.xiaoxianben.usefulthings.jei.recipeCategory.lingQiGathererRecipeCategory;
import com.xiaoxianben.usefulthings.jei.wrapper.lingQiCompressionWrapper;
import com.xiaoxianben.usefulthings.jei.wrapper.lingQiGathererWrapper;
import com.xiaoxianben.usefulthings.recipe.ItemOrFluid;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JEIPlugin
public class modPlugin implements IModPlugin {

    public List<BlockLingQiGatherer> lingQiGatherers = new ArrayList<>();
    public List<BlockLingQiCompression> lingQiCompressions = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new lingQiCompressionRecipeCategory(guiHelper), new lingQiGathererRecipeCategory(guiHelper));
    }

    @Override
    public void register(@Nonnull IModRegistry registration) {
        lingQiGatherers.addAll(Arrays.asList(ModBlocks.BLOCK_LING_QI_GATHERER));
        lingQiCompressions.addAll(Arrays.asList(ModBlocks.BLOCK_LING_QI_COMPRESSION));

        for (BlockLingQiGatherer i : lingQiGatherers) {
            if (i != null)
                registration.addRecipeCatalyst(Item.getItemFromBlock(i).getDefaultInstance(), lingQiGathererRecipeCategory.lingQiGathererUID);
        }

        for (BlockLingQiCompression b : lingQiCompressions) {
            if (b != null)
                registration.addRecipeCatalyst(Item.getItemFromBlock(b).getDefaultInstance(), lingQiCompressionRecipeCategory.lingQiCompressionUID);
        }

        registration.addRecipes(lingQiGathererRecipes(), lingQiGathererRecipeCategory.lingQiGathererUID);
        registration.addRecipes(lingQiCompressionRecipes(), lingQiCompressionRecipeCategory.lingQiCompressionUID);

        registration.addRecipeClickArea(GUIGatherer.class, 34, 27, 32, 32, lingQiGathererRecipeCategory.lingQiGathererUID);
        registration.addRecipeClickArea(GUICompression.class, 67, 36, 18, 18, lingQiCompressionRecipeCategory.lingQiCompressionUID);
        registration.addAdvancedGuiHandlers(new AdvancedGuiBaseHandler());
    }

    private List<lingQiCompressionWrapper> lingQiCompressionRecipes() {
        List<lingQiCompressionWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < RecipesList.lingQiCompression.getInputs().size(); i++) {
            FluidStack input = RecipesList.lingQiCompression.getInput(i);
            ItemOrFluid Output = RecipesList.lingQiCompression.getOutput(i);

            recipes.add(new lingQiCompressionWrapper(input, Output));
        }

        return recipes;
    }

    private List<lingQiGathererWrapper> lingQiGathererRecipes() {
        List<lingQiGathererWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < RecipesList.lingQiGatherer.getInputs().size(); i++) {
            FluidStack Output = RecipesList.lingQiGatherer.getOutput(i);

            recipes.add(new lingQiGathererWrapper(Output));
        }

        return recipes;
    }

}
