package net.sixeyes.vanillasprinkles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Pair;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModItems;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModEnglishLangProvider extends FabricLanguageProvider {


    public ModEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public static String toTitle(String s) {
        return (!s.isEmpty()) ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : "";
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for (Item item : ModItems.ITEMS) {
            String[] array = item.getTranslationKey().split("\\.");
            String item_name = array[array.length - 1];
            String[] item_name_array = item_name.split("_");
            for (int i = 0; i < item_name_array.length; i++) {
                item_name_array[i] = toTitle(item_name_array[i]);
            }
            String item_name_corrected = String.join(" ", item_name_array);

            translationBuilder.add(item.getTranslationKey(), item_name_corrected);
        }

        for (Block block : ModBlocks.BLOCKS) {
            String[] array = block.getTranslationKey().split("\\.");
            String block_name = array[array.length - 1];
            String[] block_name_array = block_name.split("_");
            for (int i = 0; i < block_name_array.length; i++) {
                block_name_array[i] = toTitle(block_name_array[i]);
            }
            String block_name_corrected = String.join(" ", block_name_array);

            translationBuilder.add(block.getTranslationKey(), block_name_corrected);
        }

        translationBuilder.add("itemGroup.items", "Vanilla Sprinkles Items");
        translationBuilder.add("itemGroup.blocks", "Vanilla Sprinkles Blocks");
    }
}
