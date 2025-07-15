package net.sixeyes.vanillasprinkles.block.planter;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class InvalidItemBehavior {

    public InvalidItemBehavior() {

    }

    public static Direction getRandomSide(Random random) {
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        return directions[random.nextBetween(0,3)];
    }

    public final ItemStack dispense(World world, BlockPos pos, ItemStack itemStack) {
        Direction side = getRandomSide(world.getRandom());
        ItemStack itemStack2 = this.dispenseSilently(world, pos, side, itemStack);
        this.playSound(world, pos);
        this.spawnParticles(world, pos, side);
        return itemStack2;
    }

    protected ItemStack dispenseSilently(World world, BlockPos pos, Direction side, ItemStack stack) {
        Position position = getOutputLocation(side, pos);
        ItemStack itemStack = stack.split(1);
        spawnItem(world, itemStack, 6, side, position);
        return stack;
    }

    public static void spawnItem(World world, ItemStack stack, int speed, Direction side, Position pos) {
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if (side.getAxis() == Direction.Axis.Y) {
            e -= 0.125;
        } else {
            e -= 0.15625;
        }

        ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
        double g = world.random.nextDouble() * 0.1 + 0.2;
        itemEntity.setVelocity(world.random.nextTriangular((double)side.getOffsetX() * g, 0.0172275 * (double)speed), world.random.nextTriangular(0.2, 0.0172275 * (double)speed), world.random.nextTriangular((double)side.getOffsetZ() * g, 0.0172275 * (double)speed));
        world.spawnEntity(itemEntity);
    }

    protected void playSound(World world, BlockPos pos) {
        world.syncWorldEvent(1000, pos, 0);
    }

    protected void spawnParticles(World world, BlockPos pos, Direction side) {
        world.syncWorldEvent(2000, pos, side.getId());
    }

    public static Position getOutputLocation(Direction direction, BlockPos pos) {
        return pos.toCenterPos().add(0.7 * (double)direction.getOffsetX(), 0.7 * (double)direction.getOffsetY(), 0.7 * (double)direction.getOffsetZ());
    }
}
