package com.example.visualfx;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.BlockHitResult;

public class VisualFxClient implements ClientModInitializer {
    private int cooldown = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (cooldown > 0) cooldown--;
            if (cooldown != 0) return;

            if (client.player == null || client.level == null) return;

            if (client.hitResult instanceof BlockHitResult hit) {
                BlockPos pos = hit.getBlockPos().relative(hit.getDirection());
                var random = client.level.random;

                for (int i = 0; i < 3; i++) {
                    double x = pos.getX() + 0.2 + random.nextDouble() * 0.6;
                    double y = pos.getY() + 0.2 + random.nextDouble() * 0.6;
                    double z = pos.getZ() + 0.2 + random.nextDouble() * 0.6;
                    client.level.addParticle(
                        ParticleTypes.END_ROD,
                        x, y, z,
                        0.0, 0.015, 0.0
                    );
                }
                cooldown = 4;
            }
        });
    }
}
