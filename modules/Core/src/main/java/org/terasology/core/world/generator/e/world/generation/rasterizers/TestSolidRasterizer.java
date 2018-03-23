/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.core.world.generator.e.world.generation.rasterizers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.core.world.generator.facets.BiomeFacet;
import org.terasology.math.geom.Vector2i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.biomes.Biome;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.liquid.LiquidData;
import org.terasology.world.liquid.LiquidType;

public class TestSolidRasterizer implements WorldRasterizer {

    private boolean debug = true;
    private static final Logger logger = LoggerFactory.getLogger(TestSolidRasterizer.class);
    private static int counter = 0;
    private static double min=0;
    private static double max=0;

    private Block water;
    private Block ice;
    private Block stone;
    private Block sand;
    private Block grass;
    private Block snow;
    private Block dirt;

    private Block glass;
    private Block brick;


    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        stone = blockManager.getBlock("core:stone");
        dirt = blockManager.getBlock("core:Dirt");
        sand = blockManager.getBlock("core:Sand");
        brick = blockManager.getBlock("core:Brick");

        glass = blockManager.getBlock("core:Glass");
        snow = blockManager.getBlock("core:Snow");

        water = blockManager.getBlock("core:water");
        ice = blockManager.getBlock("core:Ice");
        grass = blockManager.getBlock("core:Grass");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        LiquidData waterLiquid = new LiquidData(LiquidType.WATER, LiquidData.MAX_LIQUID_DEPTH);
        InfiniteGenFacet solidityFacet = chunkRegion.getFacet(InfiniteGenFacet.class);

        BiomeFacet biomeFacet = chunkRegion.getFacet(BiomeFacet.class);
        int seaLevel = -200;


        Vector2i pos2d = new Vector2i();
        for (Vector3i pos : ChunkConstants.CHUNK_REGION) {

            pos2d.set(pos.x, pos.z);
            int posY = pos.y + chunk.getChunkWorldOffsetY();

            final int maxY = 200;

            // dont generate after certain height
            if (posY > maxY) {
                continue;
            }

            //TODO make into dummy
            //dummy biome stuff
            Biome biome = biomeFacet.get(pos2d);
            chunk.setBiome(pos.x, pos.y, pos.z, biome);

            double density = solidityFacet.get(pos);

            if(4987<counter && this.debug){
                if(density>max)
                    max=density;
                if(density<min)
                    min=density;
                logger.warn("Density at (X:"+pos.x+",Y:"+pos.y+",Z:"+pos.z+") (min:"+min+",max:"+max+"): " + density + "");
                counter=0;
            }
            counter++;

            if (density >= 1000) {
                chunk.setBlock(pos, snow);
                continue;
            }

            if (density == 14 || density == 24 || density == 49 || density == 99 || density == 199 || density == 299 || density == 399 || density == 499 || density == 599 || density == 699 || density == 799 || density == 899 || density == 999) {
                chunk.setBlock(pos, glass);
                continue;
            }

            if (density >= 100) {
                chunk.setBlock(pos, stone);
                continue;
            }

            if (density >= 50) {
                chunk.setBlock(pos, dirt);
                continue;
            }

            if (density >= 25) {
                chunk.setBlock(pos, sand);
                continue;
            }

            if (density >= 15) {
                chunk.setBlock(pos, brick);
                continue;
            }

            if (density >= 10) {
                chunk.setBlock(pos, sand);
                continue;
            }

            if (density >= 5) {
                chunk.setBlock(pos, ice);
                continue;
            }

            if (density > 0) {
                chunk.setBlock(pos, glass);
                continue;
            }

            if (density == -1000 || density == -900 || density == -800 || density == -700 || density == -600 ||
                    density == -500 || density == -400 || density == -300 || density == -200 || density == -100 ||
                    density == -90 || density == -80 || density == -70 || density == -60 || density == -50 ||
                    density == -40 || density == -30 || density == -20 || density == -10 || density == -1) {
                chunk.setBlock(pos, ice);
                continue;
            }

            //fill with water
            if (density < 0) {
                // fill up terrain up to sealevel height with water or ice
                if (posY <= seaLevel) {         // either OCEAN or SNOW
                    chunk.setBlock(pos, water);
                    chunk.setLiquid(pos, waterLiquid);
                }
            }

        }
    }

}
