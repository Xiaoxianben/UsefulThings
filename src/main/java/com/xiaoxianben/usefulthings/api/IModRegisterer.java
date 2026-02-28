package com.xiaoxianben.usefulthings.api;

public interface IModRegisterer {
    void preInit();

    void initFirst();

    void initEnd();

    void postInit();

    String getModId();
}
