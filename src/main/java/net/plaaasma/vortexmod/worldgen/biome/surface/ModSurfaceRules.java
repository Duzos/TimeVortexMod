package net.plaaasma.vortexmod.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.plaaasma.vortexmod.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource SEA_LANTERN = makeStateRule(Blocks.SEA_LANTERN);
    private static final SurfaceRules.RuleSource GLOWSTONE = makeStateRule(Blocks.GLOWSTONE);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME),  SEA_LANTERN),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SEA_LANTERN))
                ),


                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}