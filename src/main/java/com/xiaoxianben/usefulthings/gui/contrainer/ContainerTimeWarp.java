package com.xiaoxianben.usefulthings.gui.contrainer;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineTimeWarp;
import com.xiaoxianben.usefulthings.api.ITextFieldChange;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerTimeWarp extends ContainerBase implements ITextFieldChange {

    public TEMachineTimeWarp tileEntity;

    public ContainerTimeWarp(EntityPlayer player, TEMachineTimeWarp tileEntity) {
        super(player);

        this.tileEntity = tileEntity;
    }

    @Override
    public void onTextFieldChange(int ID, String text) {
        if (ID < this.tileEntity.scope.length) {
            this.tileEntity.scope[ID] = Integer.parseInt(text);
        } else if (ID == this.tileEntity.scope.length) {
            this.tileEntity.multiplier = Integer.parseInt(text);
        }
    }
}
