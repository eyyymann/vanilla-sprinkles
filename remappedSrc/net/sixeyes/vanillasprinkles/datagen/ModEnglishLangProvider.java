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

import java.util.concurrent.CompletableFuture;

public class ModEnglishLangProvider extends FabricLanguageProvider {


    public ModEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for (Pair<String, Item> pair : ModItems.ITEMS) {
            String[] strings = pair.getLeft().split("\\_", 0);
            for (int i = 0; i < strings.length; i++) {
                strings[i] = StringUtils.capitalize(strings[i]);
            }
            translationBuilder.add(pair.getRight(),
                    String.join(" ", strings));
        }

        for (Pair<String, Block> pair : ModBlocks.BLOCKS) {
            String[] strings = pair.getLeft().split("\\_", 0);
            for (int i = 0; i < strings.length; i++) {
                strings[i] = StringUtils.capitalize(strings[i]);
            }
            translationBuilder.add(pair.getRight(),
                    String.join(" ", strings));
        }

        translationBuilder.add("itemgroup.items", "Vanilla Sprinkles Items");
        translationBuilder.add("itemgroup.blocks", "Vanilla Sprinkles Blocks");
    }
}
