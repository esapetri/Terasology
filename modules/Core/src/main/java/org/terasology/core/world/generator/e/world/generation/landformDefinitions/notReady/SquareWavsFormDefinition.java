/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.core.world.generator.e.world.generation.landformDefinitions.notReady;

import org.terasology.core.emath.GenMath;
import org.terasology.core.emath.MathFormula;
import org.terasology.core.world.generator.e.procedural.adapter.Scaling3DAdapter;
import org.terasology.core.world.generator.e.procedural.texture.FormulaAdapter;
import org.terasology.core.world.generator.e.world.generation.LandFormDefinition;
import org.terasology.core.world.generator.e.world.generation.facetProviders.Noise3DModifyTerainProvider;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class SquareWavsFormDefinition extends LandFormDefinition implements Noise3D {

    public SquareWavsFormDefinition(long seed) {
        super(0);
        this.maxDensity = Float.MAX_VALUE;
        this.minDensity = 200F;
        this.maxAltitude = Float.MAX_VALUE;
        this.minAltitude = Float.MIN_VALUE;
        this.maxTemperature = Float.MAX_VALUE;
        this.minTemperature = Float.MIN_VALUE;
        this.maxHumidity = Float.MAX_VALUE;
        this.minHumidity = Float.MIN_VALUE;

        this.setScoreOffset(-400f);

        this.noiseList.add(


                new Scaling3DAdapter(
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
                                        float part = (
                                                GenMath.primeDivisionSquareWave(x, 7, 1.4f, new int[]{2, 0})
                                                        + GenMath.primeDivisionSquareWave(z + GenMath.PRIMES[50], 7, 1.4f, new int[]{2, 0})
                                        );

                                        if(part <= 0)
                                            return part;

                                        float r =(float)
                                                (
                                                        ( part % 15 ) * TeraMath.fastAbs( Math.sin(y/10)) -5
                                                );

                                        return r;
                                    }
                                }
                        ),
                        new Vector3f(0.05f, 0.05f, 0.05f)
                )
        );
    }

}