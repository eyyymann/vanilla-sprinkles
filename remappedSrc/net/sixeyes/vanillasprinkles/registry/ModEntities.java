package net.sixeyes.vanillasprinkles.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.entity.ChimpanzeeEntity;

public class ModEntities {
    public static final EntityType<ChimpanzeeEntity> CHIMPANZEE = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(VanillaSprinkles.MOD_ID, "chimpanzee"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChimpanzeeEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1.25f)).build());

    public static void registerEntities() {
        FabricDefaultAttributeRegistry.register(ModEntities.CHIMPANZEE, ChimpanzeeEntity.setAttributes());
    }
}
