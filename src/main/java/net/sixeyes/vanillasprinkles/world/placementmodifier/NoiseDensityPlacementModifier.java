package net.sixeyes.vanillasprinkles.world.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.placementmodifier.AbstractCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import net.sixeyes.vanillasprinkles.registry.ModPlacementModifiers;

import java.util.List;

public class NoiseDensityPlacementModifier extends AbstractCountPlacementModifier {
    public static final MapCodec<NoiseDensityPlacementModifier> MODIFIER_CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(Codec.DOUBLE.fieldOf("noise_level").forGetter((NoiseDensityPlacementModifier) -> {
            return NoiseDensityPlacementModifier.noiseLevel;
        })).apply(instance, NoiseDensityPlacementModifier::new);
    });
    private final double noiseLevel;

    private NoiseDensityPlacementModifier(double noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public static NoiseDensityPlacementModifier of(double noiseLevel) {
        return new NoiseDensityPlacementModifier(noiseLevel);
    }

    protected int getCount(Random random, BlockPos pos) {
        OctaveSimplexNoiseSampler sampler = new OctaveSimplexNoiseSampler(random, List.of(-5,-2,1));
        double d = sampler.sample(pos.getX(), pos.getZ(), false);
        return d < this.noiseLevel ? 0 : (int)MathHelper.lerp(d, 1, 8);
    }

    public PlacementModifierType<?> getType() {
        return ModPlacementModifiers.NOISE_DENSITY;
    }
}
