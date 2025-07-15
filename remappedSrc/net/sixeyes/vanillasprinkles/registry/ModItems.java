package net.sixeyes.vanillasprinkles.registry;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.util.Rarity;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;

public class ModItems {

    private static final String MOD_ID = VanillaSprinkles.MOD_ID;
    public static List<Pair<String, Item>> ITEMS = new ArrayList<>();

    // FOOD COMPONENTS
    public static final FoodComponent DESERT_BERRIES_FOOD = (new FoodComponent.Builder()).nutrition(2).saturationModifier(0.1F).build();;

    // JUNGLE
    public static final Item BANANA = registerItem("banana",
        new Item(new Item.Settings().food(
                (new FoodComponent.Builder()).nutrition(4).saturationModifier(0.3F).build())));
    public static final Item BANANA_TREE_SAPLING = registerItem("banana_tree_sapling",
            new Item(new Item.Settings()));

    public static final Item CHIMPANZEE_SPAWN_EGG = registerItem("chimpanzee_spawn_egg",
            new SpawnEggItem(ModEntities.CHIMPANZEE, 0xD57E36, 0x1D0D00,
                    new Item.Settings()));

    // DESERT
    public static final Item DESERT_BERRIES = registerItem("desert_berries",
            (new AliasedBlockItem(ModBlocks.DESERT_THORN, (new Item.Settings()).food(DESERT_BERRIES_FOOD))));


    private static Item registerItem(String name, Item item) {
        ITEMS.add(new Pair<>(name, item));
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    public static void registerModItems() {
        VanillaSprinkles.LOGGER.info("Registering Mod Items for " + MOD_ID);
    }
}
