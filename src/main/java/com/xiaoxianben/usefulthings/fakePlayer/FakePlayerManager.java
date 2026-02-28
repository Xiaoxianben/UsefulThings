package com.xiaoxianben.usefulthings.fakePlayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class FakePlayerManager {

    public static WeakReference<FakePlayer> get(WorldServer world, int id) {
        String s = "UsefulThingsFakePlayer-"+id;
        GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes(s.getBytes()), s);
        return new WeakReference<>(FakePlayerFactory.get(world, gameProfile));
    }

    public static FakePlayer setPosition(FakePlayer player, BlockPos placePosition, BlockPos playerPosition) {
        final float deltaX = (float)(placePosition.getX() - playerPosition.getX());
        final float deltaY = (float)(placePosition.getY() - playerPosition.getY());
        final float deltaZ = (float)(placePosition.getZ() - playerPosition.getZ());

        float pitch = -(float)Math.toDegrees(Math.asin(deltaY));
        float yaw = -(float)Math.toDegrees(Math.atan2(deltaX, deltaZ));

        player.setPositionAndRotation(placePosition.getX(), placePosition.getY(), placePosition.getZ(), yaw, pitch);
        return player;
    }
}