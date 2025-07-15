package net.sixeyes.vanillasprinkles.block.planter;

import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.sixeyes.vanillasprinkles.block.PlanterBlock;

public class ValidItemBehavior {

    public ValidItemBehavior() {

    }

    public final void dispense(World world, BlockPos pos, Direction side, PlanterBlock.FarmlandPointer target) {
        this.dispenseProjectile(world, pos, side, target);
        this.playSound(world, pos);
        this.spawnParticles(world, pos, side);
    }

    protected void dispenseProjectile(World world, BlockPos pos, Direction side, PlanterBlock.FarmlandPointer target) {
        Position position = getOutputLocation(side, pos);
        spawnProjectile(world,6, side, position, target);
    }

    // MODIFY VELOCITY WITH SMART MATH TO HAVE IT LAND ON THAT PLACE
    public static void spawnProjectile(World world, int speed, Direction side, Position pos, PlanterBlock.FarmlandPointer target) {
        // planter X and Z
        double sourceX = pos.getX();
        double sourceZ = pos.getZ();

        // target X and Z
        double targetX = target.pos.getX();
        double targetZ = target.pos.getZ();

        // Y is common
        double Y = pos.getY();

        // Velocity of projectile
        double vX = 0, vZ = 0;
        double vModifier = 0.001;

        vX = (targetX - sourceX) * vModifier;
        vZ = (targetZ - sourceZ) * vModifier;

        SnowballEntity snowballEntity = new SnowballEntity(world, sourceX, Y, sourceZ);
        snowballEntity.setVelocity(vX, 0.001, vZ);
        world.spawnEntity(snowballEntity);
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
