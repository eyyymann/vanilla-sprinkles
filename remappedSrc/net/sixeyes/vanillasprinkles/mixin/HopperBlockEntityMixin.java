package net.sixeyes.vanillasprinkles.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sixeyes.vanillasprinkles.datagen.ModItemTagGenerator;
import net.sixeyes.vanillasprinkles.inventory.FilteredInputInventory;
import net.sixeyes.vanillasprinkles.registry.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    // MODIFY THIS SO THAT THE TARGET POSITION IS DURING THE FOR LOOP TO GET THE APPROPRIATE ITEM STACK
    // AND CHECK IF IT'S IN THE SEED MAP KEY LIST

    @Inject(at = @At("HEAD"), method = "insert")
    private static void insertMixin(World world, BlockPos pos, HopperBlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(ModBlocks.PLANTER)) {

            HopperBlockEntity source = new HopperBlockEntity(pos, state);
            FilteredInputInventory filteredInventory = (FilteredInputInventory)(((HopperBlockEntityInvoker) source).invokeGetOutputInventory(world, pos, state));
            if (filteredInventory == null) {
                cir.setReturnValue(false);
            } else {
                Direction direction = ((Direction)state.get(HopperBlock.FACING)).getOpposite();
                if (((HopperBlockEntityInvoker) source).invokeIsInventoryFull(filteredInventory, direction)) {
                    cir.setReturnValue(false);
                } else {
                    for(int i = 0; i < inventory.size(); ++i) {
                        if (!inventory.getStack(i).isEmpty()) {
                            ItemStack itemStack = inventory.getStack(i).copy();

                            if (!itemStack.isIn(ModItemTagGenerator.COPPER_PLANTER_VALID_INPUT))
                                cir.setReturnValue(false);

                            ItemStack itemStack2 = ((HopperBlockEntityInvoker) source).invokeTransfer(inventory, filteredInventory, inventory.removeStack(i, 1), direction);
                            if (itemStack2.isEmpty()) {
                                filteredInventory.markDirty();
                                cir.setReturnValue(true);
                            }

                            inventory.setStack(i, itemStack);
                        }
                    }

                    cir.setReturnValue(false);
                }
            }
        }
    }

}
