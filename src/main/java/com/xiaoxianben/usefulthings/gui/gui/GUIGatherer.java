package com.xiaoxianben.usefulthings.gui.gui;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQIGatherer;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerGatherer;

public class GUIGatherer extends GUIBase {


    public GUIGatherer(ContainerGatherer inventorySlotsIn) {
        super(inventorySlotsIn, 0);
    }


    @Override
    protected void drawTextured() {
        TEMachineLingQIGatherer tileEntity = ((ContainerGatherer) this.inventorySlots).tileEntity;

        if (tileEntity.isActive) drawTexturedModalRect(this.guiLeft + 34, this.guiTop + 27, 196, 0, 32, 32);

        this.drawTextureRect(tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL(), 7, 4, 176, 0, 20, 72);
        drawFluid(tileEntity.outputFluidTank, 74, 14, 16, 58);
    }

    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        TEMachineLingQIGatherer tileEntity = ((ContainerGatherer) this.inventorySlots).tileEntity;

        // 绘制鼠标矩形
        drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, String.format("FE:\n%d/%d", tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL()));

        String fluidName = tileEntity.outputFluidTank.getFluid() == null ? "Empty" : tileEntity.outputFluidTank.getFluid().getLocalizedName();
        drawMouseRect(mouseX, mouseY, 74, 14, 16, 58, String.format("%s:\n%d/%d", fluidName, tileEntity.outputFluidTank.getFluidAmount(), tileEntity.outputFluidTank.getCapacity()));
    }

}
