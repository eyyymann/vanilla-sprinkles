package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.block.entity.SandWellBlockEntity;

public class ModBlockEntities {

    public static final BlockEntityType<SandWellBlockEntity> SAND_WELL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(VanillaSprinkles.MOD_ID, "sand_well_block_entity"),
            BlockEntityType.Builder.create(SandWellBlockEntity::new, ModBlocks.SAND_WELL).build()
    );

    public static void registerModBlockEntities() {

        VanillaSprinkles.LOGGER.info("Registering Mod Tags " + VanillaSprinkles.MOD_ID);
    }
}
