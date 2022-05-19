package io.github.asablock.client;

import io.github.asablock.MedicalKitMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClientMedicalKitMod implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        try {
            for (Field field : MedicalKitMod.class.getFields()) {
                if (field.isAnnotationPresent(ClientTransparent.class) &&
                        Modifier.isStatic(field.getModifiers()) && Block.class.isAssignableFrom(field.getType())) {
                    Block block = (Block) field.get(null);
                    LOGGER.info("Find client transparent block {}", Registry.BLOCK.getId(block));
                    BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), block);
                }
            }
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        }
    }
}
