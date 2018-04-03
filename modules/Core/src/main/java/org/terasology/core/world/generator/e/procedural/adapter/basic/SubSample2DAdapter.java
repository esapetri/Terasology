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

import org.terasology.math.geom.Vector2f;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SubSampledNoise2D;

/**
 * @author Esereja
 */
public class SubSample2DAdapter implements Noise2D {

    protected SubSampledNoise2D subSampledNoise;
    protected Vector2f zoom;
    protected int sampleRate;

    /**
     * @param noise
     * @param zoom
     * @param sampleRate
     */
    public SubSample2DAdapter(Noise2D noise, Vector2f zoom, int sampleRate) {
        this.subSampledNoise = new SubSampledNoise2D(noise, zoom, sampleRate);
        this.sampleRate = sampleRate;
        this.zoom = zoom;
    }

    /**
     * @param noise
     * @param zoom
     */
    @Deprecated
    public SubSample2DAdapter(Noise2D noise, Vector2f zoom) {
        this.subSampledNoise = new SubSampledNoise2D(noise, zoom, 4);
        this.sampleRate = 4;
        this.zoom = zoom;
    }

    @Override
    public float noise(float x, float y) {
        return this.subSampledNoise.noise(x, y);
    }

}
