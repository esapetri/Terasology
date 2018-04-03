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
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * @author esereja
 */
@Produces(InfiniteGenFacet.class)
public class SubSampledNoise3DBaseDensityProvider extends BaseDensityProvider implements FacetProvider {

    protected long seed;

    protected SubSampledNoise3D subSampledNoise3D;
    protected Vector3f zoom;
    protected int sampleRate;

    /**
     * @param noise
     * @param zoom
     */
    public SubSampledNoise3DBaseDensityProvider(Noise3D noise, Vector3f zoom, int sampleRate) {
        super();
        this.subSampledNoise3D = new SubSampledNoise3D(noise, zoom, sampleRate);
        this.sampleRate = sampleRate;
        this.zoom = zoom;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    protected float calculate(int x, int y, int z) {
        return this.subSampledNoise3D.noise(x,y,z);
    }

    /**
     * @return
     */
    public SubSampledNoise3D getSubSampledNoise3D() {
        return subSampledNoise3D;
    }

    public void setSubSampledNoise3D(Noise3D noise) {
        this.subSampledNoise3D = new SubSampledNoise3D(noise, this.zoom, this.sampleRate);
    }

    public Vector3f getZoom() {
        return zoom;
    }

    public void setZoom(Vector3f zoom) {
        this.zoom = zoom;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
}
