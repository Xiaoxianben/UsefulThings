package com.xiaoxianben.usefulthings.jei.recipeCategory;

import com.xiaoxianben.usefulthings.jei.wrapper.lingQiGathererWrapper;
import com.xiaoxianben.usefulthings.util.ModInformation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class lingQiGathererRecipeCategory implements IRecipeCategory<lingQiGathererWrapper> {

    public static String lingQiGathererUID = ModInformation.MOD_ID + ":lingQiGatherer";
    private final String title;
    private final IDrawable background, animation;

    public lingQiGathererRecipeCategory(IGuiHelper guiHelper) {
        this.title = I18n.format("RC.lingQiGatherer.title");
        this.background = guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/0.png"), 32, 12, 60, 62);
        this.animation = guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/0.png"), 196, 0, 32, 32);
    }

    @Nonnull
    @Override
    public String getUid() {
        return lingQiGathererUID;
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
        this.animation.draw(minecraft, 2, 15);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull lingQiGathererWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();

        guiFluidStackGroup.init(0, false, 42, 2, 16, 58, ingredients.getOutputs(VanillaTypes.FLUID).get(0).get(0).amount, false, null);

        guiFluidStackGroup.set(0, ingredients.getOutputs(VanillaTypes.FLUID).get(0));

    }

}
