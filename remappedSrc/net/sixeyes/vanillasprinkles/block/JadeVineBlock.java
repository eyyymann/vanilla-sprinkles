package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;

public class JadeVineBlock extends AbstractPlantStemBlock {
    public static final MapCodec<JadeVineBlock> CODEC = JadeVineBlock.createCodec(JadeVineBlock::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    private final double growthChance;

    public MapCodec<JadeVineBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(this.growthDirection.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        if (!this.canAttachTo(blockState)) {
            return false;
        }
        return blockState.isOf(this.getStem()) || blockState.isOf(this.getPlant()) || blockState.isSideSolidFullSquare(world, blockPos, this.growthDirection)
                || blockState.isIn(BlockTags.LEAVES);
    }

    public JadeVineBlock(AbstractBlock.Settings settings) {

        super(settings, Direction.DOWN, SHAPE, false, 0.1);
        this.growthChance = 0.1;
    }

    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.JADE_VINE_PLANT;
    }

    protected boolean chooseStemState(BlockState state) {
        return state.isAir();
    }

    @Override
    public BlockState getRandomGrowthState(WorldAccess world) {
        return (BlockState)this.getDefaultState().with(AGE, world.getRandom().nextInt(3));
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos;
        if (state.get(AGE) < 3 && random.nextDouble() < this.growthChance && this.chooseStemState(world.getBlockState(blockPos = pos.offset(this.growthDirection)))) {
            world.setBlockState(blockPos, this.age(state, world.random));
        }
    }

    public BlockState withMaxAge(BlockState state) {
        return (BlockState)state.with(AGE, 3);
    }

    public boolean hasMaxAge(BlockState state) {
        return state.get(AGE) == 3;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        if (direction == this.growthDirection && (neighborState.isOf(this) || neighborState.isOf(this.getPlant()))) {
            return this.copyState(state, this.getPlant().getDefaultState());
        }
        if (this.tickWater) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return this.chooseStemState(world.getBlockState(pos.offset(this.growthDirection)));
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.offset(this.growthDirection);
        int i = Math.min(state.get(AGE) + 1, 3);
        int j = this.getGrowthLength(random);
        for (int k = 0; k < j && this.chooseStemState(world.getBlockState(blockPos)); ++k) {
            world.setBlockState(blockPos, (BlockState)state.with(AGE, i));
            blockPos = blockPos.offset(this.growthDirection);
            i = Math.min(i + 1, 3);
        }
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) this;
    }
}
