package net.sixeyes.vanillasprinkles.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSources;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.block.SandWellBlock;
import net.sixeyes.vanillasprinkles.registry.ModBlockEntities;

public class SandWellBlockEntity extends BlockEntity {
    public SandWellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SAND_WELL_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SandWellBlockEntity be) {
        if (!world.isClient()) {
            if ((!world.isSkyVisible(pos.up()) || !world.getBiome(pos).matchesKey(BiomeKeys.DESERT)) && !state.get(SandWellBlock.CLOSED)) {
                world.setBlockState(pos, state.with(SandWellBlock.CLOSED, true), 2);
                world.playSound(null, pos, SoundEvents.BLOCK_TRIAL_SPAWNER_CLOSE_SHUTTER, SoundCategory.BLOCKS, 1.0F, 0.9F);
            }

            if (world.isSkyVisible(pos.up()) && world.getBiome(pos).matchesKey(BiomeKeys.DESERT) && state.get(SandWellBlock.CLOSED)) {
                world.setBlockState(pos, state.with(SandWellBlock.CLOSED, false), 2);
                world.playSound(null, pos, SoundEvents.BLOCK_TRIAL_SPAWNER_CLOSE_SHUTTER, SoundCategory.BLOCKS, 1.0F, 1.1F);
            }

            if (!state.get(SandWellBlock.CLOSED) && state.get(SandWellBlock.SAND) < 3) {
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 0.05F, 1.0F);
                Random random = world.getRandom();

                if (random.nextBetween(1,1000) <= 2) {
                    world.setBlockState(pos, state.with(SandWellBlock.SAND, state.get(SandWellBlock.SAND)+1), 2);
                    world.playSound(null, pos, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }
}
