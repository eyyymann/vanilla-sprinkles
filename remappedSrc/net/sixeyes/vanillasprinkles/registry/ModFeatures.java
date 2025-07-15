package net.sixeyes.vanillasprinkles.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.world.feature.ThickGrassFeature;

public class ModFeatures {

    public static final Identifier THICK_GRASS_FEATURE_ID = Identifier.of(VanillaSprinkles.MOD_ID, "thick_grass");
    public static Feature<DefaultFeatureConfig> THICK_GRASS_FEATURE = new ThickGrassFeature(DefaultFeatureConfig.CODEC);

    public static void registerModFeatures() {
        Registry.register(Registries.FEATURE, THICK_GRASS_FEATURE_ID, THICK_GRASS_FEATURE);
        VanillaSprinkles.LOGGER.info("Registering Mod Features for " + VanillaSprinkles.MOD_ID);
    }
}
