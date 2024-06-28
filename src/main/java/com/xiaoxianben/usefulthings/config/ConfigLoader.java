package com.xiaoxianben.usefulthings.config;

import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Arrays;

public class ConfigLoader {

    private static Configuration config;

    private static Logger logger;


    public static void preInitConfigLoader(@Nonnull FMLPreInitializationEvent event) {
        logger = event.getModLog();

        ConfigValue.modConfigurationParentDirectory = event.getModConfigurationDirectory().getAbsolutePath();
        ConfigValue.modConfigurationDirectory = ConfigValue.modConfigurationParentDirectory + "/" + ModInformation.MOD_ID + "/";

        config = new Configuration(new File(ConfigValue.modConfigurationDirectory + ModInformation.MOD_ID + ".cfg"));

        //实例化了一个Configuration类,括号中填的是Forge推荐的配置文件位置,这个位置在游戏根目录的config文件夹下，
        //名为<modid>.cfg，这里就是bm.cfg。

        config.load();//读取配置
        load();
    }


    public static String[] getStringList(String key, String[] defaultValue) {
        return config.getStringList(key, Configuration.CATEGORY_GENERAL, defaultValue, I18n.format("config.ut-" + key + ".comment"));
    }

    public static void load() {
        logger.info("Started loading config.");

        //读取配置文件
        String[] blackListStr = getStringList("blackList", new String[]{});
        ConfigValue.blackList.add(ModBlocks.BLOCK_TIME_WARP);
        Arrays.stream(blackListStr).filter(s -> !s.isEmpty() || Block.getBlockFromName(s) != null).forEach(s -> ConfigValue.blackList.add(Block.getBlockFromName(s)));

        config.save(); //保存配置
        //至于为什么要保存配置呢？这是因为当配置缺失（最常见的原因就是配置文件没有创建，
        //这常常发生在你第一次使用Mod的时候）的时候，这一句会将默认的配置保存下来。
        logger.info("Finished loading config."); //输出完成加载配置文件
    }

    public static Logger logger() {
        return logger;
    }
}
