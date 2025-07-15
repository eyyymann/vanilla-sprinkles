package net.sixeyes.vanillasprinkles.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public interface FilteredInputInventory extends Inventory {

    default boolean canTransferFrom(Inventory hopperInventory, int slot, ItemStack stack, TagKey<Item> tag) {
        if (tag != null) {
            return stack.isIn(tag);
        } return true;
    }
}
