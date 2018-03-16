package org.terasology.core.emath;

public interface MathFormula {

    float compute(float x);
    float compute(float x, float y);
    float compute(float x, float y, float z);

}
