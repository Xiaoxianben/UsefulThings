package com.xiaoxianben.usefulthings.jei.advancedGuiHandlers;

import com.xiaoxianben.usefulthings.gui.gui.GUIBase;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class AdvancedGuiBaseHandler implements IAdvancedGuiHandler<GUIBase> {

    @Nonnull
    @Override
    public Class<GUIBase> getGuiContainerClass() {
        return GUIBase.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(@Nonnull GUIBase guiContainer) {
        return guiContainer.getGuiExtraAreas();
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(@Nonnull GUIBase guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
