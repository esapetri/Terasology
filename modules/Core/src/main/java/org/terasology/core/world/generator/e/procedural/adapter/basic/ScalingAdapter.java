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

import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector2f;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class ScalingAdapter implements Noise3D,Noise2D,Noise {

    private Noise3D noise3d;
    private Noise2D noise2d;

    protected Vector3f scale3d;
    protected Vector2f scale2d;


    /**
     *
     * @param noise
     */
    public ScalingAdapter(Noise3D noise, Vector3f scale) {
        this.noise3d = noise;
        this.scale3d =scale;
    }

    /**
     *
     * @param noise
     * @param scale2d
     */
    public ScalingAdapter(Noise2D noise, Vector2f scale2d) {
        this.noise2d = noise;
        this.scale2d =scale2d;
    }


    @Override
    public float noise(float x, float y,float z) {
    	return this.noise3d.noise(x*this.scale3d.x, y*this.scale3d.y, z*this.scale3d.z);
    }

    @Override
    public float noise(float x, float y) {
		return this.noise2d.noise(x*this.scale2d.x, y*this.scale2d.y);
    }

    @Override
    public float noise(int x, int y) {
        return this.noise(x,y);
    }

    @Override
    public float noise(int x, int y, int z) {
        return this.noise(x,y,z);
    }

}
