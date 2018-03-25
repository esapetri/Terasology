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
package org.terasology.core.world.generator.e.world.generation;

import org.terasology.core.emath.GenMath;
import org.terasology.math.geom.Vector4f;
import org.terasology.utilities.procedural.Noise3D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author esereja
 */
public abstract class LandFormDefinition implements Noise3D {

    protected boolean optimize;

    protected List<Noise3D> noiseList;

    protected float density;
    protected float altitude;
    protected float temperature;
    protected float humidity;

    protected float scoreOffset;

    public LandFormDefinition() {
        this.altitude = Float.MAX_VALUE;
        this.density = 0;
        this.temperature = Float.MAX_VALUE;
        this.humidity = Float.MAX_VALUE;

        this.scoreOffset = 0;

        this.noiseList = new ArrayList<Noise3D>();
        this.optimize = false;
    }

    public LandFormDefinition(float density) {
        this.altitude = Float.MAX_VALUE;
        this.density = density;
        this.density = 0;
        this.temperature = Float.MAX_VALUE;
        this.humidity = Float.MAX_VALUE;
        this.scoreOffset = 0;

        this.noiseList = new ArrayList<Noise3D>();
        this.optimize = false;
    }

    public LandFormDefinition(float density, float altitude, float temperature, float humidity) {
        this.altitude = altitude;
        this.density = density;
        this.temperature = temperature;
        this.humidity = humidity;

        this.scoreOffset = 0;

        this.noiseList = new ArrayList<Noise3D>();
        this.optimize = false;
    }

    public LandFormDefinition(ArrayList<Noise3D> noiseList, float density, float altitude, float temperature, float humidity) {
        this.altitude = altitude;
        this.density = density;
        this.temperature = temperature;
        this.humidity = humidity;
        this.noiseList = noiseList;

        this.scoreOffset = 0;
        this.optimize = false;
    }


    public float noise(float x, float y, float z) {
        float n = 0;
        int i = 0;

        if (this.optimize)
            while (i < this.noiseList.size()) {
                n += this.noiseList.get(i).noise(x, y, z);
                if (n < 0)
                    break;
                i++;
            }

        else
            while (i < this.noiseList.size()) {
                n += this.noiseList.get(i).noise(x, y, z);
                i++;
            }

        return n;
    }

    /**
     * get Score of suitability of this terrain generator for these conditions.
     * zero is best suitability, smaller values mean worse suitability
     *
     * @param altitude
     * @param density
     * @param humidity
     * @param temperature
     * @return score. This tells how suitable is this noise for given coordinate. Smaller value is the better
     */
    public float getScore(final float altitude, final float density, final float humidity, final float temperature) {
        float alt = 0;
        float den = GenMath.D1taxiCapDistance(this.density, density);
        float hum = 0;
        float temp = 0;

        if (this.altitude != Float.MAX_VALUE)
            alt = GenMath.D1taxiCapDistance(this.altitude, altitude);

        if (this.humidity != Float.MAX_VALUE)
            hum = GenMath.D1taxiCapDistance(this.humidity, humidity);

        if (this.temperature != Float.MAX_VALUE)
            temp = GenMath.D1taxiCapDistance(this.temperature, temperature);

        return alt + den + hum + temp + this.scoreOffset;
    }

    /*-------------------------------------- Setters and Getters---------------------------------------------*/

    public void addNoise(Noise3D noise) {
        this.noiseList.add(noise);
    }

    public List<Noise3D> getNoiseList() {
        return noiseList;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setScoreOffset(float scoreOffset) {
        this.scoreOffset = scoreOffset;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

}