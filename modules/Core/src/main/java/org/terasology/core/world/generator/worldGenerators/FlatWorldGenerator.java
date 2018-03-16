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

import org.terasology.core.emath.GenMath;
import org.terasology.core.emath.MathFormula;
import org.terasology.core.world.generator.e.procedural.texture.FormulaAdapter;
import org.terasology.core.world.generator.e.world.generation.facetProviders.*;
import org.terasology.core.world.generator.facetProviders.*;
import org.terasology.core.world.generator.e.world.generation.TestSolidRasterizer;
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

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public FlatWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        SimplePlanetSimulatorProvider densityProv =new SimplePlanetSimulatorProvider();
        densityProv.setOrigoOffSet(-534);
        densityProv.setUpHeightMultiplifier(0.002f);
        densityProv.setUpDensityFunction(4);
        densityProv.setDownHeightMultiplifier(0.008f);
        densityProv.setDownDensityFunction(1);
        densityProv.setDensityMultifier(30);
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

                .addProvider(new FlatSurfaceHeightProvider(40))


                .addProvider(
                        new Noise3DBaseTerainProvider(
                                new BrownianNoise3D(new SimplexNoise( System.currentTimeMillis() ),6),
                                new Vector3f(0.00080f, 0.0007f, 0.00080f),0,1,0
                        )
                )

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
                                                return (float)
                                                        (
                                                                GenMath.primeDivisionSquareWave(x,7,1.4f,new int[]{2,0})
                                                                *Math.sin(y/10)
                                                                *GenMath.primeDivisionSquareWave(z,7,1.4f,new int[]{2,0})
                                                        );
                                            }
                                        }
                                ),
                                new Vector3f(0.5f, 0.5f, 0.5f),0,1,0
                        )
                )

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
