package net.sixeyes.vanillasprinkles.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.sixeyes.vanillasprinkles.block.ThickGrassBlock;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;

public class ThickGrassFeature extends Feature<DefaultFeatureConfig> {
    public ThickGrassFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();

        BlockState blockState = ModBlocks.THICK_GRASS.getDefaultState();

        if (blockState.canPlaceAt(structureWorldAccess, blockPos)) {
            if (structureWorldAccess.getBlockState(blockPos.down()).isIn(BlockTags.DIRT) && structureWorldAccess.isAir(blockPos) && structureWorldAccess.isAir(blockPos.up()) && structureWorldAccess.isAir(blockPos.up(2))) {
                ThickGrassBlock.placeAt(structureWorldAccess, blockState, blockPos, 2);
                return true;
            }
        }
        return false;
    }
}
