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
package org.terasology.core.world.generator.e.world.generation.landformDefinitions.basic;

import org.terasology.core.world.generator.e.procedural.adapter.AdditionAdapter;
import org.terasology.core.world.generator.e.world.generation.LandFormDefinition;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;

public class VoidFormDefinition extends LandFormDefinition implements Noise3D {

    public VoidFormDefinition(long seed) {
        super(0);
        this.maxDensity = -200f;
        this.minDensity = Float.MIN_VALUE;
        this.maxAltitude = Float.MAX_VALUE;
        this.minAltitude = Float.MIN_VALUE;
        this.maxTemperature = Float.MAX_VALUE;
        this.minTemperature = Float.MIN_VALUE;
        this.maxHumidity = Float.MAX_VALUE;
        this.minHumidity = Float.MIN_VALUE;

        this.setScoreOffset(-800f);

        this.noiseList.add(new SubSampledNoise3D(new AdditionAdapter(new SimplexNoise(seed), -1f),
                new Vector3f(0.02f, 0.02f, 0.02f), 4));
    }

}