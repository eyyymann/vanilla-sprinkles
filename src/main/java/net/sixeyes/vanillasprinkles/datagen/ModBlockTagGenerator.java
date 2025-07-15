package net.sixeyes.vanillasprinkles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

//        getTagBuilder(BlockTags.LOGS).add(ModBlocks.PINE_LOG.getRegistryEntry());
//        getOrCreateTagBuilder(BlockTags.LOGS)
//                .add(ModBlocks.PINE_LOG);
//
//        getOrCreateTagBuilder(BlockTags.PLANKS)
//                .add(ModBlocks.PINE_PLANKS);
//
//        getOrCreateTagBuilder(BlockTags.LEAVES)
//                .add(ModBlocks.PINE_LEAVES);
//
//        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
//                .add(ModBlocks.PINE_LEAVES);
    }
}
