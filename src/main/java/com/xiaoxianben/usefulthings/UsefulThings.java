package com.xiaoxianben.usefulthings;

import com.xiaoxianben.usefulthings.config.ConfigLoader;
import com.xiaoxianben.usefulthings.gui.GUIHandler;
import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.init.ModOre;
import com.xiaoxianben.usefulthings.init.ModRecipe;
import com.xiaoxianben.usefulthings.init.ModRegisterer;
import com.xiaoxianben.usefulthings.packet.GuiClientToSever;
import com.xiaoxianben.usefulthings.proxy.ProxyBase;
import com.xiaoxianben.usefulthings.tileEntity.TEAltar;
import com.xiaoxianben.usefulthings.tileEntity.auto.TEAutoMachine;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

@Mod(modid = ModInformation.MOD_ID,
        name = ModInformation.NAME,
        version = ModInformation.VERSION
)
public class UsefulThings {


    @Mod.Instance
    public static UsefulThings instance;

    @SidedProxy(clientSide = ModInformation.CLIENT_PROXY_CLASS, serverSide = ModInformation.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    public static Random randomer = new Random();


    private SimpleNetworkWrapper network;

    public UsefulThings() {
        FluidRegistry.enableUniversalBucket();
    }

    public static SimpleNetworkWrapper getNetwork() {
        return instance.network;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigLoader.preInitConfigLoader(event);

        ModRegisterer.preInit();
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        ModOre.init();

        ModRegisterer.initFirst();
        ModJsonRecipes.init();
        ModRecipe.initRecipes();
        ModRegisterer.initEnd();

        this.RegisterTileEntity();

        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.MOD_ID);
        network.registerMessage(new GuiClientToSever.Handler(), GuiClientToSever.class, 1, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(UsefulThings.instance, new GUIHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    private void RegisterTileEntity() {
        GameRegistry.registerTileEntity(TEAltar.class, new ResourceLocation(ModInformation.MOD_ID, "TEAltar"));
        GameRegistry.registerTileEntity(TEAutoMachine.class, new ResourceLocation(ModInformation.MOD_ID, "TEAutoMachine"));
    }

}