package net.sixeyes.vanillasprinkles;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sixeyes.vanillasprinkles.datagen.*;

public class VanillaSprinklesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// initializing block loot table generation
		FabricDataGenerator.Pack myPack = fabricDataGenerator.createPack();
		myPack.addProvider(ModModelGenerator::new);
		myPack.addProvider(ModBlockLootTables::new);
		myPack.addProvider(ModBlockTagGenerator::new);
		myPack.addProvider(ModItemTagGenerator::new);
		myPack.addProvider(ModRecipesGenerator::new);
		myPack.addProvider(ModEnglishLangProvider::new);
	}
}
