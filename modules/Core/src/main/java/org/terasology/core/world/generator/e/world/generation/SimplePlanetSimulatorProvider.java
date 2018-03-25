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
import org.terasology.core.world.generator.e.procedural.logic.SimplePlanetSimulator;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.Region3i;
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
public class SimplePlanetSimulatorProvider extends SimplePlanetSimulator implements FacetProvider {

    private static final Logger logger = LoggerFactory.getLogger(SimplePlanetSimulatorProvider.class);

    private boolean debug;

    /***
     * just copy noise values to density as they are.
     * that is, if you don't set functions to other values.
     */
    public SimplePlanetSimulatorProvider() {
        super();
        this.debug = false;
    }

    public SimplePlanetSimulatorProvider(boolean debug) {
        super();
        this.debug = debug;
    }

    /***
     * This is density value modification Class. it can grow or decrease values starting from origo.
     * It is useful for created planet like environment or other way limited environments.
     * you need to set up or down density functions to make this class to do something.
     * @param origoOffSet
     * @param densityFunction
     * @param densityMultifier
     */
    public SimplePlanetSimulatorProvider(int origoOffSet, int densityFunction, float densityMultifier) {
        super();
        super.origoOffSet = origoOffSet;
        super.densityFunction = densityFunction;
        super.densityMultiplier = densityMultifier;

        this.debug = false;
    }

    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet = region.getRegionFacet(InfiniteGenFacet.class);
        float density = 0;
        int y, x=0, z=0;

        long time = 0;
        if (debug)
            time = System.currentTimeMillis();

        Region3i area = region.getRegion();
        int Y = area.minY();//real universal Y coordinate

        for (y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
            for (x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
                for (z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {
                    density = facet.get(new Vector3i(x, y, z));

                    density = super.compute(density, x, Y, z);
                    facet.set(x, y, z, density);

                    if (facet.getMax() < density) {
                        facet.setMax(density);
                    } else if (facet.getMin() > density) {
                        facet.setMin(density);
                    }
                }
            }
            Y++;
        }
        if (debug) {
            logger.warn("chunk(" + area.center().x + "," + area.center().y + "," + area.center().z + ") processed in " + ((double) System.currentTimeMillis() - time) / 1000 + "s");
            logger.warn("\n Y:" + Y + "\n" + super.toString());
            logger.warn("density in :" + facet.get(new Vector3i(x-1, y-1, z-1)) + " out: " + density);
        }
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

