package net.sixeyes.vanillasprinkles.block;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class NetBlock extends AbstractNetBlock {

    public static final IntProperty SIZE = IntProperty.of("size",1,3); // multiplied by 3 for radius
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WET = BooleanProperty.of("wet");

    public NetBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(SIZE, 1).with(FACING, Direction.NORTH).with(WET, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIZE).add(FACING).add(WET);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            if (!state.get(WET)) {
                for (Direction direction : Direction.values()) {
                    if (world.getBlockState(pos.offset(direction, 1)).isOf(Blocks.WATER)) {
                        world.setBlockState(pos, state.with(WET, true));
                        return;
                    }
                }
            }

            if (state.get(WET)) {
                for (Direction direction : Direction.values()) {
                    if (world.getBlockState(pos.offset(direction, 1)).isOf(Blocks.SPONGE)) {
                        world.setBlockState(pos, state.with(WET, false));
                    }
                }
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            if (neighborState.getFluidState().isOf(Fluids.WATER) && !state.get(WET)) {
                return world.getBlockState(pos).with(WET, true);
            } else if (neighborState.isOf(Blocks.SPONGE) && state.get(WET)) {
                return world.getBlockState(pos).with(WET, false);
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
