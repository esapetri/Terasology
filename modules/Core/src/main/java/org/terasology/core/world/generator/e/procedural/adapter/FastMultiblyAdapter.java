package org.terasology.core.world.generator.e.procedural.adapter;

import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

public class FastMultiblyAdapter implements Noise3D, Noise2D {

    private Noise3D noise31;
    private Noise2D noise21;

    private Noise3D noise32;
    private Noise2D noise22;


    /**
     * @param noise1
     * @param noise2
     */
    public FastMultiblyAdapter(Noise3D noise1, Noise3D noise2) {
        this.noise31 = noise1;
        this.noise32 = noise2;
    }

    /**
     * @param noise1
     * @param noise2
     * @param b
     */
    public FastMultiblyAdapter(Noise2D noise1, Noise2D noise2, byte b) {
        this.noise21 = noise1;
        this.noise22 = noise2;
    }


    @Override
    public float noise(float x, float y, float z) {
        float result = this.noise31.noise(x, y, z);
        if (result > 0)
            return result * this.noise32.noise(x, y, z);
        return result;
    }

    @Override
    public float noise(float x, float y) {
        float result = this.noise21.noise(x, y);
        if (result > 0)
            return result * this.noise22.noise(x, y);
        return result;
    }
}
