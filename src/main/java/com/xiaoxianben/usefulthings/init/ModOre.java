package com.xiaoxianben.usefulthings.init;

import net.minecraftforge.oredict.OreDictionary;

public class ModOre {
    public static void init() {
        OreDictionary.registerOre("ingotLingQi", ModItems.LingQi_ingot);

        OreDictionary.registerOre("blockLingQi", ModBlocks.LingQi_block);
    }
}
