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
package org.terasology.core.world.generator.e.world.generation.facetProviders.basic;


import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.Produces;

/**
 * @author esereja
 */
@Produces(InfiniteGenFacet.class)
public class SimpleNoise3DBaseDensityProvider extends BaseDensityProvider implements FacetProvider {

    protected long seed;
    protected Noise3D noise;

    /**
     * @param noise
     */
    public SimpleNoise3DBaseDensityProvider(Noise3D noise) {
        this.noise = noise;
    }


    protected float calculate(int x, int y,int z){
        return this.noise.noise(x,y,z);
    }

    /*-------------------getters at setters------------------------*/

    public Noise3D getNoise() {
        return noise;
    }

    public void setNoise(Noise3D noise) {
        this.noise = noise;
    }


}
