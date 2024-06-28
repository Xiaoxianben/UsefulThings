package com.xiaoxianben.usefulthings.gui.contrainer;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQIGatherer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerGatherer extends ContainerMachineBase {

    public TEMachineLingQIGatherer tileEntity;

    public ContainerGatherer(EntityPlayer player, TEMachineLingQIGatherer tileEntity) {
        super(player, tileEntity);

        this.tileEntity = tileEntity;
    }
}
