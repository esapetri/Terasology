/*
 * Copyright 2013 MovingBlocks
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
package org.terasology.core.world.generator.worldGenerators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.core.emath.MathFormula;
import org.terasology.core.world.generator.e.procedural.texture.FormulaAdapter;
import org.terasology.core.world.generator.e.world.generation.OldSimplePlanetSimulatorProvider;
import org.terasology.core.world.generator.e.world.generation.facetProviders.*;
import org.terasology.core.world.generator.facetProviders.*;
import org.terasology.core.world.generator.e.world.generation.rasterizers.TestSolidRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.registry.In;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

@RegisterWorldGenerator(id = "flat", displayName = "Flat")
public class FlatWorldGenerator extends BaseFacetedWorldGenerator {

    private static final Logger logger = LoggerFactory.getLogger(FlatWorldGenerator.class);
    private static int counter =0 ;

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public FlatWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        OldSimplePlanetSimulatorProvider densityProv = new OldSimplePlanetSimulatorProvider();
        densityProv.setOrigoOffSet(-534);
        densityProv.setUpHeightMultiplier(0.002f);
        densityProv.setUpDensityFunction(4);
        densityProv.setDownHeightMultiplier(0.008f);
        densityProv.setDownDensityFunction(1);
        densityProv.setDensityMultiplier(30);
        densityProv.setDensityFunction(1);
        return new WorldBuilder(worldGeneratorPluginLibrary)
                //.addProvider(new SeaLevelProvider(32))
                // height of 40 so that it is far enough from sea level so that it doesnt just create beachfront
                //
                //.addProvider(new PerlinHumidityProvider())
                //.addProvider(new PerlinSurfaceTemperatureProvider())
                //.addProvider(new BiomeProvider())
                //.addProvider(new SurfaceToDensityProvider())
                //.addProvider(new DefaultFloraProvider())
                //.addProvider(new DefaultTreeProvider())
                //.addRasterizer(new FloraRasterizer())
                //.addRasterizer(new TreeRasterizer())
                //.addRasterizer(new TestSolidRasterizer())
                //.addPlugins();

                .addProvider(new SeaLevelProvider(-10))

                //for spawn height
                .addProvider(new FlatSurfaceHeightProvider(200))

                /*
                .addProvider(
                        new Noise3DBaseTerainProvider(
                                new BrownianNoise3D(new SimplexNoise(System.currentTimeMillis()), 6),
                                new Vector3f(0.00080f, 0.0007f, 0.00080f), 0, 1, 0
                        )
                )
                */

                .addProvider(
                        new Noise3DBaseTerainProvider(
                                new BrownianNoise3D(new SimplexNoise(System.currentTimeMillis()), 8),
                                new Vector3f(0.00080f, 0.0007f, 0.00080f), 0, 1, 0
                        )
                )
                //.addProvider(new Noise3DBaseTerainProvider(new NullNoise(0), new Vector3f(1f, 1f, 1f), 0, 1, 0))

                .addProvider(
                        new Noise3DTerainProvider(
                                new FormulaAdapter(
                                        new MathFormula() {
                                            @Override
                                            public float compute(float x) {
                                                return 0;
                                            }

                                            @Override
                                            public float compute(float x, float y) {
                                                return 0;
                                            }

                                            @Override
                                            public float compute(float x, float y, float z) {
                                                float part =(float) (
                                                        //GenMath.pDSW( x, 7, 1.4f, new int[]{0, 2})
                                                        //                                                                + GenMath.primeDivisionSquareWave( z, 7, 1.4f, new int[]{0, 2})
                                                        Math.sin(x)
                                                );

                                                if(1987<counter){
                                                    logger.warn("val(X:"+x+",Y:"+y+",Z:"+z+"): " + part + "");
                                                    counter=0;
                                                }

                                                counter++;

                                                //if(part>4)
                                                    return part;
                                                //return 0;

                                                //part /= 4;

                                                //if(part <= 0)
                                                //    return part;
                                                /*
                                                float r =(float)
                                                        (
                                                                //part * TeraMath.fastAbs( Math.sin(y))
                                                                part *  Math.sin(y)
                                                        );

                                                return r;*/
                                            }
                                        }
                                ),
                                new Vector3f(0.05f, 0.05f, 0.05f), 0, 1, 0
                        )
                )

                /*.addProvider(
                        new Noise3DTerainProvider(
                                new BrownianNoise3D(new SimplexNoise(System.currentTimeMillis()), 6),
                                new Vector3f(0.00080f, 0.0007f, 0.00080f), 0, 1, 0
                        )
                )*/

                /*
                .addProvider(
                        new Noise3DModifyTerainProvider(
                                new CreateBoxNoiseTool(new Vector3f(-10, 100, -10), new Vector3f(10, 200, 10)),
                                new Vector3f(1f, 1f, 1f), 0, 1, 0
                        )
                )*/


                /*
               .addProvider(new Simplex3DTerainProvider(2,new Vector3f(0.0025f, 0.01f, 0.0025f),10,0,1.2,0))
               .addProvider(new Perlin3DTerainProvider(3,new Vector3f(0.00085f, 0.0007f, 0.00085f),9,0,0.8,0))
               .addProvider(new Perlin3DNoiseProvider(4,new Vector3f(0.0042f, 0.0042f, 0.0042f),0,-1.5,0))
               .addProvider(new Perlin3DNoiseProvider(5,new Vector3f(0.0015f, 0.0010f, 0.0015f),0,-1,0))
               .addProvider(new PerlinHumidityProvider())
                */

                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProv)
                .addRasterizer(new TestSolidRasterizer())
                .addPlugins();
    }
}
