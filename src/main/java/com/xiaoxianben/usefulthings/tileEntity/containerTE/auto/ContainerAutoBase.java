package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.tileEntity.containerTE.ContainerTEBase;

public abstract class ContainerAutoBase extends ContainerTEBase {

    public ContainerAutoBase() {
    }

    public boolean canStart() {
        return true;
    }

    public void processStart() {
    }

    public void processFinish() {
    }

    public void processOff() {
    }
}