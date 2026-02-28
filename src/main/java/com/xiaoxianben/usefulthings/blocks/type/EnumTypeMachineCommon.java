package com.xiaoxianben.usefulthings.blocks.type;

public enum EnumTypeMachineCommon {
    /** 自动工作台 */
    AutoCraft(0),
    /** 自动合金 */
    AutoMetallurgy(1),
    /** 自动熔炉 */
    AutoFurnace(2),
    /** 自动磨粉 */
    AutoPulverizer(3),
    /** 自动造石 */
    AutoExtruder(4),
    /** 自动熔炼 */
    AutoCrucible(5),
    /** 自动充电 */
    AutoCharger(6),
    /** 自动破矿 */
    AutoBrokenMiner(7),
    /** 自动压缩 */
    AutoDecompress(8),
    /** 自动解压 */
    AutoCompress(9),
    /** 自动化学 */
    AutoChemical(10),
    /** 自动电解 */
    AutoElectrolysis(11),
    /** 自动清洗 */
    AutoWashes(12),
    /** 自动放置方块 */
    AutoBlockPlacer(13),
    /** 自动方块点击 */
    AutoBlockClicker(14),
    /** 自动流体灌注 */
    AutoInfusion(15),
    ;


    public final int id;

    EnumTypeMachineCommon(int id) {
        this.id = id;
    }

    public static EnumTypeMachineCommon getType(int id) {
        for (EnumTypeMachineCommon type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}