package com.xiaoxianben.usefulthings;

import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.proxy.ProxyBase;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInformation.MOD_ID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class UsefulThings {

    public static Logger logger;

    @Mod.Instance
    public static UsefulThings instance;

    @SidedProxy(clientSide = ModInformation.CLIENT_PROXY_CLASS, serverSide = ModInformation.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.initItem();
        ModBlocks.initBlock();
        logger = event.getModLog();
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        ModItems.initItemRecipe();
        ModBlocks.initBlockRecipe();
        this.RegisterTileEntity();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    private void RegisterTileEntity() {
    }

}