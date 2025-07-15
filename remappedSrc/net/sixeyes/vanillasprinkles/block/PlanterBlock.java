package net.sixeyes.vanillasprinkles.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.block.entity.PlanterBlockEntity;
import net.sixeyes.vanillasprinkles.block.planter.InvalidItemBehavior;
import net.sixeyes.vanillasprinkles.block.planter.ValidItemBehavior;
import net.sixeyes.vanillasprinkles.datagen.ModItemTagGenerator;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.*;

public class PlanterBlock extends BlockWithEntity {

    // HOW IT WORKS //
    // when powered with redstone it will place the seed it has inside on the farthest farmland in a 9x9x9 cube area
    // only has 1 slot
    // ADD A UNIQUE BEHAVIOUR WHERE ONLY SEEDS AND OTHER CROP BLOCKS CAN BE PUT INTO THE PLANTER (HOPPER MIXIN)

    // ADD ACTIVE TOP TEXTURE CHANGE (MAKE THE QUARTZ LIGHT UP)

    // ADD THE ANIMATION OF 'PLANTING' AND PARTICLES

    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    public static final DirectionProperty FACING = Properties.FACING;
    private static final int SCHEDULED_TICK_DELAY = 4;

    private static final int PLANTING_RANGE = 5;

    public static final Map<Item, Block> SEED_TO_BLOCK_CROPS = new HashMap<>() {
        {
            put(Items.CARROT, Blocks.CARROTS);
            put(Items.WHEAT_SEEDS, Blocks.WHEAT);
            put(Items.POTATO, Blocks.POTATOES);
            put(Items.BEETROOT_SEEDS, Blocks.BEETROOTS);
            put(Items.MELON_SEEDS, Blocks.MELON_STEM);
            put(Items.PUMPKIN_SEEDS, Blocks.PUMPKIN_STEM);
            put(Items.TORCHFLOWER_SEEDS, Blocks.TORCHFLOWER_CROP);
            put(Items.PITCHER_POD, Blocks.PITCHER_CROP);
        }
    };

