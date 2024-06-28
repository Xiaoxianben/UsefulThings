package com.xiaoxianben.usefulthings.event;

import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModModelRegister {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.ITEMS) {
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        for (Block block : ModBlocks.BLOCKS) {
            event.getRegistry().register(block);
        }
    }

}
