package io.github.asablock.feature;

import io.github.asablock.MedicalKitMod;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.function.Predicate;

public final class Features {
    private Features() {}

    public static final ConfiguredFeature<?, ?> PATCH_WILD_COTTON_PLAINS;
    public static final ConfiguredFeature<?, ?> PATCH_WILD_COTTON_FOREST;

    static {
        PATCH_WILD_COTTON_PLAINS = Feature.RANDOM_PATCH.configure(Configs.WILD_COTTON_CONFIG)
                .applyChance(50)
                .spreadHorizontally()
                .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D,
                        5, 10)));
        PATCH_WILD_COTTON_FOREST = Feature.RANDOM_PATCH.configure(Configs.WILD_COTTON_CONFIG)
                .applyChance(50)
                .repeat(2)
                .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                .repeat(2);
    }

    public static final class Configs {
        private Configs() {}

        public static final RandomPatchFeatureConfig WILD_COTTON_CONFIG;

        static {
            WILD_COTTON_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(States.WILD_COTTON),
                    SimpleBlockPlacer.INSTANCE).tries(25).build();
        }
    }

    public static final class States {
        private States() {}

        public static final BlockState WILD_COTTON;

        static {
            WILD_COTTON = MedicalKitMod.WILD_COTTON_BLOCK.getDefaultState();
        }
    }

    @SuppressWarnings("deprecation")
    public static Predicate<BiomeSelectionContext> foundInPlainsAndForest() {
        return BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.FOREST,
                BiomeKeys.WOODED_HILLS, BiomeKeys.BIRCH_FOREST);
    }
}
