package net.sixeyes.vanillasprinkles.datagen;

import com.mojang.serialization.DataResult;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.sixeyes.vanillasprinkles.block.DesertThornBlock;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTables extends FabricBlockLootTableProvider {
    public ModBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LOBSTER_CLAW_PLANT, drops(ModBlocks.LOBSTER_CLAW.asItem()));
        addDrop(ModBlocks.LOBSTER_CLAW, drops(ModBlocks.LOBSTER_CLAW.asItem()));
        addDrop(ModBlocks.PASSION_FLOWER, drops(ModBlocks.PASSION_FLOWER.asItem()));
        addDrop(ModBlocks.JUNGLE_ROOTS, drops(ModBlocks.JUNGLE_ROOTS.asItem()));
        addDrop(ModBlocks.JADE_VINE_PLANT, drops(ModBlocks.JADE_VINE.asItem()));
        addDrop(ModBlocks.JADE_VINE, drops(ModBlocks.JADE_VINE.asItem()));
        addDrop(ModBlocks.MORNING_GLORY, drops(ModBlocks.MORNING_GLORY.asItem()));
        addDrop(ModBlocks.POTTED_MORNING_GLORY, pottedPlantDrops(ModBlocks.MORNING_GLORY));
        addDrop(ModBlocks.ZEBRA_PLANT, drops(ModBlocks.ZEBRA_PLANT.asItem()));
        addDrop(ModBlocks.POTTED_ZEBRA_PLANT, pottedPlantDrops(ModBlocks.ZEBRA_PLANT));
        addDrop(ModBlocks.COMMANDER, drops(ModBlocks.COMMANDER.asItem()));
        addDrop(ModBlocks.BOULDER, drops(ModBlocks.BOULDER.asItem()));
        addDrop(ModBlocks.DRIPSTONE_SLAB, drops(ModBlocks.DRIPSTONE_SLAB.asItem()));
        addDrop(ModBlocks.DRIPSTONE_STAIRS, drops(ModBlocks.DRIPSTONE_STAIRS.asItem()));
        addDrop(ModBlocks.DRIPSTONE_WALL, drops(ModBlocks.DRIPSTONE_WALL.asItem()));
        addDrop(ModBlocks.POLISHED_DRIPSTONE, drops(ModBlocks.POLISHED_DRIPSTONE.asItem()));
        addDrop(ModBlocks.POLISHED_DRIPSTONE_SLAB, drops(ModBlocks.POLISHED_DRIPSTONE_SLAB.asItem()));
        addDrop(ModBlocks.POLISHED_DRIPSTONE_STAIRS, drops(ModBlocks.POLISHED_DRIPSTONE_STAIRS.asItem()));
        addDrop(ModBlocks.POLISHED_DRIPSTONE_WALL, drops(ModBlocks.POLISHED_DRIPSTONE_WALL.asItem()));
        addDrop(ModBlocks.DRIPSTONE_BRICKS, drops(ModBlocks.DRIPSTONE_BRICKS.asItem()));
        addDrop(ModBlocks.DRIPSTONE_BRICK_SLAB, drops(ModBlocks.DRIPSTONE_BRICK_SLAB.asItem()));
        addDrop(ModBlocks.DRIPSTONE_BRICK_STAIRS, drops(ModBlocks.DRIPSTONE_BRICK_STAIRS.asItem()));
        addDrop(ModBlocks.DRIPSTONE_BRICK_WALL, drops(ModBlocks.DRIPSTONE_BRICK_WALL.asItem()));
        addDrop(ModBlocks.CHISELED_DRIPSTONE, drops(ModBlocks.CHISELED_DRIPSTONE.asItem()));
        addDrop(ModBlocks.DRIPSTONE_TILES, drops(ModBlocks.DRIPSTONE_TILES.asItem()));
        addDrop(ModBlocks.DRIPSTONE_TILE_SLAB, drops(ModBlocks.DRIPSTONE_TILE_SLAB.asItem()));
        addDrop(ModBlocks.DRIPSTONE_TILE_STAIRS, drops(ModBlocks.DRIPSTONE_TILE_STAIRS.asItem()));
        addDrop(ModBlocks.DRIPSTONE_TILE_WALL, drops(ModBlocks.DRIPSTONE_TILE_WALL.asItem()));
        addDrop(ModBlocks.TRAPPED_BRICKS, drops(ModBlocks.TRAPPED_BRICKS.asItem()));
        addDrop(ModBlocks.EXPOSED_SANDSTONE, drops(ModBlocks.EXPOSED_SANDSTONE.asItem()));
        addDrop(ModBlocks.EXPOSED_SMOOTH_SANDSTONE, drops(ModBlocks.EXPOSED_SMOOTH_SANDSTONE.asItem()));
        addDrop(ModBlocks.CHISELED_EXPOSED_SANDSTONE, drops(ModBlocks.CHISELED_EXPOSED_SANDSTONE.asItem()));
        addDrop(ModBlocks.CUT_EXPOSED_SANDSTONE, drops(ModBlocks.CUT_EXPOSED_SANDSTONE.asItem()));
        addDrop(ModBlocks.SAND_WELL, drops(ModBlocks.SAND_WELL.asItem()));
        addDrop(ModBlocks.LOOSE_SAND, drops(ModBlocks.LOOSE_SAND.asItem()));
        addDrop(ModBlocks.DESERT_THORN, (block) -> this.applyExplosionDecay(block,
                LootTable.builder().pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.DESERT_THORN)
                                .properties(StatePredicate.Builder.create().exactMatch(DesertThornBlock.AGE, 2)))
                        .with(ItemEntry.builder(ModItems.DESERT_BERRIES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
                ).pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.DESERT_THORN)))));
        addDrop(ModBlocks.POTTED_DESERT_THORN, pottedPlantDrops(ModBlocks.DESERT_THORN));
        addDrop(ModBlocks.LAPIS_SLAB, drops(ModBlocks.LAPIS_SLAB.asItem()));
        addDrop(ModBlocks.LAPIS_STAIRS, drops(ModBlocks.LAPIS_STAIRS.asItem()));
        addDrop(ModBlocks.LAPIS_WALL, drops(ModBlocks.LAPIS_WALL.asItem()));
        addDrop(ModBlocks.LAPIS_BRICKS, drops(ModBlocks.LAPIS_BRICKS.asItem()));
        addDrop(ModBlocks.LAPIS_BRICK_SLAB, drops(ModBlocks.LAPIS_BRICK_SLAB.asItem()));
        addDrop(ModBlocks.LAPIS_BRICK_STAIRS, drops(ModBlocks.LAPIS_BRICK_STAIRS.asItem()));
        addDrop(ModBlocks.LAPIS_BRICK_WALL, drops(ModBlocks.LAPIS_BRICK_WALL.asItem()));
        addDrop(ModBlocks.LAPIS_COLUMN, drops(ModBlocks.LAPIS_COLUMN.asItem()));
        addDrop(ModBlocks.GILDED_LAPIS_BLOCK, drops(ModBlocks.GILDED_LAPIS_BLOCK.asItem()));
        addDrop(ModBlocks.GILDED_LAPIS_BRICKS, drops(ModBlocks.GILDED_LAPIS_BRICKS.asItem()));
        addDrop(ModBlocks.THICK_GRASS, dropsWithSilkTouch(ModBlocks.THICK_GRASS));
        addDrop(ModBlocks.BAOBAB_LOG, drops(ModBlocks.BAOBAB_LOG.asItem()));
        addDrop(ModBlocks.BAOBAB_WOOD, drops(ModBlocks.BAOBAB_WOOD.asItem()));
        addDrop(ModBlocks.TERMITE_MOUND, drops(ModBlocks.TERMITE_MOUND.asItem()));
    }
}
