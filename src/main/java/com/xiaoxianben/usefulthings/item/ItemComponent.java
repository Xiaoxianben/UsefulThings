package com.xiaoxianben.usefulthings.item;

public class ItemComponent extends ItemBase {
    public ItemComponent(String name, int stackSize) {
        super("component_" + name);
        this.setMaxStackSize(stackSize);
    }

}
