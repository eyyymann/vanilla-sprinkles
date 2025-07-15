package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ThickGrassBlock extends PlantBlock {
    public static final MapCodec<ThickGrassBlock> CODEC = createCodec(ThickGrassBlock::new);
    public static final IntProperty PART = IntProperty.of("part", 1, 3);

    public MapCodec<? extends ThickGrassBlock> getCodec() {
        return CODEC;
    }

    public ThickGrassBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, 1));
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int part = state.get(PART);
        if (direction == Direction.UP) {
            if (part < 3 && !neighborState.isOf(this)) {
                return Blocks.AIR.getDefaultState();
            }
        } else if (direction == Direction.DOWN) {
            if (!neighborState.isOf(this)) {
                return Blocks.AIR.getDefaultState();
            }
        }

        return state;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();

        return blockPos.getY() < world.getTopYInclusive() - 2 && world.getBlockState(blockPos.up(2)).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, withWaterloggedState(world, pos.up(), this.getDefaultState().with(PART, 2)), 3);
        world.setBlockState(blockPos.up(), withWaterloggedState(world, blockPos.up(), this.getDefaultState().with(PART, 3)), 3);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(PART) == 1) {
            return super.canPlaceAt(state, world, pos);
        } else {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(PART) == 1;
        }
    }

    public static void placeAt(WorldAccess world, BlockState state, BlockPos pos, int flags) {
        BlockPos blockPos = pos.up();
        world.setBlockState(pos, withWaterloggedState(world, pos, state.with(PART, 1)), flags);
        world.setBlockState(blockPos, withWaterloggedState(world, blockPos, state.with(PART, 2)), flags);
        world.setBlockState(blockPos.up(), withWaterloggedState(world, blockPos.up(), state.with(PART, 3)), flags);
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        return state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, world.isWater(pos)) : state;
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                onBreakInCreative(world, pos, state, player);
            } else {
                dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int doubleBlockHalf = state.get(PART);
        if (doubleBlockHalf > 1) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(state.getBlock()) && blockState.get(PART) == 1) {
                BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos, blockState2, 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    protected long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(PART) < 2 ? 0 : 1).getY(), pos.getZ());
    }
}
