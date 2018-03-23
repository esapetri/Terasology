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
package org.terasology.core.world.generator.e.procedural.adapter;

import org.terasology.core.world.generator.e.procedural.logic.SimplePlanetSimulator;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class SimplePlanetSimulationAdapter extends SimplePlanetSimulator implements Noise3D {

    private Noise3D noise3;

    public SimplePlanetSimulationAdapter(Noise3D noise) {
        super();
        this.noise3 = noise;
    }

    public SimplePlanetSimulationAdapter() {
        super();
        this.noise3 = null;
    }

    @Override
    public float noise(float x, float y,float z) {
        return super.compute(this.noise3.noise(x, y, z),x,y,z);
    }

    public Noise3D getNoise3() {
        return noise3;
    }

    public void setNoise3(Noise3D noise3) {
        this.noise3 = noise3;
    }

}
