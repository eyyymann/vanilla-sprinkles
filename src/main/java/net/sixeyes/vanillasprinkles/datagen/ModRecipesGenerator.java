package net.sixeyes.vanillasprinkles.datagen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Util;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesGenerator extends FabricRecipeProvider {
    public ModRecipesGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {

                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShaped(RecipeCategory.TOOLS, ModItems.RAKE, 1)
                        .pattern("  c")
                        .pattern(" s ")
                        .pattern("s  ")
                        .input('s', Items.STICK)
                        .input('c', ItemTags.STONE_CRAFTING_MATERIALS)
                        .group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
                        .criterion(hasItem(Items.STICK), conditionsFromItem(ModItems.RAKE))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "FabricDocsReferenceRecipeProvider";
    }
}
