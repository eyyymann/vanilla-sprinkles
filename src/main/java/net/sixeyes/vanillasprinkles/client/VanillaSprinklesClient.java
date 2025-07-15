package net.sixeyes.vanillasprinkles.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;

public class VanillaSprinklesClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
                        0x3a7d31,
                ModBlocks.PINE_LEAVES);

//        BlockRenderLayerMap.putBlock(ModBlocks.PINE_LEAVES, RenderLayer.getCutout());
//        BlockRenderLayerMap.putBlock(ModBlocks.SHORT_SNOWY_GRASS, RenderLayer.getCutout());
//        BlockRenderLayerMap.putBlock(ModBlocks.TALL_SNOWY_GRASS, RenderLayer.getCutout());
//        BlockRenderLayerMap.putBlock(ModBlocks.ICICLE_PATCH, RenderLayer.getCutout());
//        BlockRenderLayerMap.putBlock(ModBlocks.FIREWEED, RenderLayer.getCutout());
//        BlockRenderLayerMap.putBlock(ModBlocks.POTTED_FIREWEED, RenderLayer.getCutout());
    }
}
