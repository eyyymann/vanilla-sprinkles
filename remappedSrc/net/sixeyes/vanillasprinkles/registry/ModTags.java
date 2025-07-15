package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;

public class ModTags {

    public static final TagKey<Block> BOULDER_BREAKABLE = blockTagOf("boulder_breakable");
    public static final TagKey<Block> BOULDER_PASSABLE = blockTagOf("boulder_passable");

    private static TagKey<Block> blockTagOf(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(VanillaSprinkles.MOD_ID, name));
    }

    public static void registerModTags() {

        VanillaSprinkles.LOGGER.info("Registering Mod Tags " + VanillaSprinkles.MOD_ID);
    }

}
