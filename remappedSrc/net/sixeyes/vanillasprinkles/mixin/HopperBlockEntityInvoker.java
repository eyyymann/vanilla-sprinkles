package net.sixeyes.vanillasprinkles.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityInvoker {
    @Invoker("getOutputInventory")
    public Inventory invokeGetOutputInventory(World world, BlockPos pos, HopperBlockEntity blockEntity);

    @Invoker("isInventoryFull")
    public boolean invokeIsInventoryFull(Inventory inventory, Direction direction);

    @Invoker("transfer")
    public ItemStack invokeTransfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side);
}
