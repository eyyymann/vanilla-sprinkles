package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.world.placementmodifier.NoiseDensityPlacementModifier;

public class ModPlacementModifiers {

    public static final Identifier NOISE_DENSITY_ID = Identifier.of(VanillaSprinkles.MOD_ID, "noise_density");
    public static PlacementModifierType<NoiseDensityPlacementModifier> NOISE_DENSITY = Registry.register(
            Registries.PLACEMENT_MODIFIER_TYPE,
            NOISE_DENSITY_ID,
            () -> NoiseDensityPlacementModifier.MODIFIER_CODEC);

    public static void registerModPlacementModifiers() {
        VanillaSprinkles.LOGGER.info("Registering Mod Placement Modifiers for " + VanillaSprinkles.MOD_ID);
    }
}
