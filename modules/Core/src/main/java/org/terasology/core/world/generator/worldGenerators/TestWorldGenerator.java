 
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

import org.terasology.core.world.generator.e.procedural.adapter.NoiseMultiplicationAdapter;
import org.terasology.core.world.generator.e.procedural.adapter.SimplePlanetSimulationAdapter;
import org.terasology.core.world.generator.e.world.generation.SimpleDensityProvider;
import org.terasology.core.world.generator.e.world.generation.facetProviders.Noise3DBaseTerainProvider;
import org.terasology.core.world.generator.e.world.generation.rasterizers.TestSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.*;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.registry.In;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

/**
 *
 */
@RegisterWorldGenerator(id = "testworldgenerator", displayName = "TestWorldGenerator", description = "test flat world generator")
public class TestWorldGenerator extends BaseFacetedWorldGenerator {
    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public TestWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        SimplePlanetSimulationAdapter planetSimulationAdapter = new SimplePlanetSimulationAdapter(
                new NoiseMultiplicationAdapter(
                        new BrownianNoise3D(new SimplexNoise(System.currentTimeMillis()), 4),
                        new SimplexNoise(System.currentTimeMillis() + 1)));

        //planetSimulationAdapter.setOrigoOffSet(-534);
        planetSimulationAdapter.setOrigoOffSet(-2000);
        //planetSimulationAdapter.setUpHeightMultiplier(0.002f);
        planetSimulationAdapter.setUpHeightMultiplier(0.02f);
        planetSimulationAdapter.setUpDensityFunction(4);
        planetSimulationAdapter.setDownHeightMultiplier(0.012f);
        //planetSimulationAdapter.setDownHeightMultiplier(0.008f);
        //planetSimulationAdapter.setDownDensityFunction(2);
        planetSimulationAdapter.setDownDensityFunction(3);
        planetSimulationAdapter.setDensityMultiplier(30);
        planetSimulationAdapter.setDensityFunction(1);

        SimpleDensityProvider densityProvider = new SimpleDensityProvider(true);

        return new WorldBuilder(worldGeneratorPluginLibrary)

                .addProvider(new SeaLevelProvider(-10))

                //for spawn height
                .addProvider(new FlatSurfaceHeightProvider(200))

                //base noise
                .addProvider(
                        new Noise3DBaseTerainProvider(
                                planetSimulationAdapter,
                                //new Vector3f(0.008f, 0.008f, 0.008f), 0, 25, 0
                                new Vector3f(0.008f, 0.008f, 0.008f), 0, 25, 0
                        )
                )

                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProvider)
                .addRasterizer(new TestSolidRasterizer())
                .addPlugins();
    }
}