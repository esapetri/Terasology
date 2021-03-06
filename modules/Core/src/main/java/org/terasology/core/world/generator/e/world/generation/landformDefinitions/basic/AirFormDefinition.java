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

import org.terasology.core.world.generator.e.procedural.noise.NullNoise;
import org.terasology.core.world.generator.e.world.generation.LandFormDefinition;
import org.terasology.core.world.generator.e.world.generation.facets.InfiniteGenFacet;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class AirFormDefinition extends LandFormDefinition implements Noise3D {

    public AirFormDefinition() {
        super(-1000);

        this.noiseList.add(
                new NullNoise(-1)
        );
    }

}