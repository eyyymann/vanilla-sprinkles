package net.sixeyes.vanillasprinkles.world.trees;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;

public class ModTrees {


    // BAOBAB BIG GENERATION
    public static final Identifier BAOBAB_TRUNK_PLACER_ID = Identifier.of(VanillaSprinkles.MOD_ID, "baobab_trunk_placer");
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = Registry.register(
            Registries.TRUNK_PLACER_TYPE, BAOBAB_TRUNK_PLACER_ID, new TrunkPlacerType(BaobabTrunkPlacer.CODEC));

    public static void registerModTrees() {

        VanillaSprinkles.LOGGER.info("Registering Mod Trunk Placers for " + VanillaSprinkles.MOD_ID);
    }
}
