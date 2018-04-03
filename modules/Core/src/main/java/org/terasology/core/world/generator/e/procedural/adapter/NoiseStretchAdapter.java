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

import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class NoiseStretchAdapter implements Noise3D {

    private Noise3D noise3d;
    private char axel;
    private int start;
    private int stop;

    public NoiseStretchAdapter(Noise3D noise) {
        this.noise3d = noise;
        this.axel = 'y';
        this.start = 0;
        this.stop = 0;
    }

    /**
     * @param noise
     */
    public NoiseStretchAdapter(Noise3D noise, char axel, int start, int stop) {
        this.noise3d = noise;
        this.axel = axel;
        this.start = start;
        this.stop = stop;
    }


    @Override
    public float noise(float x, float y, float z) {
        if (this.start == this.stop) {
            return this.noise3d.noise(x, y, z);
        }

        float value = 0;

        if (this.axel == 'x')
            value = x;

        if (this.axel == 'y')
            value = y;

        if (this.axel == 'z')
            value = z;

        //bigger than start value
        if (this.start < value) {
            //smaller than stop value
            if (value < this.stop) {

                if (this.axel == 'x')
                    return this.noise3d.noise(this.start, y, z);

                if (this.axel == 'y')
                    return this.noise3d.noise(x, this.start, z);

                if (this.axel == 'z')
                    return this.noise3d.noise(x, y, this.start);

            //bigger than stop value
            } else {//FIXME coordinate shift might not work correctly in both negative and positive side of origin
                if (this.axel == 'x')
                    return this.noise3d.noise(x - (this.stop - this.start), y, z);

                if (this.axel == 'y')
                    return this.noise3d.noise(x, y - (this.stop - this.start), z);

                if (this.axel == 'z')
                    return this.noise3d.noise(x, y, z - (this.stop - this.start));
            }
        //smaller than start value
        }
        return this.noise3d.noise(x, y, z);
    }


    /*-------------------------------------- Getters and Setters------------------------------------------*/

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public char getAxel() {
        return axel;
    }

    public void setAxel(char axel) {
        this.axel = axel;
    }


}
