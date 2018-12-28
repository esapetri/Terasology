 
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

import org.terasology.core.world.generator.e.procedural.adapter.AxelValueDipAdapter;
import org.terasology.core.world.generator.e.procedural.adapter.NoiseStretchAdapter;
import org.terasology.core.world.generator.e.procedural.adapter.basic.ScalingAdapter;
import org.terasology.core.world.generator.e.procedural.adapter.basic.ValueAdditionAdapter;
import org.terasology.core.world.generator.e.world.generation.LandFormProvider;
import org.terasology.core.world.generator.e.world.generation.SimpleDensityProvider;
import org.terasology.core.world.generator.e.world.generation.SimplePlanetSimulatorProvider;
import org.terasology.core.world.generator.e.world.generation.facetProviders.basic.SimpleNoise3DBaseDensityProvider;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.AirFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.FlatGroundFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.FillFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.HillsFormDefinition;
import org.terasology.core.world.generator.e.world.generation.rasterizers.TestSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.*;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.registry.In;
import org.terasology.utilities.procedural.PerlinNoise;
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

        //planet simulator
        SimplePlanetSimulatorProvider simplePlanetSimulatorProvider = new SimplePlanetSimulatorProvider(true);

        //simplePlanetSimulatorProvider.setOrigoOffSet(1000);
        //simplePlanetSimulatorProvider.setDensityFunction(1);

        simplePlanetSimulatorProvider.setUpDensityFunction(8);
        /*
         * value     edge
         * 0.00001 -  100 000
         * 0.0006 -  1700
         * 0.0004 -  2500
         * 0.0002 -  5000
         * 0.00015 - 7500
         * 0.0001 -  10 000
         * 0.001 -   1000
         * 0.01  -   100
         * 0.1   -   10
         * 1     -   1
         * */
        simplePlanetSimulatorProvider.setUpHeightMultiplier(0.0001f);

        simplePlanetSimulatorProvider.setDownDensityFunction(7);
        simplePlanetSimulatorProvider.setDownHeightMultiplier(0.0001f);

        //simplePlanetSimulatorProvider.setOrigoOffSet(-534);
        //simplePlanetSimulatorProvider.setUpHeightMultiplier(0.002f);

        //density provider
        SimpleDensityProvider densityProvider = new SimpleDensityProvider(true);

        //Create world
        LandFormProvider landFormProvider = new LandFormProvider();
        //basic forms
        landFormProvider.addNoise(new FillFormDefinition(landFormProvider.getSeed()));
        landFormProvider.addNoise(new AirFormDefinition());
        landFormProvider.addNoise(new FlatGroundFormDefinition(landFormProvider.getSeed()));

        //more detailed ones
        landFormProvider.addNoise(new HillsFormDefinition(landFormProvider.getSeed()));


        return new WorldBuilder(worldGeneratorPluginLibrary)

                .addProvider(new SeaLevelProvider(-10))

                //for spawn height
                .addProvider(new FlatSurfaceHeightProvider(0))

                //base noise
                .addProvider(
                        new SimpleNoise3DBaseDensityProvider(
                                new ValueAdditionAdapter(new AxelValueDipAdapter(new NoiseStretchAdapter(
                                        /*
                                        new SubSample3DAdapter(
                                                new PerlinNoise(System.currentTimeMillis()),
                                                //new Vector3f(0.008f, 0.008f, 0.008f), 0, 1, 0  //visible structure
                                                new Vector3f(0.001f, 0.001f, 0.001f),
                                                4
                                        )
                                        */

                                        new ScalingAdapter(
                                                new PerlinNoise(System.currentTimeMillis()),
                                                //new Vector3f(0.008f, 0.008f, 0.008f), 0, 1, 0  //visible structure
                                                new Vector3f(0.001f, 0.001f, 0.001f)
                                        )

                                        ,
                                        'y',
                                        100,
                                        1000
                                ),
                                        'z',
                                        110,
                                        210),
                                        -1)
                        )
                )
                .addProvider(simplePlanetSimulatorProvider)
                //.addProvider(new ValueMultiplyModifyTerainProvider(1000f))


                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProvider)
                .addRasterizer(new TestSolidRasterizer())
                .addPlugins();
    }
}