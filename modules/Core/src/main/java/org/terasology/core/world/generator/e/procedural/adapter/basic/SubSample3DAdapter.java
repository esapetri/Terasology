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

import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;

/**
 * @author Esereja
 */
public class SubSample3DAdapter implements Noise3D {

    private SubSampledNoise3D subSampledNoise;

    /**
     *
     * @param noise
     * @param zoom
     * @param sampleRate
     */
    public SubSample3DAdapter(Noise3D noise, Vector3f zoom, int sampleRate) {
        this.subSampledNoise = new SubSampledNoise3D(noise, zoom, sampleRate);
    }

    /**
     *
     * @param noise
     * @param zoom
     */
    @Deprecated
    public SubSample3DAdapter(Noise3D noise, Vector3f zoom) {
        this.subSampledNoise = new SubSampledNoise3D(noise, zoom, 4);
    }

    @Override
    public float noise(float x, float y,float z) {

    	return this.subSampledNoise.noise(x, y, z);
    }
  
}
