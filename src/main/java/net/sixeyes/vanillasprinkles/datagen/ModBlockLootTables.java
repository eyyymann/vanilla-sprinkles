package net.sixeyes.vanillasprinkles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTables extends FabricBlockLootTableProvider {
    public ModBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PINE_LOG, drops(ModBlocks.PINE_LOG.asItem()));
        addDrop(ModBlocks.PINE_WOOD, drops(ModBlocks.PINE_WOOD.asItem()));
        addDrop(ModBlocks.PINE_PLANKS, drops(ModBlocks.PINE_PLANKS.asItem()));
    }
}
