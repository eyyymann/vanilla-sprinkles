package net.sixeyes.vanillasprinkles.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sixeyes.vanillasprinkles.block.entity.SandWellBlockEntity;
import net.sixeyes.vanillasprinkles.registry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class SandWellBlock extends BlockWithEntity {

    public static final IntProperty SAND = IntProperty.of("sand", 1, 3);
    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");
    public static final int MAX_SAND_AMOUNT = 3;

    public SandWellBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(SAND, 1).with(CLOSED, true)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SAND, CLOSED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SandWellBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.SAND_WELL_BLOCK_ENTITY, SandWellBlockEntity::tick);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(SandWellBlock.CLOSED) && state.get(SandWellBlock.SAND) < MAX_SAND_AMOUNT) {
            for (int i = 0; i < 10; i++) {
                world.addParticleClient(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()),
                        pos.toCenterPos().getX() + random.nextBetween(-30, 30)/100f,
                        pos.toCenterPos().getY() + 5 + random.nextBetween(-10,10)/100f,
                        pos.toCenterPos().getZ() + random.nextBetween(-30, 30)/100f,
                        0, -0.1 - random.nextBetween(1,10)/100f, 0);
            }
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!state.get(SandWellBlock.CLOSED)) {
            if (state.get(SandWellBlock.SAND) > 1) {
                world.setBlockState(pos, state.with(SandWellBlock.SAND, 1), 2);
                world.playSound(null, pos, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.BLOCKS, 1.0F, 1.1F);
                ItemStack sand = Items.SAND.getDefaultStack();
                sand.setCount((int) (Math.round(Math.pow(2,state.get(SandWellBlock.SAND))) + world.random.nextBetween(-1,1)));
                player.giveItemStack(sand);
                return ActionResult.SUCCESS_NO_ITEM_USED;
            }
        } else {
            world.playSound(null, pos, SoundEvents.BLOCK_VAULT_REJECT_REWARDED_PLAYER, SoundCategory.BLOCKS, 1.0F, 0.9F);
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}
