package net.sixeyes.vanillasprinkles.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.block.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModBlocks {

    private static final String MOD_ID = VanillaSprinkles.MOD_ID;
    public static List<Block> BLOCKS = new ArrayList<>();

    // TAIGA
    public static final Block PINE_LOG = registerClassBlock("pine_log", PillarBlock::new, Blocks.createLogSettings(MapColor.TERRACOTTA_ORANGE, MapColor.LIGHT_GRAY, BlockSoundGroup.WOOD));
    public static final Block PINE_WOOD = registerClassBlock("pine_wood", PillarBlock::new, Blocks.createLogSettings(MapColor.TERRACOTTA_ORANGE, MapColor.LIGHT_GRAY, BlockSoundGroup.WOOD));
    public static final Block PINE_PLANKS = registerClasslessBlock("pine_planks", AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block PINE_LEAVES = registerClassBlock("pine_leaves", (settings) -> new TintedParticleLeavesBlock(0.01F, settings), Blocks.createLeavesSettings(BlockSoundGroup.AZALEA_LEAVES));
    public static final Block STRIPPED_PINE_LOG = registerClassBlock("stripped_pine_log", PillarBlock::new, Blocks.createLogSettings(MapColor.TERRACOTTA_ORANGE, MapColor.LIGHT_GRAY, BlockSoundGroup.WOOD));
    public static final Block STRIPPED_PINE_WOOD = registerClassBlock("stripped_pine_wood", PillarBlock::new, Blocks.createLogSettings(MapColor.TERRACOTTA_ORANGE, MapColor.LIGHT_GRAY, BlockSoundGroup.WOOD));
    public static final Block PINE_DOOR = registerClassBlock("pine_door", (settings) -> new DoorBlock(BlockSetType.PALE_OAK, settings), AbstractBlock.Settings.create().mapColor(PINE_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block PINE_TRAPDOOR = registerClassBlock("pine_trapdoor", (settings) -> new TrapdoorBlock(BlockSetType.PALE_OAK, settings), AbstractBlock.Settings.create().mapColor(PINE_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable());

    // SNOWY BIOMES
    public static final Block SHORT_SNOWY_GRASS = registerClassBlock("short_snowy_grass", ShortSnowyGrassBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).burnable().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block TALL_SNOWY_GRASS = registerClassBlock("tall_snowy_grass", TallSnowyGrassBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).burnable().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block ICICLE_PATCH = registerClassBlock("icicle_patch", IciclePatchBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.CYAN).noCollision().sounds(BlockSoundGroup.GLASS).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block FIREWEED = registerClassBlock("fireweed", (settings) -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5.0F, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PINK).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block POTTED_FIREWEED = registerClassBlockNoItem("potted_fireweed", (settings) -> new FlowerPotBlock(FIREWEED, settings), Blocks.createFlowerPotSettings());

    // SETTING UP
    public static final RegistryKey<ItemGroup> BLOCKS_GROUP_REGISTRY_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), Identifier.of(VanillaSprinkles.MOD_ID, "item_group"));
    public static final ItemGroup BLOCKS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(PINE_LOG.asItem()))
            .displayName(Text.translatable("itemGroup.blocks"))
            .build();

    // METHODS
    private static Block registerClassBlockNoItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Identifier id = Identifier.of(VanillaSprinkles.MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block block = factory.apply(settings.registryKey(key));

        Block registeredBlock = Registry.register(Registries.BLOCK, key, block);
        BLOCKS.add(registeredBlock);
        return registeredBlock;
    }

    private static Block registerClasslessBlock(String name, AbstractBlock.Settings settings) {
        Identifier id = Identifier.of(VanillaSprinkles.MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        Function<AbstractBlock.Settings, Block> factory = Block::new;
        Block block = factory.apply(settings.registryKey(key));
        registerBlockItem(name, block);

        Block registeredBlock = Registry.register(Registries.BLOCK, key, block);
        BLOCKS.add(registeredBlock);
        return registeredBlock;
    }

    private static Block registerClassBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Identifier id = Identifier.of(VanillaSprinkles.MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block block = factory.apply(settings.registryKey(key));
        registerBlockItem(name, block);

        Block registeredBlock = Registry.register(Registries.BLOCK, key, block);
        BLOCKS.add(registeredBlock);
        return registeredBlock;
    }

    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(VanillaSprinkles.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = new Item.Settings().useBlockPrefixedTranslationKey().registryKey(key);
        return Registry.register(Registries.ITEM, key, new BlockItem(block, settings));
    }

    public static void registerBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> {
            itemGroup.add(PINE_LOG.asItem());
            itemGroup.add(PINE_WOOD.asItem());
            itemGroup.add(PINE_PLANKS.asItem());
            itemGroup.add(PINE_LEAVES.asItem());
            itemGroup.add(STRIPPED_PINE_LOG.asItem());
            itemGroup.add(STRIPPED_PINE_WOOD.asItem());
            itemGroup.add(PINE_DOOR.asItem());
            itemGroup.add(PINE_TRAPDOOR.asItem());
            itemGroup.add(SHORT_SNOWY_GRASS.asItem());
            itemGroup.add(TALL_SNOWY_GRASS.asItem());
            itemGroup.add(ICICLE_PATCH.asItem());
            itemGroup.add(FIREWEED.asItem());
        });

        VanillaSprinkles.LOGGER.info("Registering Mod Blocks " + MOD_ID);
    }
}
