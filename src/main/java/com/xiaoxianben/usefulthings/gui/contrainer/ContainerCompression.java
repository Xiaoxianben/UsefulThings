package com.xiaoxianben.usefulthings.gui.contrainer;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQICompression;
import com.xiaoxianben.usefulthings.slot.SlotTakeItemHandler;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCompression extends ContainerMachineBase {

    public TEMachineLingQICompression tileEntity;

    public ContainerCompression(EntityPlayer player, TEMachineLingQICompression tileEntity) {
        super(player, tileEntity);

        this.addSlotToContainer(new SlotTakeItemHandler(tileEntity.outputSlot, 0, 111, 37));

        this.tileEntity = tileEntity;
    }
}
