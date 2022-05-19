package io.github.asablock;

import io.github.asablock.block.CottonBlock;
import io.github.asablock.client.ClientTransparent;
import io.github.asablock.feature.Features;
import io.github.asablock.item.BandageItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.function.Predicate;

public class MedicalKitMod implements ModInitializer {
    @ClientTransparent
    public static final Block COTTON_BLOCK = new CottonBlock(FabricBlockSettings.copyOf(Blocks.WHEAT));
    @ClientTransparent
    public static final Block WILD_COTTON_BLOCK = new PlantBlock(FabricBlockSettings.copyOf(COTTON_BLOCK)) {};

    public static final Item COTTON_SEEDS_ITEM = new AliasedBlockItem(COTTON_BLOCK, groupped());
    public static final Item COTTON_WAD_ITEM = new Item(groupped());
    public static final Item BANDAGE_1_ITEM = new BandageItem(groupped().maxDamage(15), 1);
    public static final Item BANDAGE_2_ITEM = new BandageItem(groupped().maxDamage(25), 2);
    public static final Item BANDAGE_3_ITEM = new BandageItem(groupped().maxDamage(35), 3);

    public static final ItemGroup MEDICAL_KIT_GROUP = FabricItemGroupBuilder.build(identifier("item_group"),
            () -> new ItemStack(BANDAGE_1_ITEM));

    @Override
    public void onInitialize() {
        registerBlocks();
        registerItems();
        registerConfiguredFeatures();
    }

    private static void registerBlocks() {
        register(COTTON_BLOCK, "cotton");
        register(WILD_COTTON_BLOCK, "wild_cotton");
    }

    private static void registerItems() {
        register(COTTON_SEEDS_ITEM, "cotton_seeds");
        register(COTTON_WAD_ITEM, "cotton_wad");
        register(BANDAGE_1_ITEM, "bandage_1");
        register(BANDAGE_2_ITEM, "bandage_2");
        register(BANDAGE_3_ITEM, "bandage_3");
    }

    private static void registerConfiguredFeatures() {
        registerAndAdd(Features.PATCH_WILD_COTTON_PLAINS, "patch_wild_cotton_plains",
                Features.foundInPlainsAndForest(), GenerationStep.Feature.VEGETAL_DECORATION);
        registerAndAdd(Features.PATCH_WILD_COTTON_FOREST, "patch_wild_cotton_forest",
                Features.foundInPlainsAndForest(), GenerationStep.Feature.VEGETAL_DECORATION);
    }

    private static void register(Item item, String id) {
        Registry.register(Registry.ITEM, identifier(id), item);
    }

    private static void register(Block block, String id) {
        Registry.register(Registry.BLOCK, identifier(id), block);
    }

    @SuppressWarnings("deprecation")
    private static void registerAndAdd(ConfiguredFeature<?, ?> configuredFeature, String id,
                                       Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature featureType) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, identifier(id));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), configuredFeature);
        BiomeModifications.addFeature(biomeSelector, featureType, key);
    }

    private static Identifier identifier(String id) {
        return new Identifier("medical-kit", id);
    }

    private static Item.Settings groupped() {
        return new Item.Settings().group(MEDICAL_KIT_GROUP);
    }
}
