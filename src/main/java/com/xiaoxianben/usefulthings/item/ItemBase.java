package com.xiaoxianben.usefulthings.item;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.util.IHasModel;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

    public ItemBase(String name) {
        setUnlocalizedName("ut." + name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.mod_creative_tab);

        ModItems.ITEMS.add(this);
    }


    @Override
    public void registerModels() {
        UsefulThings.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
