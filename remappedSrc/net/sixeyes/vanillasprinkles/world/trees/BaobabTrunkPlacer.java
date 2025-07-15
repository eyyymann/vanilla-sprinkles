package net.sixeyes.vanillasprinkles.world.trees;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BaobabTrunkPlacer extends TrunkPlacer {
    // Use the fillTrunkPlacerFields to create our codec
    public static final MapCodec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            fillTrunkPlacerFields(instance).apply(instance, BaobabTrunkPlacer::new));

    public BaobabTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrees.BAOBAB_TRUNK_PLACER;
    }

    private void generateCube(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos startPos,
                              int thickness, TreeFeatureConfig config) {
        for (int x = 0; x < thickness; x++) {
            for (int z = 0; z < thickness; z++) {
                BlockPos pos = startPos.north(x).east(z);
                this.getAndSetState(world, placer, random, pos, config);
            }
        }
    }

    private List<Pair<BlockPos, Integer>> generateBranchesSide(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos startPos, int real_height, int starting_height, TreeFeatureConfig config
    , Direction.Axis axis_offset, Direction.Axis axis_main, boolean isNegativeDirection) {

        int minimum_length = 4;
        int maximum_length = 6;
        int branch_height = 3;
        int horizontal_offset;
        int vertical_offset;
        int length;
        BlockPos pos = startPos;
        List<Pair<BlockPos, Integer>> branch_nodes = new ArrayList<>();

        Function<BlockState, BlockState> functionX = (state) -> (BlockState)state.withIfExists(PillarBlock.AXIS, Direction.Axis.X);
        Function<BlockState, BlockState> functionZ = (state) -> (BlockState)state.withIfExists(PillarBlock.AXIS, Direction.Axis.Z);

        vertical_offset = random.nextBetween(0, real_height - starting_height);

        for (int i = 0; i < 2; i++) {
            horizontal_offset = random.nextBetween(0, 3);
            length = random.nextBetween(minimum_length, maximum_length);

            for (int x = 0; x < length; x++) {
                pos = startPos.up(starting_height + vertical_offset)
                        .offset(axis_offset, axis_main == Direction.Axis.X ? -horizontal_offset : horizontal_offset)
                        .offset(axis_main, (
                                axis_main == Direction.Axis.X ? (
                                        isNegativeDirection ? -x-1 : x+4
                                ) : (
                                        isNegativeDirection ? -x-4 : x+1
                                )));
                this.getAndSetState(world, placer, random, pos, config,
                        axis_main == Direction.Axis.X ? functionX : functionZ);
            }

            for (int y = 0; y < random.nextBetween(1, branch_height); y++) {
                pos = pos.up();
                this.getAndSetState(world, placer, random, pos, config);
            }
            branch_nodes.add(new Pair(pos.up(), length - 4));

            // conditions to check what should happen with the next branch
            if (vertical_offset <= 1 || vertical_offset > real_height - starting_height - 2) {
                vertical_offset = vertical_offset <= 1
                        ? (random.nextBetween(real_height - starting_height - 1, real_height - starting_height))
                        : (random.nextBetween(0, 1));
            } else {
                break;
            }
        }

        return branch_nodes;
    }

    private List<Pair<BlockPos, Integer>> generateBranches(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos startPos,
                                                           int real_height, int starting_height, TreeFeatureConfig config) {
        List<Pair<BlockPos, Integer>> branch_nodes = new ArrayList<>();

        // POSITIVE X BRANCH
        branch_nodes.addAll(
                generateBranchesSide(world, placer, random, startPos, real_height, starting_height, config,
                        Direction.Axis.Z, Direction.Axis.X, false));

        // NEGATIVE X BRANCH
        branch_nodes.addAll(
                generateBranchesSide(world, placer, random, startPos, real_height, starting_height, config,
                        Direction.Axis.Z, Direction.Axis.X, true));

        // POSITIVE Z BRANCH
        branch_nodes.addAll(
                generateBranchesSide(world, placer, random, startPos, real_height, starting_height, config,
                        Direction.Axis.X, Direction.Axis.Z, false));

        // NEGATIVE Z BRANCH
        branch_nodes.addAll(
                generateBranchesSide(world, placer, random, startPos, real_height, starting_height, config,
                        Direction.Axis.X, Direction.Axis.Z, true));

        return branch_nodes;
    }

    private void generateTrunks(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos startPos, int real_height, TreeFeatureConfig config) {

        int offset;

        // POSITIVE X
        offset = random.nextBetween(1,2);
        for (int i = -1; i < random.nextBetween(1,3); i++) {
            this.getAndSetState(world, placer, random, startPos.up(i).offset(Direction.Axis.X, 4).offset(Direction.Axis.Z, -offset), config);
        }

        // NEGATIVE X
        offset = random.nextBetween(1,2);
        for (int i = -1; i < random.nextBetween(1,3); i++) {
            this.getAndSetState(world, placer, random, startPos.up(i).offset(Direction.Axis.X, -1).offset(Direction.Axis.Z, -offset), config);
        }

        // POSITIVE Z
        offset = random.nextBetween(1,2);
        for (int i = -1; i < random.nextBetween(1,3); i++) {
            this.getAndSetState(world, placer, random, startPos.up(i).offset(Direction.Axis.Z, 1).offset(Direction.Axis.X, offset), config);
        }

        // NEGATIVE Z
        offset = random.nextBetween(1,2);
        for (int i = -1; i < random.nextBetween(1,3); i++) {
            this.getAndSetState(world, placer, random, startPos.up(i).offset(Direction.Axis.Z, -4).offset(Direction.Axis.X, offset), config);
        }
    }

    private void generateCore(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos startPos, int real_height, TreeFeatureConfig config) {
        for (int i = 0; i <= real_height; i++) {
            BlockPos basePos = startPos.up(i);
            generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 1), 2, config);
            generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 1).offset(Direction.Axis.Z, -2), 2, config);

            generateCube(world, placer, random, basePos.offset(Direction.Axis.Z, -1), 1, config);
            generateCube(world, placer, random, basePos.offset(Direction.Axis.Z, -2), 1, config);
            generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 3).offset(Direction.Axis.Z, -1), 1, config);
            generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 3).offset(Direction.Axis.Z, -2), 1, config);

            if (i < real_height) {
                generateCube(world, placer, random, basePos, 1, config);
                generateCube(world, placer, random, basePos.offset(Direction.Axis.Z, -3), 1, config);
                generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 3), 1, config);
                generateCube(world, placer, random, basePos.offset(Direction.Axis.X, 3).offset(Direction.Axis.Z, -3), 1, config);
            }
        }

        // NEGATIVE X
        BlockPos cornerPos = startPos.up(real_height);
        if (world.testBlockState(cornerPos.offset(Direction.Axis.X, -1), state -> state.isIn(BlockTags.LOGS))
                || world.testBlockState(cornerPos.offset(Direction.Axis.Z, 1), state -> state.isIn(BlockTags.LOGS))) {
            this.getAndSetState(world, placer, random, cornerPos, config);
        }

        // POSITIVE X
        cornerPos = startPos.up(real_height).offset(Direction.Axis.X, 3);
        if (world.testBlockState(cornerPos.offset(Direction.Axis.X, 1), state -> state.isIn(BlockTags.LOGS))
                || world.testBlockState(cornerPos.offset(Direction.Axis.Z, 1), state -> state.isIn(BlockTags.LOGS))) {
            this.getAndSetState(world, placer, random, cornerPos, config);
        }

        // NEGATIVE Z
        cornerPos = startPos.up(real_height).offset(Direction.Axis.Z, -3);
        if (world.testBlockState(cornerPos.offset(Direction.Axis.X, -1), state -> state.isIn(BlockTags.LOGS))
                || world.testBlockState(cornerPos.offset(Direction.Axis.Z, -1), state -> state.isIn(BlockTags.LOGS))) {
            this.getAndSetState(world, placer, random, cornerPos, config);
        }

        // POSITIVE Z
        cornerPos = startPos.up(real_height).offset(Direction.Axis.Z, -3).offset(Direction.Axis.X, 3);
        if (world.testBlockState(cornerPos.offset(Direction.Axis.X, 1), state -> state.isIn(BlockTags.LOGS))
                || world.testBlockState(cornerPos.offset(Direction.Axis.Z, -1), state -> state.isIn(BlockTags.LOGS))) {
            this.getAndSetState(world, placer, random, cornerPos, config);
        }
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> placer,
            Random random, int height, BlockPos startPos,
            TreeFeatureConfig config) {

        int real_height = (height + (height%2));

        // create variable length branches on each side of core and record the tips for foliage nodes
        int starting_height = real_height/2;
        List<Pair<BlockPos, Integer>> list =  generateBranches(world, placer, random, startPos, real_height, starting_height, config);
        List<FoliagePlacer.TreeNode> nodes = new ArrayList<>();

        // create 4xYx4 core with indents in top corners based on the presence on branches on said corners
        generateCore(world, placer, random, startPos, real_height, config);

        // add small trunks to each side of core
        generateTrunks(world, placer, random, startPos, real_height, config);

        for (Pair<BlockPos, Integer> pair : list) {
            nodes.add(new FoliagePlacer.TreeNode(pair.getLeft(), pair.getRight(), false));
        }

        return nodes;
    }
}
