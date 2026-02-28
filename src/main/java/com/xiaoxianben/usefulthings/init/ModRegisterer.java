package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.api.IModRegisterer;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.init.modRegisterer.RegistererMinecraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class ModRegisterer {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    private static final List<IModRegisterer> registerers = new ArrayList<>();

    public static void register(IModRegisterer registerer) {
        if (!Loader.isModLoaded(registerer.getModId())) {
            ConfigLoader.logger().info("{} plugins is not loaded", registerer.getModId());
            return;
        }
        registerers.add(registerer);
        ConfigLoader.logger().info("{} plugins is loading", registerer.getModId());
    }

    public static void preInit() {
        register(new RegistererMinecraft());

        for (IModRegisterer registerer : registerers) {
            registerer.preInit();
        }
    }

    public static void initFirst() {
        for (IModRegisterer registerer : registerers) {
            registerer.initFirst();
        }
    }

    public static void initEnd() {
        for (IModRegisterer registerer : registerers) {
            registerer.initEnd();
        }
    }

    public static void postInit() {
        for (IModRegisterer registerer : registerers) {
            registerer.postInit();
        }
    }
}
