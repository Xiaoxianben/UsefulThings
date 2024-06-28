package com.xiaoxianben.usefulthings.gui.gui;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQICompression;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerCompression;

public class GUICompression extends GUIBase {


    public GUICompression(ContainerCompression inventorySlotsIn) {
        super(inventorySlotsIn, 1);
    }


    @Override
    protected void drawTextured() {
        TEMachineLingQICompression tileEntity = ((ContainerCompression) this.inventorySlots).tileEntity;

        if (tileEntity.isActive) drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 36, 196, 0, 18, 18);

        this.drawTextureRect(tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL(), 7, 4, 176, 0, 20, 72);
        drawFluid(tileEntity.inputFluidTank, 48, 17, 16, 58);
        drawFluid(tileEntity.outputFluidTank, 88, 17, 16, 58);
    }

    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        TEMachineLingQICompression tileEntity = ((ContainerCompression) this.inventorySlots).tileEntity;

        // 绘制鼠标矩形
        drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, String.format("FE:\n%d/%d", tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL()));

        String fluidName = tileEntity.inputFluidTank.getFluid() == null ? "Empty" : tileEntity.inputFluidTank.getFluid().getLocalizedName();
        drawMouseRect(mouseX, mouseY, 48, 17, 16, 58, String.format("%s:\n%d/%d", fluidName, tileEntity.inputFluidTank.getFluidAmount(), tileEntity.inputFluidTank.getCapacity()));

        fluidName = tileEntity.outputFluidTank.getFluid() == null ? "Empty" : tileEntity.outputFluidTank.getFluid().getLocalizedName();
        drawMouseRect(mouseX, mouseY, 88, 17, 16, 58, String.format("%s:\n%d/%d", fluidName, tileEntity.outputFluidTank.getFluidAmount(), tileEntity.outputFluidTank.getCapacity()));
    }

}
