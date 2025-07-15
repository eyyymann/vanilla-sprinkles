package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;

public class TallSnowyGrassBlock extends SnowyVegetationBlock implements Fertilizable {
    public static final MapCodec<TallSnowyGrassBlock> CODEC = createCodec(TallSnowyGrassBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(12.0, 0.0, 10.0);

    public MapCodec<TallSnowyGrassBlock> getCodec() {
        return CODEC;
    }

    public TallSnowyGrassBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Fertilizable.findPosToSpreadTo(world, pos, ModBlocks.SHORT_SNOWY_GRASS.getDefaultState()).ifPresent((blockPos) -> {
            world.setBlockState(blockPos, ModBlocks.SHORT_SNOWY_GRASS.getDefaultState());
        });
    }
}
