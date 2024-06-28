package com.xiaoxianben.usefulthings.gui.gui.responder;

import net.minecraft.client.gui.GuiPageButtonList;

public abstract class IntResponder implements GuiPageButtonList.GuiResponder {
    @Override
    public void setEntryValue(int id, boolean value) {
        this.setEntryValue(id, Boolean.toString(value));
    }

    @Override
    public void setEntryValue(int id, float value) {
        this.setEntryValue(id, Float.toString(value));
    }
}
