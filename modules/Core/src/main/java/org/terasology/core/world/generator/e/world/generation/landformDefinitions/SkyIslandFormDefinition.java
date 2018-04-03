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

package org.terasology.core.world.generator.e.world.generation.landformDefinitions;
import org.terasology.core.world.generator.e.procedural.adapter.basic.ModulusAdapter;
import org.terasology.core.world.generator.e.procedural.adapter.basic.ValueMultiplicationAdapter;
import org.terasology.core.world.generator.e.world.generation.OldLandFormDefinition;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.*;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class SkyIslandFormDefinition extends OldLandFormDefinition implements Noise3D {

	public SkyIslandFormDefinition(Long seed){
    	super(100);
    	this.maxDensity=100f;
    	this.minDensity=-300;
    	this.maxAltitude=8000f;
    	this.minAltitude=1000f;
    	this.maxTemperature=50f;
    	this.minTemperature=-50;	
    	this.maxHumidity=Float.MAX_VALUE;
    	this.minHumidity=Float.MIN_VALUE;

    	this.noiseList.add(new SubSampledNoise3D( new BrownianNoise3D(new SimplexNoise(seed),10),
    			new Vector3f(0.0025f, 0.01f, 0.0025f),4
    			)
    	);
    	this.noiseList.add(new ModulusAdapter(
    			new ValueMultiplicationAdapter(
    			new SubSampledNoise3D( new BrownianNoise3D(new PerlinNoise(seed),9),
    			new Vector3f(0.00085f, 0.0007f, 0.00085f),4),
    			2),
    			1)
    	);
    	this.noiseList.add(new ValueMultiplicationAdapter(
    			new SubSampledNoise3D(
    			new PerlinNoise(seed),
    			new Vector3f(0.0012f, 0.0012f, 0.0012f),4),
    			3)
    	);
    	this.noiseList.add(new ValueMultiplicationAdapter(
    			new SubSampledNoise3D(
    			new PerlinNoise(seed),
    			new Vector3f(0.0001f, 0.0001f, 0.0001f),4),
    			1.5f)
    	);
    	
    }
    
}