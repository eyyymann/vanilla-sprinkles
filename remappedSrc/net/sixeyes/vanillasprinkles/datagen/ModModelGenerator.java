package net.sixeyes.vanillasprinkles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.data.client.*;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.block.SandWellBlock;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModItems;

public class ModModelGenerator extends FabricModelProvider {
    public ModModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    public final void registerBlockSubIdBottomAndTop(BlockStateModelGenerator b, Block base) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.BOTTOM, TextureMap.getSubId(base, "_bottom"))
                .put(TextureKey.TOP, TextureMap.getSubId(base, "_top"))
                .put(TextureKey.SIDE, TextureMap.getId(base));
        b.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(base, Models.CUBE_BOTTOM_TOP.upload(base, textureMap, b.modelCollector)));
    }

    public final void registerChiseledExposedSandstone(BlockStateModelGenerator b) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.EXPOSED_SMOOTH_SANDSTONE))
                .put(TextureKey.TOP, TextureMap.getId(ModBlocks.EXPOSED_SMOOTH_SANDSTONE))
                .put(TextureKey.SIDE, TextureMap.getId(ModBlocks.CHISELED_EXPOSED_SANDSTONE));
        b.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(ModBlocks.CHISELED_EXPOSED_SANDSTONE, Models.CUBE_BOTTOM_TOP.upload(ModBlocks.CHISELED_EXPOSED_SANDSTONE, textureMap, b.modelCollector)));
    }

    public final void registerCutExposedSandstone(BlockStateModelGenerator b) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.EXPOSED_SMOOTH_SANDSTONE))
                .put(TextureKey.TOP, TextureMap.getId(ModBlocks.EXPOSED_SMOOTH_SANDSTONE))
                .put(TextureKey.SIDE, TextureMap.getId(ModBlocks.CUT_EXPOSED_SANDSTONE));
        b.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(ModBlocks.CUT_EXPOSED_SANDSTONE, Models.CUBE_BOTTOM_TOP.upload(ModBlocks.CUT_EXPOSED_SANDSTONE, textureMap, b.modelCollector)));
    }

    public final void registerBlockSubIdSide(BlockStateModelGenerator b, Block base) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.BOTTOM, TextureMap.getId(base))
                .put(TextureKey.TOP, TextureMap.getId(base))
                .put(TextureKey.SIDE, TextureMap.getSubId(base, "_side"));
        b.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(base, Models.CUBE_BOTTOM_TOP.upload(base, textureMap, b.modelCollector)));
    }

    private void registerSweetBerryBush(BlockStateModelGenerator b) {
        b.registerItemModel(ModItems.DESERT_BERRIES);
        b.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(ModBlocks.DESERT_THORN).coordinate(BlockStateVariantMap
                        .create(Properties.AGE_2).register((stage) -> BlockStateVariant
                                .create().put(VariantSettings.MODEL, b.createSubModel(ModBlocks.DESERT_THORN, String.valueOf(stage + 1), Models.CROSS, TextureMap::cross)))));
    }

    private void registerCommander(BlockStateModelGenerator b) {
        Identifier identifier = ModelIds.getBlockModelId(ModBlocks.COMMANDER);
        Identifier identifier2 = ModelIds.getBlockSubModelId(ModBlocks.COMMANDER, "_on");
        b.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.COMMANDER)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.POWERED, identifier2, identifier))
                .coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates()));
    }

    private void registerLapisColumn(BlockStateModelGenerator b) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.BOTTOM, TextureMap.getSubId(ModBlocks.LAPIS_COLUMN, "_top"))
                .put(TextureKey.TOP, TextureMap.getSubId(ModBlocks.LAPIS_COLUMN, "_top"))
                .put(TextureKey.SIDE, TextureMap.getId(ModBlocks.LAPIS_COLUMN));
        b.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(ModBlocks.LAPIS_COLUMN, Models.CUBE_BOTTOM_TOP.upload(ModBlocks.LAPIS_COLUMN, textureMap, b.modelCollector)));

    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerPlantPart(ModBlocks.LOBSTER_CLAW, ModBlocks.LOBSTER_CLAW_PLANT, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerItemModel(ModBlocks.LOBSTER_CLAW, "_plant");
        blockStateModelGenerator.excludeFromSimpleItemModelGeneration(ModBlocks.LOBSTER_CLAW_PLANT);

        blockStateModelGenerator.registerSimpleState(ModBlocks.JUNGLE_ROOTS);

        blockStateModelGenerator.registerPlantPart(ModBlocks.JADE_VINE, ModBlocks.JADE_VINE_PLANT, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerItemModel(ModBlocks.JADE_VINE, "_plant");
        blockStateModelGenerator.excludeFromSimpleItemModelGeneration(ModBlocks.JADE_VINE_PLANT);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.ZEBRA_PLANT, ModBlocks.POTTED_ZEBRA_PLANT, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MORNING_GLORY, ModBlocks.POTTED_MORNING_GLORY, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerParentedItemModel(ModItems.CHIMPANZEE_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));

        registerCommander(blockStateModelGenerator);
        registerBlockSubIdBottomAndTop(blockStateModelGenerator,ModBlocks.BOULDER);

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DRIPSTONE_BLOCK)
                .stairs(ModBlocks.DRIPSTONE_STAIRS)
                .slab(ModBlocks.DRIPSTONE_SLAB)
                .wall(ModBlocks.DRIPSTONE_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_DRIPSTONE)
                .stairs(ModBlocks.POLISHED_DRIPSTONE_STAIRS)
                .slab(ModBlocks.POLISHED_DRIPSTONE_SLAB)
                .wall(ModBlocks.POLISHED_DRIPSTONE_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DRIPSTONE_BRICKS)
                .stairs(ModBlocks.DRIPSTONE_BRICK_STAIRS)
                .slab(ModBlocks.DRIPSTONE_BRICK_SLAB)
                .wall(ModBlocks.DRIPSTONE_BRICK_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DRIPSTONE_TILES)
                .stairs(ModBlocks.DRIPSTONE_TILE_STAIRS)
                .slab(ModBlocks.DRIPSTONE_TILE_SLAB)
                .wall(ModBlocks.DRIPSTONE_TILE_WALL);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TRAPPED_BRICKS);
        registerBlockSubIdSide(blockStateModelGenerator, ModBlocks.CHISELED_DRIPSTONE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.EXPOSED_SANDSTONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.EXPOSED_SMOOTH_SANDSTONE);
        registerChiseledExposedSandstone(blockStateModelGenerator);
        registerCutExposedSandstone(blockStateModelGenerator);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LOOSE_SAND);
        registerSweetBerryBush(blockStateModelGenerator);
        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.LAPIS_BLOCK)
                .stairs(ModBlocks.LAPIS_STAIRS)
                .slab(ModBlocks.LAPIS_SLAB)
                .wall(ModBlocks.LAPIS_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LAPIS_BRICKS)
                .stairs(ModBlocks.LAPIS_BRICK_STAIRS)
                .slab(ModBlocks.LAPIS_BRICK_SLAB)
                .wall(ModBlocks.LAPIS_BRICK_WALL);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GILDED_LAPIS_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GILDED_LAPIS_BRICKS);
        registerLapisColumn(blockStateModelGenerator);

        blockStateModelGenerator.registerLog(ModBlocks.BAOBAB_LOG).log(ModBlocks.BAOBAB_LOG).wood(ModBlocks.BAOBAB_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BAOBAB_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TERMITE_MOUND);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BANANA, Models.GENERATED);
    }
}
