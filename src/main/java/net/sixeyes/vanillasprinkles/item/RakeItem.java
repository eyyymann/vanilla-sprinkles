package net.sixeyes.vanillasprinkles.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.particle.TintedParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;

import java.util.List;

public class RakeItem extends Item {
    public RakeItem(Item.Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos playerPos = context.getPlayer().getBlockPos();
        ItemStack stack = context.getStack();
        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);
        PlayerEntity playerEntity = context.getPlayer();

        VanillaSprinkles.LOGGER.info("Used rake on leaf litter at" + blockPos);

        if (blockState.isOf(Blocks.LEAF_LITTER)) {
            /*
            * If the player's position is the same as the target leaf litter
            * then it will check if the leaf litter has less than 3 segments
            * in which case it will rotate the shape by 90 degrees
            * the player. Otherwise the action will fail
            * */
            if (playerPos.equals(blockPos)) {
                int segmentCount = blockState.get(Properties.SEGMENT_AMOUNT);
                if (segmentCount < 3) {
                    world.playSound(context.getPlayer(), blockPos, SoundEvents.BLOCK_LEAF_LITTER_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(blockPos, blockState.rotate(BlockRotation.CLOCKWISE_90));
                    spawnLeafParticle(world, blockPos, false);
                    context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
                return ActionResult.FAIL;
            }

            int playerX = playerPos.getX();
            int playerY = playerPos.getY();
            int playerZ = playerPos.getZ();
            int blockX = blockPos.getX();
            int blockY = blockPos.getY();
            int blockZ = blockPos.getZ();

            if (playerX != blockX) {
                if (playerX < blockX)
                    blockX--;
                else
                    blockX++;
            }

            if (playerY != blockY) {
                if (playerY < blockY)
                    blockY--;
                else
                    blockY++;
            }

            if (playerZ != blockZ) {
                if (playerZ < blockZ)
                    blockZ--;
                else
                    blockZ++;
            }

            BlockPos replacedBlockPos = new BlockPos(blockX, blockY, blockZ);
            BlockState replacedBlockState = world.getBlockState(replacedBlockPos);

            if (replacedBlockState.isIn(BlockTags.AIR) && world.getBlockState(replacedBlockPos.down()).isOpaqueFullCube()) {
                moveLeafLitterToEmptyBlock(world, context, blockPos, replacedBlockPos, blockState, playerEntity);
                return ActionResult.SUCCESS;
            } else if (replacedBlockState.isOf(Blocks.LEAF_LITTER)) {
                int firstSegmentAmount = blockState.get(Properties.SEGMENT_AMOUNT);
                int secondSegmentAmount = replacedBlockState.get(Properties.SEGMENT_AMOUNT);

                if (firstSegmentAmount + secondSegmentAmount <= Segmented.MAX_SEGMENTS) {
                    moveLeafLitterToPileCompletely(world, context, blockPos, replacedBlockPos, blockState, playerEntity, firstSegmentAmount, secondSegmentAmount);
                    return ActionResult.SUCCESS;
                } else if (firstSegmentAmount + secondSegmentAmount > Segmented.MAX_SEGMENTS && secondSegmentAmount < Segmented.MAX_SEGMENTS) {
                    moveLeafLitterToPilePartially(world, context, blockPos, replacedBlockPos, blockState, playerEntity, firstSegmentAmount, secondSegmentAmount);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            }
            else {
                return ActionResult.FAIL;
            }
        } else {
            return ActionResult.FAIL;
        }
    }

    /*
     * If the player's position has an air block that is directly supported by an opaque block under it,
     * then the targeted leaf litter will be pulled to the player's position.
     * */
    private void moveLeafLitterToEmptyBlock(World world, ItemUsageContext context, BlockPos blockPos, BlockPos replacedBlockPos, BlockState blockState, PlayerEntity playerEntity) {
        world.playSound(context.getPlayer(), blockPos, SoundEvents.BLOCK_LEAF_LITTER_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        world.setBlockState(replacedBlockPos, blockState);

        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), blockState));
        spawnLeafParticle(world, replacedBlockPos, true);
        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
    }

    /*
     * If the amount of segments in the targeted and player's position leaf litter does not exceed
     * the maximum amount per block, then the targeted amount will be added to the player's position.
     * */
    private void moveLeafLitterToPileCompletely(World world, ItemUsageContext context, BlockPos blockPos, BlockPos replacedBlockPos, BlockState blockState, PlayerEntity playerEntity ,int firstSegmentAmount, int secondSegmentAmount) {
        world.playSound(context.getPlayer(), blockPos, SoundEvents.BLOCK_LEAF_LITTER_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        world.setBlockState(replacedBlockPos, blockState.with(Properties.SEGMENT_AMOUNT, firstSegmentAmount + secondSegmentAmount));

        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), blockState));
        spawnLeafParticle(world, replacedBlockPos, firstSegmentAmount <= 2);
        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
    }

    /*
     * If the summed amount of segments of leaf litter at both positions exceeds the maximum amount, but
     * the amount in the player's position is less than the maximum amount, then the remaining amount from the
     * targeted position will be added, while the segments that exceed the maximum will stay at the targeted
     * position
     * */
    private void moveLeafLitterToPilePartially(World world, ItemUsageContext context, BlockPos blockPos, BlockPos replacedBlockPos, BlockState blockState, PlayerEntity playerEntity ,int firstSegmentAmount, int secondSegmentAmount) {
        world.playSound(context.getPlayer(), blockPos, SoundEvents.BLOCK_LEAF_LITTER_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        world.setBlockState(blockPos, blockState.with(Properties.SEGMENT_AMOUNT, firstSegmentAmount + secondSegmentAmount - Segmented.MAX_SEGMENTS));
        world.setBlockState(replacedBlockPos, blockState.with(Properties.SEGMENT_AMOUNT, 4));

        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), blockState));
        spawnLeafParticle(world, replacedBlockPos, firstSegmentAmount <= 2);
        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
    }

    private void spawnLeafParticle(World world, BlockPos pos, Boolean spawnTwoParticles) {
        TintedParticleEffect tintedParticleEffect = TintedParticleEffect.create(
                ParticleTypes.TINTED_LEAVES, 0xcf9363);
        if (spawnTwoParticles) {
            ParticleUtil.spawnParticle(world, pos.up(), world.random, tintedParticleEffect);
            ParticleUtil.spawnParticle(world, pos.up(), world.random, tintedParticleEffect);
        } else {
            ParticleUtil.spawnParticle(world, pos.up(), world.random, tintedParticleEffect);
        }
    }
}
