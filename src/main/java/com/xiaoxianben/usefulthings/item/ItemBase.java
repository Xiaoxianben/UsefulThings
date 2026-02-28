package com.xiaoxianben.usefulthings.item;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.api.IHasModel;
import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

    public ItemBase(String name, ModCreativeTab creativeTab) {
        setUnlocalizedName("ut-" + name);
        setRegistryName(name);
        setCreativeTab(creativeTab);
    }


    @Override
    public void registerModels() {
        UsefulThings.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
