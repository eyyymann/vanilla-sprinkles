package net.sixeyes.vanillasprinkles;

import net.fabricmc.api.ModInitializer;

import net.sixeyes.vanillasprinkles.registry.*;
import net.sixeyes.vanillasprinkles.world.trees.ModTrees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaSprinkles implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "vanillasprinkles";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerBlocks();
		ModFeatures.registerModFeatures();
		ModPlacementModifiers.registerModPlacementModifiers();
		ModItems.registerModItems();
		ModBlockEntities.registerModBlockEntities();
		ModTags.registerModTags();
		ModTrees.registerModTrees();
		LOGGER.info("Hello Fabric world!");
	}
}