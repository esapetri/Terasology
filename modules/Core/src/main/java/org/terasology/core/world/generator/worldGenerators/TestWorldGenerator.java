 
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

import org.terasology.core.world.generator.e.procedural.adapter.ValueMultiplicationAdapter;
import org.terasology.core.world.generator.e.world.generation.LandFormProvider;
import org.terasology.core.world.generator.e.world.generation.SimpleDensityProvider;
import org.terasology.core.world.generator.e.world.generation.SimplePlanetSimulatorProvider;
import org.terasology.core.world.generator.e.world.generation.facetProviders.Noise3DBaseTerainProvider;
import org.terasology.core.world.generator.e.world.generation.facetProviders.special.ValueMultiplyModifyTerainProvider;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.AirFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.FlatGroundFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.FillFormDefinition;
import org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic.HillsFormDefinition;
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

        //planet simulator
        SimplePlanetSimulatorProvider simplePlanetSimulatorProvider = new SimplePlanetSimulatorProvider(true);

        //simplePlanetSimulatorProvider.setOrigoOffSet(-1000);
        //simplePlanetSimulatorProvider.setDensityFunction(1);
        //simplePlanetSimulatorProvider.setDensityMultiplier(1000);

        simplePlanetSimulatorProvider.setUpDensityFunction(10);
        simplePlanetSimulatorProvider.setUpHeightMultiplier(0.01f);

        //simplePlanetSimulatorProvider.setDownDensityFunction(9);
        //simplePlanetSimulatorProvider.setDownHeightMultiplier(0.001f);
        simplePlanetSimulatorProvider.setDownDensityFunction(-1);
        simplePlanetSimulatorProvider.setDownHeightMultiplier(1f);

        //simplePlanetSimulatorProvider.setOrigoOffSet(-534);
        //simplePlanetSimulatorProvider.setUpHeightMultiplier(0.002f);
        //simplePlanetSimulatorProvider.setDownHeightMultiplier(0.008f);
        //simplePlanetSimulatorProvider.setDownDensityFunction(2);

        //density provider
        SimpleDensityProvider densityProvider = new SimpleDensityProvider(true);

        //Create world
        LandFormProvider landFormProvider =new LandFormProvider();
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
                        new Noise3DBaseTerainProvider(
                                new ValueMultiplicationAdapter(
                                        new BrownianNoise3D(new SimplexNoise(System.currentTimeMillis()), 4)
                                        ,0.25f
                                ),
                                //new Vector3f(0.008f, 0.008f, 0.008f), 0, 25, 0
                                new Vector3f(0.008f, 0.008f, 0.008f), 0, 1, 0
                        )
                )
                .addProvider(simplePlanetSimulatorProvider)
                .addProvider(new ValueMultiplyModifyTerainProvider(1000f))



                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProvider)
                .addRasterizer(new TestSolidRasterizer())
                .addPlugins();
    }
}