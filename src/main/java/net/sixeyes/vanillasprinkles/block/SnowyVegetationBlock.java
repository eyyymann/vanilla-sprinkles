package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.screen.Property;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.sixeyes.vanillasprinkles.registry.ModTags;

public class SnowyVegetationBlock extends PlantBlock {
    public static final MapCodec<SnowyVegetationBlock> CODEC = createCodec(SnowyVegetationBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(12.0, 0.0, 13.0);
    private static final BooleanProperty SNOWLOGGED = BooleanProperty.of("snowlogged");

    public MapCodec<? extends SnowyVegetationBlock> getCodec() {
        return CODEC;
    }

    public SnowyVegetationBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.SNOWY_VEGETATION_MAY_PLACE_ON);
    }
}
