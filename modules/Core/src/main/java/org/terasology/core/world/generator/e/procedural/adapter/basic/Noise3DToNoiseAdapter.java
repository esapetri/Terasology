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
package org.terasology.core.world.generator.e.procedural.adapter.basic;

import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class Noise3DToNoiseAdapter implements Noise3D, Noise2D, Noise {

    private Noise3D noise3d;

    /**
     * @param noise
     */
    public Noise3DToNoiseAdapter(Noise3D noise) {
        this.noise3d = noise;
    }


    @Override
    public float noise(float x, float y, float z) {
        return this.noise3d.noise(x, y, z);
    }

    @Override
    public float noise(float x, float y) {
        return this.noise3d.noise(x, y, 1);
    }

    @Override
    public float noise(int x, int y) {
        return this.noise3d.noise(x, y, 0);
    }

    @Override
    public float noise(int x, int y, int z) {
        return this.noise3d.noise(x, y, z);
    }

}
