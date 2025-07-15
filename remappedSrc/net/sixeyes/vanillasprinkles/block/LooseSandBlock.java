package net.sixeyes.vanillasprinkles.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;


public class LooseSandBlock extends Block {

    public static final BooleanProperty UNSTABLE = BooleanProperty.of("unstable");

    public LooseSandBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(UNSTABLE, false)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, state.getBlock(), 2);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        implode(world, pos, state);
    }

    public void implode(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), NOTIFY_LISTENERS);
        for (int i = 0; i < 10; i++) {
            world.addParticleClient(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()),
                    pos.toCenterPos().getX() + world.random.nextBetween(-25, 25)/100f,
                    pos.toCenterPos().getY() + 0.5,
                    pos.toCenterPos().getZ() + world.random.nextBetween(-25, 25)/100f,
                    0, 0.05*i + world.random.nextBetween(1,10)/100f, 0);
        }
    }
}
