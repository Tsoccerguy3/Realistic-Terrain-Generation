package rtg.world.biome.realistic.vanilla;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenTrees;

import rtg.api.biome.BiomeConfig;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBirchForest;
import rtg.util.BlockUtil;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;
import rtg.world.biome.deco.*;
import rtg.world.biome.deco.helper.DecoHelperRandomSplit;
import rtg.world.gen.feature.tree.rtg.TreeRTG;
import rtg.world.gen.feature.tree.rtg.TreeRTGBetulaPapyrifera;
import rtg.world.gen.surface.vanilla.SurfaceVanillaBirchForest;
import rtg.world.gen.terrain.GroundEffect;
import rtg.world.gen.terrain.TerrainBase;

public class RealisticBiomeVanillaBirchForest extends RealisticBiomeVanillaBase {

    public static Biome biome = Biomes.BIRCH_FOREST;
    public static Biome river = Biomes.RIVER;

    public RealisticBiomeVanillaBirchForest(BiomeConfig config) {

        super(config, biome, river,
            new rtg.world.gen.terrain.vanilla.TerrainVanillaBirchForest(),
            new SurfaceVanillaBirchForest(config, biome.topBlock, biome.fillerBlock, 0f, 1.5f, 60f, 65f, 1.5f, BlockUtil.getStateDirt(2), 0.15f)
        );

        TreeRTG birchSmall = new TreeRTGBetulaPapyrifera();
        birchSmall.logBlock = BlockUtil.getStateLog(2);
        birchSmall.leavesBlock = BlockUtil.getStateLeaf(2);
        birchSmall.minTrunkSize = 4;
        birchSmall.maxTrunkSize = 10;
        birchSmall.minCrownSize = 8;
        birchSmall.maxCrownSize = 19;
        this.addTree(birchSmall);

        DecoTree smallBirch = new DecoTree(birchSmall);
        smallBirch.strengthNoiseFactorForLoops = true;
        smallBirch.treeType = DecoTree.TreeType.RTG_TREE;
        smallBirch.distribution.noiseDivisor = 80f;
        smallBirch.distribution.noiseFactor = 60f;
        smallBirch.distribution.noiseAddend = -15f;
        smallBirch.treeCondition = DecoTree.TreeCondition.ALWAYS_GENERATE;
        smallBirch.maxY = 120;
        this.addDeco(smallBirch);

        TreeRTG birchTree = new TreeRTGBetulaPapyrifera();
        birchTree.logBlock = BlockUtil.getStateLog(2);
        birchTree.leavesBlock = BlockUtil.getStateLeaf(2);
        birchTree.minTrunkSize = 4;
        birchTree.maxTrunkSize = 10;
        birchTree.minCrownSize = 8;
        birchTree.maxCrownSize = 19;
        this.addTree(birchTree);

        DecoTree birchTrees = new DecoTree(birchTree);
        birchTrees.strengthFactorForLoops = 3f;
        birchTrees.treeType = DecoTree.TreeType.RTG_TREE;
        birchTrees.treeCondition = DecoTree.TreeCondition.ALWAYS_GENERATE;
        birchTrees.maxY = 100;

        DecoTree rtgTrees = new DecoTree(new WorldGenTrees(false));
        rtgTrees.treeType = DecoTree.TreeType.WORLDGEN;
        rtgTrees.strengthFactorForLoops = 3f;
        rtgTrees.treeCondition = DecoTree.TreeCondition.ALWAYS_GENERATE;
        rtgTrees.maxY = 100;

        DecoHelperRandomSplit decoHelperRandomSplit = new DecoHelperRandomSplit();
        decoHelperRandomSplit.decos = new DecoBase[]{birchTrees, rtgTrees};
        decoHelperRandomSplit.chances = new int[]{10, 4};
        this.addDeco(decoHelperRandomSplit);

        DecoFallenTree decoFallenTree = new DecoFallenTree();
        decoFallenTree.logCondition = DecoFallenTree.LogCondition.RANDOM_CHANCE;
        decoFallenTree.logConditionChance = 8;
        decoFallenTree.logBlock = BlockUtil.getStateLog(2);
        decoFallenTree.leavesBlock = BlockUtil.getStateLeaf(2);
        decoFallenTree.minSize = 3;
        decoFallenTree.maxSize = 6;
        this.addDeco(decoFallenTree, this.config._boolean(BiomeConfigVanillaBirchForest.decorationLogsId));

        DecoShrub decoShrub = new DecoShrub();
        decoShrub.maxY = 120;
        decoShrub.strengthFactor = 3f;
        this.addDeco(decoShrub);

        DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
        decoBaseBiomeDecorations.notEqualsZeroChance = 3;
        this.addDeco(decoBaseBiomeDecorations);

        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.flowers = new int[]{3, 6};
        decoFlowersRTG.maxY = 128;
        decoFlowersRTG.strengthFactor = 12f;
        this.addDeco(decoFlowersRTG);

        DecoGrass decoGrass = new DecoGrass();
        decoGrass.maxY = 128;
        decoGrass.strengthFactor = 20f;
        this.addDeco(decoGrass);
    }

    @Override
    public TerrainBase initTerrain() {

        return new TerrainVanillaBirchForest();
    }

    public class TerrainVanillaBirchForest extends TerrainBase {


        private GroundEffect groundEffect = new GroundEffect(4f);

        public TerrainVanillaBirchForest() {

        }

        @Override
        public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river) {
            //return terrainPlains(x, y, simplex, river, 160f, 10f, 60f, 80f, 65f);
            return riverized(65f + groundEffect.added(simplex, cell, x, y), river);
        }
    }
}
