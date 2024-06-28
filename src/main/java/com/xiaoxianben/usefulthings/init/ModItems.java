package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.item.ItemBase;
import com.xiaoxianben.usefulthings.item.ItemComponent;
import com.xiaoxianben.usefulthings.item.ItemVirtualWater;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();
    /**
     * 虚拟水源
     */
    public static ItemVirtualWater virtualWaterSource;
    /**
     * 灵气锭
     */
    public static ItemBase LingQi_ingot;

    /**
     * 组件
     */
    public static ItemComponent component_null;
    public static ItemComponent component_machine;

    // 初始化物品
    public static void initItem() {
        LingQi_ingot = new ItemBase("lingqi_ingot");
        virtualWaterSource = new ItemVirtualWater();
        component_null = new ItemComponent("null", 0);
        component_machine = new ItemComponent("machine", 1);
    }

    public static void initItemRecipe() {
        Object[] params = new Object[]{
                "LWL",
                "WDW",
                "LWL",
                'D', "blockDiamond",
                'L', "ingotLingQi",
                'W', Items.WATER_BUCKET
        };
        GameRegistry.addShapedRecipe(
                virtualWaterSource.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "virtual_water"),
                virtualWaterSource.getDefaultInstance(),
                params
        );
        ModRecipe.recipeComponent(component_null, Item.getItemFromBlock(Blocks.PISTON));
        ModRecipe.recipeComponent(component_machine, LingQi_ingot);
    }
}
