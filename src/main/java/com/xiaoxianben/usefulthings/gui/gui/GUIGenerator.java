package com.xiaoxianben.usefulthings.gui.gui;

import com.xiaoxianben.usefulthings.TileEntity.generator.TEGeneratorLingQI;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerGenerator;

public class GUIGenerator extends GUIBase {


    public GUIGenerator(ContainerGenerator inventorySlotsIn) {
        super(inventorySlotsIn, 2);
    }


    @Override
    protected void drawTextured() {
        TEGeneratorLingQI tileEntity = ((ContainerGenerator) this.inventorySlots).tileEntity;

        if (tileEntity.isActive) drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 29, 196, 0, 32, 32);

        this.drawTextureRect(tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL(), 7, 4, 176, 0, 20, 72);
        drawFluid(tileEntity.LingQiTank, 66, 17, 16, 58);
        drawFluid(tileEntity.LingQiFluidTank, 88, 17, 16, 58);
    }

    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        TEGeneratorLingQI tileEntity = ((ContainerGenerator) this.inventorySlots).tileEntity;

        // 绘制鼠标矩形
        drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, String.format("FE:\n%d/%d", tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL()));

        String fluidName = tileEntity.LingQiTank.getFluid() == null ? "Empty" : tileEntity.LingQiTank.getFluid().getLocalizedName();
        drawMouseRect(mouseX, mouseY, 66, 17, 16, 58, String.format("%s:\n%d/%d", fluidName, tileEntity.LingQiTank.getFluidAmount(), tileEntity.LingQiTank.getCapacity()));

        fluidName = tileEntity.LingQiFluidTank.getFluid() == null ? "Empty" : tileEntity.LingQiFluidTank.getFluid().getLocalizedName();
        drawMouseRect(mouseX, mouseY, 88, 17, 16, 58, String.format("%s:\n%d/%d", fluidName, tileEntity.LingQiFluidTank.getFluidAmount(), tileEntity.LingQiFluidTank.getCapacity()));
    }

}
