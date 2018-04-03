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
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * @author esereja
 */
@Produces(InfiniteGenFacet.class)
public abstract class BaseDensityProvider implements FacetProvider {

    protected long seed;

    /**
     */
    protected BaseDensityProvider() {
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(InfiniteGenFacet.class);
        InfiniteGenFacet facet = new InfiniteGenFacet(region.getRegion(), border);
        Region3i processRegion = facet.getWorldRegion();

        int y, x, z;

        //real universal Y coordinate
        int Y = processRegion.minY();
        int X;
        int Z;

        for (y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
            X = processRegion.minX();//real universal Y coordinate
            for (x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
                Z = processRegion.minZ();//real universal Y coordinate
                for (z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {

                    float density = this.calculate(X, Y, Z);
                    facet.set(x, y, z, density);

                    if (facet.getMax() < density) {
                        facet.setMax(density);
                    } else if (facet.getMin() > density) {
                        facet.setMin(density);
                    }
                    Z++;
                }
                X++;
            }
            Y++;
        }
        region.setRegionFacet(InfiniteGenFacet.class, facet);
    }

    protected abstract float calculate(int x, int y, int z);

}
