package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.init.modRegisterer.RegistererMinecraft;
import net.minecraftforge.oredict.OreDictionary;

public class ModOre {
    public static void init() {
        OreDictionary.registerOre("dustCommon", RegistererMinecraft.abnormal_dust);
        OreDictionary.registerOre("gemCommon", RegistererMinecraft.abnormal_gem);
        OreDictionary.registerOre("ingotCommon", RegistererMinecraft.abnormal_ingot);
    }
}
