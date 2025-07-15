package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.datagen.ModBlockTagGenerator;
import net.sixeyes.vanillasprinkles.registry.ModTags;

public class BoulderBlock extends FallingBlock {
    public static final MapCodec<BoulderBlock> CODEC = createCodec(BoulderBlock::new);
    public static final BooleanProperty CAPTURED = BooleanProperty.of("captured");
    public static final BooleanProperty JUST_BROKE = BooleanProperty.of("just_broke");

    public BoulderBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(CAPTURED, false).with(JUST_BROKE, false));
    }

    @Override
    protected MapCodec<BoulderBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(CAPTURED)) {
            if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
                FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
                this.configureFallingBlockEntity(fallingBlockEntity);
            }
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(1.5F, 40);
        entity.setVelocity(0,0,0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CAPTURED, JUST_BROKE);
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockState.get(JUST_BROKE)) {
            if (world.getBlockState(pos.down()).isIn(ModTags.BOULDER_BREAKABLE)) {
                world.breakBlock(pos.down(), true);
                world.setBlockState(pos, fallingBlockState.with(JUST_BROKE, true), 2);
            }
            world.playSound(fallingBlockEntity, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundCategory.BLOCKS, 1f, 1f);
        } else {
            world.setBlockState(pos, fallingBlockState.with(JUST_BROKE, false), 2);
        }
    }
}
