package net.sixeyes.vanillasprinkles.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.item.RakeItem;

public class ModItems {

    private static final String MOD_ID = VanillaSprinkles.MOD_ID;
    public static List<Item> ITEMS = new ArrayList<>();

    public static final Item RAKE = registerItem("rake", RakeItem::new,
            new Item.Settings().maxDamage(256));

    public static final RegistryKey<ItemGroup> ITEMS_GROUP_REGISTRY_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), Identifier.of(VanillaSprinkles.MOD_ID, "item_group"));
    public static final ItemGroup ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RAKE))
            .displayName(Text.translatable("itemGroup.items"))
            .build();

    // DESERT
    private static Item registerItem(String name, Item.Settings s) {
        Identifier id = Identifier.of(VanillaSprinkles.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = s.registryKey(key);

        Item item = Registry.register(Registries.ITEM, key, new Item(settings));
        ITEMS.add(item);
        return item;
    }

    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(VanillaSprinkles.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);
        ITEMS.add(item);

        return item;
    }

    public static void registerModItems() {

        VanillaSprinkles.LOGGER.info("Registering Mod Items for " + MOD_ID);
    }
}
