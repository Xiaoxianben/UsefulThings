package com.xiaoxianben.usefulthings.jei.recipeCategory;

import com.xiaoxianben.usefulthings.jei.wrapper.lingQiCompressionWrapper;
import com.xiaoxianben.usefulthings.util.ModInformation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class lingQiCompressionRecipeCategory implements IRecipeCategory<lingQiCompressionWrapper> {

    public static String lingQiCompressionUID = ModInformation.MOD_ID + ":lingQiCompression";
    private final String title;
    private final IDrawable background, animation;

    public lingQiCompressionRecipeCategory(IGuiHelper guiHelper) {
        this.title = I18n.format("RC.lingqiCompression.title");
        this.background = guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/1.png"), 46, 15, 83, 62);
        this.animation = guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/1.png"), 196, 0, 18, 18);
    }

    @Nonnull
    @Override
    public String getUid() {
        return lingQiCompressionUID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Nonnull
    @Override
    public String getModName() {
        return ModInformation.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        this.animation.draw(minecraft, 21, 21);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull lingQiCompressionWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();

        guiFluidStackGroup.init(0, true, 2, 2, 16, 58, 1, false, null);
        guiFluidStackGroup.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));

        if (!ingredients.getOutputs(VanillaTypes.FLUID).isEmpty()) {
            guiFluidStackGroup.init(1, false, 42, 2, 16, 58, ingredients.getOutputs(VanillaTypes.FLUID).get(0).get(0).amount, false, null);
            guiFluidStackGroup.set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
        }


        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();

        if (!ingredients.getOutputs(VanillaTypes.ITEM).isEmpty()) {
            guiItemStackGroup.init(0, true, 64, 21);
            guiItemStackGroup.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
        }
    }

}
