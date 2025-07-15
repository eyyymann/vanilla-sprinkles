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

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        // BEHAVIOUR GROUPS
        getOrCreateTagBuilder(BlockTags.DEAD_BUSH_MAY_PLACE_ON)
                .add(ModBlocks.EXPOSED_SANDSTONE);

        getOrCreateTagBuilder(BlockTags.LOGS)
                .add(ModBlocks.BAOBAB_LOG);

        getOrCreateTagBuilder(BlockTags.CLIMBABLE)
                .add(ModBlocks.JADE_VINE)
                .add(ModBlocks.JADE_VINE_PLANT);

        // BLOCKSET GROUPS
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.DRIPSTONE_WALL)
                .add(ModBlocks.POLISHED_DRIPSTONE_WALL)
                .add(ModBlocks.DRIPSTONE_BRICK_WALL)
                .add(ModBlocks.DRIPSTONE_TILE_WALL)
                .add(ModBlocks.LAPIS_WALL)
                .add(ModBlocks.LAPIS_BRICK_WALL);

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_ZEBRA_PLANT)
                .add(ModBlocks.POTTED_MORNING_GLORY);

        getOrCreateTagBuilder(BlockTags.FLOWERS)
                .add(ModBlocks.PASSION_FLOWER);

        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.ZEBRA_PLANT)
                .add(ModBlocks.MORNING_GLORY);


        // TOOLTYPE GROUPS
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.COMMANDER)
                .add(ModBlocks.BOULDER)
                .add(ModBlocks.DRIPSTONE_SLAB)
                .add(ModBlocks.DRIPSTONE_STAIRS)
                .add(ModBlocks.DRIPSTONE_WALL)
                .add(ModBlocks.POLISHED_DRIPSTONE)
                .add(ModBlocks.POLISHED_DRIPSTONE_SLAB)
                .add(ModBlocks.POLISHED_DRIPSTONE_STAIRS)
                .add(ModBlocks.POLISHED_DRIPSTONE_WALL)
                .add(ModBlocks.DRIPSTONE_BRICKS)
                .add(ModBlocks.DRIPSTONE_BRICK_SLAB)
                .add(ModBlocks.DRIPSTONE_BRICK_STAIRS)
                .add(ModBlocks.DRIPSTONE_BRICK_WALL)
                .add(ModBlocks.CHISELED_DRIPSTONE)
                .add(ModBlocks.DRIPSTONE_TILES)
                .add(ModBlocks.DRIPSTONE_TILE_SLAB)
                .add(ModBlocks.DRIPSTONE_TILE_STAIRS)
                .add(ModBlocks.DRIPSTONE_TILE_WALL)
                .add(ModBlocks.TRAPPED_BRICKS)
                .add(ModBlocks.EXPOSED_SANDSTONE)
                .add(ModBlocks.EXPOSED_SMOOTH_SANDSTONE)
                .add(ModBlocks.CHISELED_EXPOSED_SANDSTONE)
                .add(ModBlocks.CUT_EXPOSED_SANDSTONE)
                .add(ModBlocks.LAPIS_SLAB)
                .add(ModBlocks.LAPIS_STAIRS)
                .add(ModBlocks.LAPIS_WALL)
                .add(ModBlocks.LAPIS_BRICKS)
                .add(ModBlocks.LAPIS_BRICK_SLAB)
                .add(ModBlocks.LAPIS_BRICK_STAIRS)
                .add(ModBlocks.LAPIS_BRICK_WALL)
                .add(ModBlocks.LAPIS_COLUMN)
                .add(ModBlocks.GILDED_LAPIS_BLOCK)
                .add(ModBlocks.GILDED_LAPIS_BRICKS)
                .add(ModBlocks.SAND_WELL);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.LOOSE_SAND)
                .add(ModBlocks.TERMITE_MOUND);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.JUNGLE_ROOTS)
                .add(ModBlocks.BAOBAB_LOG)
                .add(ModBlocks.BAOBAB_WOOD);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(ModBlocks.LOBSTER_CLAW)
                .add(ModBlocks.LOBSTER_CLAW_PLANT)
                .add(ModBlocks.PASSION_FLOWER)
                .add(ModBlocks.JADE_VINE)
                .add(ModBlocks.JADE_VINE_PLANT)
                .add(ModBlocks.ZEBRA_PLANT)
                .add(ModBlocks.MORNING_GLORY)
                .add(ModBlocks.DESERT_THORN)
                .add(ModBlocks.THICK_GRASS);

        // TOOLTIER GROUPS
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.COMMANDER)
                .add(ModBlocks.BOULDER)
                .add(ModBlocks.DRIPSTONE_SLAB)
                .add(ModBlocks.DRIPSTONE_STAIRS)
                .add(ModBlocks.DRIPSTONE_WALL)
                .add(ModBlocks.POLISHED_DRIPSTONE)
                .add(ModBlocks.POLISHED_DRIPSTONE_SLAB)
                .add(ModBlocks.POLISHED_DRIPSTONE_STAIRS)
                .add(ModBlocks.POLISHED_DRIPSTONE_WALL)
                .add(ModBlocks.DRIPSTONE_BRICKS)
                .add(ModBlocks.DRIPSTONE_BRICK_SLAB)
                .add(ModBlocks.DRIPSTONE_BRICK_STAIRS)
                .add(ModBlocks.DRIPSTONE_BRICK_WALL)
                .add(ModBlocks.CHISELED_DRIPSTONE)
                .add(ModBlocks.DRIPSTONE_TILES)
                .add(ModBlocks.DRIPSTONE_TILE_SLAB)
                .add(ModBlocks.DRIPSTONE_TILE_STAIRS)
                .add(ModBlocks.DRIPSTONE_TILE_WALL)
                .add(ModBlocks.TRAPPED_BRICKS)
                .add(ModBlocks.EXPOSED_SANDSTONE)
                .add(ModBlocks.EXPOSED_SMOOTH_SANDSTONE)
                .add(ModBlocks.CHISELED_EXPOSED_SANDSTONE)
                .add(ModBlocks.CUT_EXPOSED_SANDSTONE)
                .add(ModBlocks.LAPIS_SLAB)
                .add(ModBlocks.LAPIS_STAIRS)
                .add(ModBlocks.LAPIS_WALL)
                .add(ModBlocks.LAPIS_BRICKS)
                .add(ModBlocks.LAPIS_BRICK_SLAB)
                .add(ModBlocks.LAPIS_BRICK_STAIRS)
                .add(ModBlocks.LAPIS_BRICK_WALL)
                .add(ModBlocks.LAPIS_COLUMN)
                .add(ModBlocks.GILDED_LAPIS_BLOCK)
                .add(ModBlocks.GILDED_LAPIS_BRICKS)
                .add(ModBlocks.SAND_WELL);
    }
}
