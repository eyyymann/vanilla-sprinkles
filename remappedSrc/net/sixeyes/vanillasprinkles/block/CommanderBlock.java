package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import net.sixeyes.vanillasprinkles.registry.ModTags;

public class CommanderBlock extends FacingBlock {

    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty CAPTURED = BooleanProperty.of("captured");
    public static final MapCodec<CommanderBlock> CODEC = createCodec(CommanderBlock::new);
    public static final int RANGE = 16;

    public CommanderBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.SOUTH)).with(POWERED, false).with(CAPTURED, false));
    }

    @Override
    protected MapCodec<CommanderBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, CAPTURED);
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        uncaptureBoulders(state, (World) world, pos);
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                world.scheduleBlockTick(pos, this, 0);
            }
        }
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateCapturedState(state, world, pos);

        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.with(POWERED, false), 2);

            boolean bl = false;

            for (int i = 2; i <= RANGE; i++) {
                BlockPos offset_start = pos.offset(state.get(FACING),i);
                BlockPos offset_end = pos.offset(state.get(FACING), i-1);

                if (world.getBlockState(offset_start).isOf(ModBlocks.BOULDER) && world.getBlockState(offset_end).isIn(ModTags.BOULDER_PASSABLE)) {
                    bl = true;
                    break;
                }
            }

            if (!bl) {
                uncaptureBoulders(state, world, pos);
            }
        }

        if (!state.get(POWERED) && world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.with(POWERED, true), 2);

            for (int i = 2; i <= RANGE; i++) {
                BlockPos offset_start = pos.offset(state.get(FACING),i);
                BlockPos offset_end = pos.offset(state.get(FACING), i-1);

                if (world.getBlockState(offset_start).isOf(ModBlocks.BOULDER) && world.getBlockState(offset_end).isIn(ModTags.BOULDER_PASSABLE)) {
                    world.setBlockState(offset_start, Blocks.AIR.getDefaultState(), 2);
                    if (!world.getBlockState(offset_end).isIn(BlockTags.AIR))
                        world.breakBlock(offset_end, true);
                    world.setBlockState(offset_end, ModBlocks.BOULDER.getDefaultState().with(BoulderBlock.CAPTURED, true), 2);

                    if (world.getBlockState(offset_start.down()).isOpaque()
                        && state.get(FACING).getAxis().isHorizontal()) {
                        world.playSound(null, offset_end, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 0.5f, 0.8f);
                    }

                    break;
                }
            }
        }
    }

    protected void uncaptureBoulders(BlockState state, World world, BlockPos pos) {
        for (int i = 1; i <= RANGE; i++) {
            BlockPos offset = pos.offset(state.get(FACING),i);
            if (world.getBlockState(offset).isOf(ModBlocks.BOULDER) && world.getBlockState(offset).get(BoulderBlock.CAPTURED)) {
                world.setBlockState(offset, ModBlocks.BOULDER.getDefaultState().with(BoulderBlock.CAPTURED, false), 2);
            }
        }
    }

    protected void updateCapturedState(BlockState state, ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, state.with(CAPTURED,
                world.getBlockState(pos.offset(state.get(FACING),1)).isOf(ModBlocks.BOULDER) && state.get(POWERED)),
                NOTIFY_ALL);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(CAPTURED) ? 15 : 0;
    }
}
