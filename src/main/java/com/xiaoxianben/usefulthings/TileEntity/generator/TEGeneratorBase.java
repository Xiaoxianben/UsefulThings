package com.xiaoxianben.usefulthings.TileEntity.generator;

import com.xiaoxianben.usefulthings.TileEntity.itemStackHandler.ItemComponentHandler;
import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineBase;

public abstract class TEGeneratorBase extends TEMachineBase {


    public TEGeneratorBase() {
        super();
        this.itemComponentHandler = new ItemComponentHandler(1, ItemComponentHandler.machine);
        this.canReceive = false;
        this.canExtract = true;
    }

}
