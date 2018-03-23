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
package org.terasology.core.world.generator.e.world.generation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

/**
 * simulates planets surface by making mountains thinner higher you go and ground thicker deeper you go
 *
 * @author esereja
 */
@Updates(@Facet(InfiniteGenFacet.class))
public class SimpleDensityProvider implements FacetProvider {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDensityProvider.class);

    private boolean debug;

    /***
     * just copy noise values to density as they are.
     * that is, if you don't set functions to other values.
     */
    public SimpleDensityProvider() {
        this.debug = false;
    }

    public SimpleDensityProvider(boolean debug) {
        this.debug = debug;
    }

    public void setSeed(long seed) {
    }

    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet = region.getRegionFacet(InfiniteGenFacet.class);

        long time = 0;
        if (debug)
            time = System.currentTimeMillis();

        for (int y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
            for (int x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
                for (int z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {
                    float density = facet.get(new Vector3i(x, y, z));

                    facet.set(x, y, z, density);

                    if (facet.getMax() < density) {
                        facet.setMax(density);
                    } else if (facet.getMin() > density) {
                        facet.setMin(density);
                    }
                }
            }
        }

        if (debug)
            logger.debug("chunk processed in " + ((double) System.currentTimeMillis() - time) / 1000 + "s");
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}

