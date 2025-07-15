package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.block.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class ModBlocks {

    private static final String MOD_ID = VanillaSprinkles.MOD_ID;
    public static List<Pair<String, Block>> BLOCKS = new ArrayList<>();

    // JUNGLE
    public static final Block LOBSTER_CLAW = registerBlock("lobster_claw", new LobsterClawBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LOBSTER_CLAW_PLANT = registerBlockBare("lobster_claw_plant", new LobsterClawPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block PASSION_FLOWER = registerBlock("passion_flower", new GlowLichenBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
            .luminance(state -> 8).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block JUNGLE_ROOTS = registerBlock("jungle_roots", new JungleRootsBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_ROOTS)));
    public static final Block JADE_VINE = registerBlock("jade_vine", new JadeVineBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.CYAN).luminance(state -> 9).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block JADE_VINE_PLANT = registerBlockBare("jade_vine_plant", new JadeVinePlantBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.CYAN).luminance(state -> 8).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block ZEBRA_PLANT = registerBlock("zebra_plant",
            new FlowerBlock(StatusEffects.GLOWING, 5, AbstractBlock.Settings.copy(Blocks.CORNFLOWER)
                    .offset(AbstractBlock.OffsetType.XYZ)));
    public static final Block POTTED_ZEBRA_PLANT = registerBlockBare("potted_zebra_plant", new FlowerPotBlock(ModBlocks.ZEBRA_PLANT, AbstractBlock.Settings.copy(Blocks.POTTED_ALLIUM)));
    public static final Block MORNING_GLORY = registerBlock("morning_glory",
            new FlowerBlock(StatusEffects.GLOWING, 5, AbstractBlock.Settings.copy(Blocks.CORNFLOWER)
                    .offset(AbstractBlock.OffsetType.XYZ)));
    public static final Block POTTED_MORNING_GLORY = registerBlockBare("potted_morning_glory", new FlowerPotBlock(ModBlocks.MORNING_GLORY, AbstractBlock.Settings.copy(Blocks.POTTED_ALLIUM)));
    public static final Block COMMANDER = registerBlock("commander", new CommanderBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER)
            .luminance(state -> state.get(CommanderBlock.POWERED) ? 5 : 0)));
    public static final Block BOULDER = registerBlock("boulder", new BoulderBlock(AbstractBlock.Settings.copy(Blocks.ANDESITE)));

    public static final Block DRIPSTONE_SLAB = registerBlock("dripstone_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(Blocks.DRIPSTONE_BLOCK)));
    public static final Block DRIPSTONE_STAIRS = registerBlock("dripstone_stairs", new StairsBlock(Blocks.DRIPSTONE_BLOCK.getDefaultState(), AbstractBlock.Settings.copyShallow(Blocks.DRIPSTONE_BLOCK)));
    public static final Block DRIPSTONE_WALL = registerBlock("dripstone_wall", new WallBlock(AbstractBlock.Settings.copyShallow(Blocks.DRIPSTONE_BLOCK).solid()));
    public static final Block POLISHED_DRIPSTONE = registerBlock("polished_dripstone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF)));
    public static final Block POLISHED_DRIPSTONE_SLAB = registerBlock("polished_dripstone_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(POLISHED_DRIPSTONE)));
    public static final Block POLISHED_DRIPSTONE_STAIRS = registerBlock("polished_dripstone_stairs", new StairsBlock(POLISHED_DRIPSTONE.getDefaultState(), AbstractBlock.Settings.copyShallow(POLISHED_DRIPSTONE)));
    public static final Block POLISHED_DRIPSTONE_WALL = registerBlock("polished_dripstone_wall", new WallBlock(AbstractBlock.Settings.copyShallow(POLISHED_DRIPSTONE).solid()));
    public static final Block DRIPSTONE_BRICKS = registerBlock("dripstone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.TUFF_BRICKS)));
    public static final Block DRIPSTONE_BRICK_SLAB = registerBlock("dripstone_brick_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(DRIPSTONE_BRICKS)));
    public static final Block DRIPSTONE_BRICK_STAIRS = registerBlock("dripstone_brick_stairs", new StairsBlock(DRIPSTONE_BRICKS.getDefaultState(), AbstractBlock.Settings.copyShallow(DRIPSTONE_BRICKS)));
    public static final Block DRIPSTONE_BRICK_WALL = registerBlock("dripstone_brick_wall", new WallBlock(AbstractBlock.Settings.copyShallow(DRIPSTONE_BRICKS).solid()));
    public static final Block CHISELED_DRIPSTONE = registerBlock("chiseled_dripstone", new Block(AbstractBlock.Settings.copy(POLISHED_DRIPSTONE)));
    public static final Block DRIPSTONE_TILES = registerBlock("dripstone_tiles", new Block(AbstractBlock.Settings.copy(POLISHED_DRIPSTONE)));
    public static final Block DRIPSTONE_TILE_SLAB = registerBlock("dripstone_tile_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(DRIPSTONE_TILES)));
    public static final Block DRIPSTONE_TILE_STAIRS = registerBlock("dripstone_tile_stairs", new StairsBlock(DRIPSTONE_TILES.getDefaultState(), AbstractBlock.Settings.copyShallow(DRIPSTONE_BRICKS)));
    public static final Block DRIPSTONE_TILE_WALL = registerBlock("dripstone_tile_wall", new WallBlock(AbstractBlock.Settings.copyShallow(DRIPSTONE_TILES).solid()));
    public static final Block TRAPPED_BRICKS = registerBlock("trapped_bricks", new TrappedBricksBlock(AbstractBlock.Settings.copy(DRIPSTONE_BRICKS)));

    // DESERT
    public static final Block EXPOSED_SANDSTONE = registerBlock("exposed_sandstone", new Block(AbstractBlock.Settings.copy(Blocks.SANDSTONE)));
    public static final Block EXPOSED_SMOOTH_SANDSTONE = registerBlock("exposed_smooth_sandstone", new Block(AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block CHISELED_EXPOSED_SANDSTONE = registerBlock("chiseled_exposed_sandstone", new Block(AbstractBlock.Settings.copy(EXPOSED_SANDSTONE)));
    public static final Block CUT_EXPOSED_SANDSTONE = registerBlock("cut_exposed_sandstone", new Block(AbstractBlock.Settings.copy(EXPOSED_SANDSTONE)));
    public static final Block SAND_WELL = registerBlock("sand_well", new SandWellBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));
    public static final Block LOOSE_SAND = registerBlock("loose_sand", new LooseSandBlock(AbstractBlock.Settings.copy(Blocks.SAND)));
    public static final Block DESERT_THORN = registerBlock("desert_thorn",
            new DesertThornBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)
                    .offset(AbstractBlock.OffsetType.XZ)));
    public static final Block POTTED_DESERT_THORN = registerBlockBare("potted_desert_thorn", new FlowerPotBlock(ModBlocks.DESERT_THORN, AbstractBlock.Settings.copy(Blocks.POTTED_DEAD_BUSH)));

    public static final Block LAPIS_SLAB = registerBlock("lapis_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(Blocks.LAPIS_BLOCK)));
    public static final Block LAPIS_STAIRS = registerBlock("lapis_stairs", new StairsBlock(Blocks.DRIPSTONE_BLOCK.getDefaultState(), AbstractBlock.Settings.copyShallow(Blocks.LAPIS_BLOCK)));
    public static final Block LAPIS_WALL = registerBlock("lapis_wall", new WallBlock(AbstractBlock.Settings.copyShallow(Blocks.LAPIS_BLOCK).solid()));
    public static final Block LAPIS_BRICKS = registerBlock("lapis_bricks", new Block(AbstractBlock.Settings.copy(ModBlocks.DRIPSTONE_BRICKS)));
    public static final Block LAPIS_BRICK_SLAB = registerBlock("lapis_brick_slab", new SlabBlock(AbstractBlock.Settings.copyShallow(LAPIS_BRICKS)));
    public static final Block LAPIS_BRICK_STAIRS = registerBlock("lapis_brick_stairs", new StairsBlock(LAPIS_BRICKS.getDefaultState(), AbstractBlock.Settings.copyShallow(LAPIS_BRICKS)));
    public static final Block LAPIS_BRICK_WALL = registerBlock("lapis_brick_wall", new WallBlock(AbstractBlock.Settings.copyShallow(LAPIS_BRICKS).solid()));
    public static final Block LAPIS_COLUMN = registerBlock("lapis_column", new Block(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK)));
    public static final Block GILDED_LAPIS_BLOCK = registerBlock("gilded_lapis_block", new Block(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK)));
    public static final Block GILDED_LAPIS_BRICKS = registerBlock("gilded_lapis_bricks", new Block(AbstractBlock.Settings.copy(LAPIS_BRICKS)));

    // SAVANNA
    public static final Block TERMITE_MOUND = registerBlock("termite_mound", new Block(AbstractBlock.Settings.copy(Blocks.CLAY)));
    public static final Block THICK_GRASS = registerBlock("thick_grass", new ThickGrassBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block BAOBAB_LOG = registerBlock("baobab_log", Blocks.createLogBlock(MapColor.TERRACOTTA_ORANGE, MapColor.LIGHT_GRAY));
    public static final Block BAOBAB_WOOD = registerBlock("baobab_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
    public static final Block BAOBAB_PLANKS = registerBlock("baobab_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    // METHODS
    private static Block registerBlockBare(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }

    private static Block registerBlockNoCreative(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        BLOCKS.add(new Pair<>(name, block));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {

        VanillaSprinkles.LOGGER.info("Registering Mod Blocks " + MOD_ID);
    }
}
