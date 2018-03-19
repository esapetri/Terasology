package org.terasology.core.world.generator.e.procedural.tools;

import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

//FIXME not tested
public class CreateBoxNoiseTool implements Noise3D, Noise2D, Noise {

    Vector3f startPoints;
    Vector3f endPoints;

    /**
     * gives 0 in given area and 1 else where, useful for securing spawn to be free
     * use this noise in compination with "NoiseModifyTerainProvider"
     * @param startPoints
     * @param endPoints
     */
    public CreateBoxNoiseTool(Vector3f startPoints, Vector3f endPoints) {
        this.startPoints = startPoints;
        this.endPoints = endPoints;

    }


    @Override
    public float noise(int x, int y) {
        if (x > startPoints.x && x < endPoints.x
                && y > startPoints.y && y < endPoints.y
                )
            return 0;
        return 1;
    }

    @Override
    public float noise(int x, int y, int z) {
        if (x > startPoints.x && x < endPoints.x
                && y > startPoints.y && y < endPoints.y
                && z > endPoints.z && z < endPoints.z
                )
            return 0;
        return 1;
    }


    @Override
    public float noise(float x, float y) {
        if (x > startPoints.x && x < endPoints.x
                && y > startPoints.y && y < endPoints.y
                )
            return 0;
        return 1;
    }

    /**
     * @param x Position on the x-axis
     * @param y Position on the y-axis
     * @param z Position on the z-axis
     * @return
     */
    @Override
    public float noise(float x, float y, float z) {
        if (x > startPoints.x && x < endPoints.x
                && y > startPoints.y && y < endPoints.y
                && z > endPoints.z && z < endPoints.z
                )
            return 0;
        return 1;
    }
}
