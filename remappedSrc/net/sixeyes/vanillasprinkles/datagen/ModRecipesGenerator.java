package net.sixeyes.vanillasprinkles.datagen;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
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
    public void generate(RecipeExporter exporter) {

                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SAND_WELL)
                .pattern("ggg").pattern("l l").pattern("lll")
                .input('g', Items.GOLD_INGOT)
                .input('l', Blocks.LAPIS_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.GOLD_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.GOLD_INGOT))
                .criterion(FabricRecipeProvider.hasItem(Blocks.LAPIS_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Blocks.LAPIS_BLOCK))
                .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BAOBAB_PLANKS, 4)
                        .input(ModBlocks.BAOBAB_LOG)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.BAOBAB_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.BAOBAB_LOG))
                .offerTo(exporter);
    }
}
