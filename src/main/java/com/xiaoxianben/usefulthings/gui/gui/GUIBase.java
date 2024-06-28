package com.xiaoxianben.usefulthings.gui.gui;

import com.xiaoxianben.usefulthings.client.RenderFluid;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerBase;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public abstract class GUIBase extends GuiContainer {
    protected final ResourceLocation TEXTURES;

    public GUIBase(ContainerBase inventorySlotsIn, int id) {
        super(inventorySlotsIn);
        TEXTURES = new ResourceLocation(ModInformation.MOD_ID + ":textures/gui/%d.png".replaceFirst("%d", String.valueOf(id)));
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.drawAllMouseRect(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        for (Rectangle guiExtraArea : this.getGuiExtraAreas()) {
            this.drawTexturedModalRect(this.guiLeft + guiExtraArea.x, this.guiTop + guiExtraArea.y, 7, 83, guiExtraArea.width, guiExtraArea.height);
        }

        drawTextured();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    /**
     * 绘制 所有矩形，在 背景绘制时。
     */
    protected abstract void drawTextured();

    /**
     * 绘制 所有鼠标矩形，在 前景绘制时。
     */
    protected abstract void drawAllMouseRect(int mouseX, int mouseY);

    /**
     * 获取 额外的GUI矩形。
     */
    public List<Rectangle> getGuiExtraAreas() {
        return ((ContainerBase) this.inventorySlots).getGuiExtraAreas();
    }


    protected void drawFluid(@Nonnull FluidTank tank, double x, double y, double width, double height) {
        RenderFluid.renderGuiTank(tank, this.guiLeft + x, this.guiTop + y, 300, width, height);
    }

    protected void drawTextureRect(float value, float MaxValue, int x, int y, int textureX, int textureY, int width, int height) {
        int h = (int) ((value / MaxValue) * height);
        int drawY = height - h;
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y + drawY, textureX, textureY + drawY, width, h);
    }

    protected void drawMouseRect(int mouseX, int mouseY, int StartX, int startY, int width, int height, String text) {
        if (mouseX > this.guiLeft + StartX && mouseX < this.guiLeft + StartX + width &&
                mouseY > this.guiTop + startY && mouseY < this.guiTop + startY + height) {
            drawRect(StartX, startY, StartX + width, startY + height, 0x50000000);

            this.drawHoveringText(Arrays.asList(text.split("\n")), mouseX - this.guiLeft, mouseY - this.guiTop);
        }
    }
}
