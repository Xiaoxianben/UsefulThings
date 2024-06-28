package com.xiaoxianben.usefulthings.gui.contrainer;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineBase;
import com.xiaoxianben.usefulthings.slot.SlotTakeItemHandler;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class ContainerMachineBase extends ContainerBase {
    public ContainerMachineBase(EntityPlayer player, TEMachineBase tileEntity) {
        super(player);

        for (int i = 0; i < tileEntity.getItemComponentHandler().getSlots(); i++) {
            guiExtraAreas.add(new Rectangle(-18, i * 18, 18, 18));
            this.addSlotToContainer(new SlotTakeItemHandler(tileEntity.getItemComponentHandler(), i, -17, 1 + i * 18));
        }

    }
}
