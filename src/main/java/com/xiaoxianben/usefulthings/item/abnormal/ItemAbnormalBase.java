package com.xiaoxianben.usefulthings.item.abnormal;

import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import com.xiaoxianben.usefulthings.item.ItemBase;

public class ItemAbnormalBase extends ItemBase {
    public final String type;

    public ItemAbnormalBase(String type) {
        super("abnormal_" + type, ModCreativeTab.creative_tab_main);
        this.type = type;
    }

}
