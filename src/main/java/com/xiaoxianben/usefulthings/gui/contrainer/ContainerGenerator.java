package com.xiaoxianben.usefulthings.gui.contrainer;

import com.xiaoxianben.usefulthings.TileEntity.generator.TEGeneratorLingQI;
import com.xiaoxianben.usefulthings.slot.SlotTakeItemHandler;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerGenerator extends ContainerMachineBase {

    public TEGeneratorLingQI tileEntity;

    public ContainerGenerator(EntityPlayer player, TEGeneratorLingQI tileEntity) {
        super(player, tileEntity);

        this.addSlotToContainer(new SlotTakeItemHandler(tileEntity.inputItemHandler, 0, 111, 37));

        this.tileEntity = tileEntity;
    }
}
