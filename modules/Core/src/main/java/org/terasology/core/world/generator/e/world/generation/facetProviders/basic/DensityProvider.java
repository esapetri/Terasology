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
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

/**
 * 
 * @author esereja
 */
@Updates(@Facet(InfiniteGenFacet.class))
public abstract class DensityProvider implements FacetProvider {

	protected long seed;

	@Deprecated
	public DensityProvider(){

	}

	@Override
	public void setSeed(long seed) {
		this.seed=seed;
	}


	public void process(GeneratingRegion region) {
		InfiniteGenFacet facet = region.getRegionFacet(InfiniteGenFacet.class);

		float[] originalData = facet.getInternal();
		float[] newData = createNoiseArray(facet.getWorldRegion());
		for (int i = 0; originalData.length > i; i++) {

			originalData[i] = joinValues(originalData[i],newData[i]);
			if (facet.getMax() < originalData[i]) {
				facet.setMax(originalData[i]);
			} else if (facet.getMin() > originalData[i]) {
				facet.setMin(originalData[i]);
			}
		}
	}

	private float[] createNoiseArray(Region3i region3i){
		int xDim = region3i.sizeX();
		int yDim = region3i.sizeY();
		int zDim = region3i.sizeZ();

		float[] fullData = new float[xDim * yDim * zDim];
		for (int z = 0; z < zDim; z++) {
			for (int y = 0; y < yDim; y++) {
				for (int x = 0; x < xDim; x++) {
					int actualX = x + region3i.minX();
					int actualY = y + region3i.minY();
					int actualZ = z + region3i.minZ();
					fullData[x + xDim * (y + yDim * z)] = this.calculate(actualX, actualY, actualZ);
				}
			}
		}
		return fullData;
	}

	protected abstract float calculate(int x,int y,int z);

	protected float joinValues(float originalValue,float newValue){
		return originalValue+ newValue;
	}


}