    public PlanterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TRIGGERED, false).with(FACING, Direction.NORTH));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED).add(FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlanterBlockEntity(pos, state);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.plant((ServerWorld) world, state, pos);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean bl2 = (Boolean)state.get(TRIGGERED);
        if (bl && !bl2) {
            world.scheduleBlockTick(pos, this, 4);
            world.setBlockState(pos, (BlockState)state.with(TRIGGERED, true), 2);
        } else if (!bl && bl2) {
            world.setBlockState(pos, (BlockState)state.with(TRIGGERED, false), 2);
        }

    }

    protected void plant(ServerWorld world, BlockState state, BlockPos pos) {
        PlanterBlockEntity planterBlockEntity = (PlanterBlockEntity)world.getBlockEntity(
                pos, ModBlocks.PLANTER_BLOCK_ENTITY).orElse((PlanterBlockEntity) null);

        if (state.get(TRIGGERED) && planterBlockEntity != null) {
            ItemStack itemStack = planterBlockEntity.getStack(0);
            int count = itemStack.getCount();

            // CHECKING IF THE ITEM IS A SEED - IF NOT, THEN DISPENSE LIKE DISPENSER
            if (!itemStack.isIn(ModItemTagGenerator.COPPER_PLANTER_VALID_INPUT)) {
                InvalidItemBehavior invalidItemBehavior = new InvalidItemBehavior();
                invalidItemBehavior.dispense(world, pos, itemStack);
                return;
            }

            // SCAN 11x11x11 AREA FOR ALL FARMLAND TYPE BLOCKS
            List<FarmlandPointer> FarmlandList = new ArrayList<>();
            for (int x = -PLANTING_RANGE; x <= PLANTING_RANGE; x++) {
                for (int y = -PLANTING_RANGE; y <= PLANTING_RANGE; y++) {
                    for (int z = -PLANTING_RANGE; z <= PLANTING_RANGE; z++) {

                        BlockPos targetPos = pos.north(x).west(z).up(y);
                        BlockState target = world.getBlockState(targetPos);

                        if (target.isOf(Blocks.FARMLAND) && !world.getBlockState(targetPos.up()).isIn(BlockTags.CROPS))
                            FarmlandList.add(new FarmlandPointer(target, targetPos, pos.getSquaredDistance(targetPos)));
                    }
                }
            }

            // LOOK FOR THE ONE WITH THE GREATEST DISTANCE TO SOURCE
            FarmlandPointer mostDistant = new FarmlandPointer(null, null, 0);
            for (FarmlandPointer fp : FarmlandList) {
                if (fp.distanceFromSource > mostDistant.distanceFromSource)
                    mostDistant = fp;
            }

            if (mostDistant.pos != null) {
                // GETTING THE FACE, FROM WHICH THE PLANTER WILL PLANT
                Direction plantingDirection = getPlantingDirection(pos, mostDistant.pos, mostDistant.distanceFromSource);
                ValidItemBehavior validItemBehavior = new ValidItemBehavior();

                // DEBUG DISPENSING TO SEE IF FACE CALCULATION IS GOOD
                validItemBehavior.dispense(world, pos, plantingDirection, mostDistant);

                // PLACING THE STEM ON THE TARGETTED BLOCK AND DECREMENTING THE AMOUNT OF SEEDS
                BlockState resultCropState = (SEED_TO_BLOCK_CROPS.get(itemStack.getItem())).getDefaultState();
                world.setBlockState(mostDistant.pos.up(1), resultCropState);
                itemStack.setCount(count - 1);
            }
        }
    }

    public static Direction getPlantingDirection(BlockPos sourcePos, BlockPos targetPos, double distance) {
        List<Direction> directions = new ArrayList<>() {
            {
                add(Direction.NORTH);
                add(Direction.EAST);
                add(Direction.SOUTH);
                add(Direction.WEST);
            }
        };

        /*
        *  SETS THE SHORTEST DISTANCE TO 2 TIMES THE DISTANCE FROM THE SOURCE TO THE TARGET
        *  FOR EVERY HORIZONTAL DIRECTION IT EVALUATES THE DISTANCE BETWEEN THE REFERENCE AND THE TARGET
        *  IF SAID DISTANCE IS LESS THAN THE CURRENT SHORTEST DISTANCE, THEN ITS OVERWRITTEN
        *  AND THE DIRECTION IS ALSO OVERWRITTEN
        * */

        double shortestDistance = targetPos.toCenterPos().distanceTo(sourcePos.offset(Direction.NORTH, MathHelper.floor(distance)).toCenterPos());

        double tempDistance;

        Direction chosenDirection = Direction.NORTH;
        for (Direction d : directions.subList(1,3)) {
            tempDistance = targetPos.toCenterPos().distanceTo(sourcePos.offset(d, MathHelper.floor(distance)).toCenterPos());

            VanillaSprinkles.LOGGER.warn("Distance from {} to {} is {}!",
                    targetPos, sourcePos.offset(d, MathHelper.floor(distance)), tempDistance);
            VanillaSprinkles.LOGGER.warn("Current distance is {} compared to potential {}", shortestDistance, tempDistance);
            if (tempDistance <= shortestDistance) {
                shortestDistance = tempDistance;
                chosenDirection = d;
            }
        }

        return chosenDirection;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    //This method will drop all items onto the ground when the block is broken
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PlanterBlockEntity) {
                ItemScatterer.spawn(world, pos, (PlanterBlockEntity)blockEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PlanterBlockEntity) {
                ((PlanterBlockEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public class FarmlandPointer {

        public BlockState blockState;
        public BlockPos pos;
        public double distanceFromSource;

        FarmlandPointer(BlockState blockState, BlockPos pos, double distanceFromSource) {
            this.blockState = blockState;
            this.pos = pos;
            this.distanceFromSource = distanceFromSource;
        }
    }
}
