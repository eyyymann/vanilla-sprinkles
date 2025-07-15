package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Pair;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;

public class ModItemGroups {
    public static final ItemGroup ITEMS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(VanillaSprinkles.MOD_ID, "items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.items"))
                    .icon(() -> new ItemStack(ModItems.BANANA))
                    .entries((displayContext, entries) -> {
                        for (Pair<String, Item> pair : ModItems.ITEMS) {
                            entries.add(pair.getRight());
                        }
                    }).build()
    );

    public static final ItemGroup BLOCKS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(VanillaSprinkles.MOD_ID, "blocks"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.blocks"))
                    .icon(() -> new ItemStack(ModBlocks.LOBSTER_CLAW))
                    .entries((displayContext, entries) -> {
                        for (Pair<String, Block> pair : ModBlocks.BLOCKS) {
                            entries.add(pair.getRight());
                        }
                    }).build()
    );

    public static void registerItemGroups() {
        VanillaSprinkles.LOGGER.info("Registering Item Group for " + VanillaSprinkles.MOD_ID);
    }
}
