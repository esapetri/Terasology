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
package org.terasology.core.world.generator.e.world.generation.facetProviders.special;

import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

/**
 * @author esereja
 */
@Updates(@Facet(InfiniteGenFacet.class))
public class ValueMultiplyModifyTerainProvider implements FacetProvider {

    protected float value;

    public ValueMultiplyModifyTerainProvider(float value) {
        this.value = value;
    }

    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet = region.getRegionFacet(InfiniteGenFacet.class);

        float[] originalData = facet.getInternal();
        for (int i = 0; originalData.length > i; i++) {
            originalData[i] *= this.value;
            if (facet.getMax() < originalData[i]) {
                facet.setMax(originalData[i]);
            } else if (facet.getMin() > originalData[i]) {
                facet.setMin(originalData[i]);
            }
        }
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
