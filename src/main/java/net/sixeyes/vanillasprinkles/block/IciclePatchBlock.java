package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.function.BiFunction;

public class IciclePatchBlock extends Block {
    public static final MapCodec<IciclePatchBlock> CODEC = createCodec(IciclePatchBlock::new);
    public static final Property<Direction> FACING;
    public static final IntProperty ICICLE_AMOUNT;
    private static final BiFunction<Direction, Integer, VoxelShape> FACING_AND_AMOUNT_TO_SHAPE;

    public MapCodec<IciclePatchBlock> getCodec() {
        return CODEC;
    }

    public IciclePatchBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ICICLE_AMOUNT, 1));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction()
                && context.getStack().isOf(this.asItem())
                && state.get(ICICLE_AMOUNT) < 4 || super.canReplace(state, context);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FACING_AND_AMOUNT_TO_SHAPE.apply(state.get(FACING), state.get(ICICLE_AMOUNT));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return blockState.isOf(this)
                ? blockState.with(ICICLE_AMOUNT, Math.min(4, blockState.get(ICICLE_AMOUNT) + 1))
                : this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ICICLE_AMOUNT);
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
        ICICLE_AMOUNT = IntProperty.of("icicle_amount", 1, 4);;
        FACING_AND_AMOUNT_TO_SHAPE = Util.memoize((facing, icicleAmount) -> {
            VoxelShape[] voxelShapes = new VoxelShape[]{
                    Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
                    Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
                    Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
                    Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)};
            VoxelShape voxelShape = VoxelShapes.empty();

            for(int i = 0; i < icicleAmount; ++i) {
                int j = Math.floorMod(i - (facing.getAxis().isHorizontal() ? 1 : 0), 4);
                voxelShape = VoxelShapes.union(voxelShape, voxelShapes[j]);
            }

            return voxelShape.asCuboid();
        });
    }
}
