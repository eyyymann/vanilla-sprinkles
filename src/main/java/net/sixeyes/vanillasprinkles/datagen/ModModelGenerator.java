package net.sixeyes.vanillasprinkles.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModItems;

public class ModModelGenerator extends FabricModelProvider {
    public ModModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.createLogTexturePool(ModBlocks.PINE_LOG).log(ModBlocks.PINE_LOG).wood(ModBlocks.PINE_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.STRIPPED_PINE_LOG).log(ModBlocks.STRIPPED_PINE_LOG).wood(ModBlocks.STRIPPED_PINE_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PINE_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PINE_LEAVES);
        blockStateModelGenerator.registerDoor(ModBlocks.PINE_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.PINE_TRAPDOOR);

        blockStateModelGenerator.registerTintableCross(ModBlocks.SHORT_SNOWY_GRASS, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.TALL_SNOWY_GRASS, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerFlowerPotPlantAndItem(ModBlocks.FIREWEED, ModBlocks.POTTED_FIREWEED, BlockStateModelGenerator.CrossType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RAKE, Models.HANDHELD);
    }
}
