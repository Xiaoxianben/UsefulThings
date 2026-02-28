package com.xiaoxianben.usefulthings.gui.guiScreen;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GUIScreenBase extends GuiContainer {
    protected abstract ResourceLocation getTEXTURES();

    public GUIScreenBase(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(getTEXTURES());
        drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 256, 256);

        drawGuiTexture(mouseX, mouseY);
        drawBGGuiText(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        drawFGGuiText(mouseX, mouseY);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    public abstract void drawGuiTexture(int mouseX, int mouseY);
    public abstract void drawBGGuiText(int mouseX, int mouseY);
    public abstract void drawFGGuiText(int mouseX, int mouseY);
}
