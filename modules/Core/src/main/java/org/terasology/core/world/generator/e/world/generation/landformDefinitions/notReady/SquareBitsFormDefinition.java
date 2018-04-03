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

import org.terasology.core.emath.BitScrampler;
import org.terasology.core.emath.GenMath;
import org.terasology.core.emath.MathFormula;
import org.terasology.core.world.generator.e.procedural.adapter.basic.SubSample3DAdapter;
import org.terasology.core.world.generator.e.procedural.texture.FormulaAdapter;
import org.terasology.core.world.generator.e.world.generation.LandFormDefinition;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class SquareBitsFormDefinition extends LandFormDefinition implements Noise3D {

    public SquareBitsFormDefinition(long seed) {
        super(0);
        this.noiseList.add(
                new SubSample3DAdapter(
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
                                        float part = 2 + (
                                                GenMath.primeDivisionSquareWave(BitScrampler.scrampleBits(x + GenMath.PRIMES[45]), 7, 1.4f, new int[]{0, 2})
                                                        + GenMath.primeDivisionSquareWave(BitScrampler.scrampleBits(z + GenMath.PRIMES[50]), 7, 1.4f, new int[]{0, 2})
                                        );

                                        part /= 4;

                                        //if(part <= 0)
                                        //    return part;

                                        float r = (float)
                                                (
                                                        part * TeraMath.fastAbs(Math.sin(y))
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