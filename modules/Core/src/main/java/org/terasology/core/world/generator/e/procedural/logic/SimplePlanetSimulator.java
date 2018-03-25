package org.terasology.core.world.generator.e.procedural.logic;

import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3f;

public class SimplePlanetSimulator {
    protected int origoOffSet;

    protected float densityMultiplier;
    protected float upHeightMultiplier;
    protected float downHeightMultiplier;

    protected int densityFunction;
    protected int upDensityFunction;
    protected int downDensityFunction;

    public SimplePlanetSimulator() {
        this.upHeightMultiplier = 0.1f;
        this.downHeightMultiplier = 0.1f;
        this.origoOffSet = 0;
        this.upDensityFunction = 0;
        this.downDensityFunction = 0;
        this.densityFunction = 0;
        this.densityMultiplier = 1;
    }

    public float compute(final float value, final Vector3f v) {
        return this.compute(value, v.x, v.y, v.z);
    }

    public float compute(final float value, final float x, final float y, final float z) {
        float density = value;

        switch (this.densityFunction) {
            case 1:
                density *= densityMultiplier;
            case 2:
                density *= density;
                density *= densityMultiplier;
            case 0:
            default:
        }

        if (y + origoOffSet < 0) {
            density = this.computeDensity(downDensityFunction, density, y, downHeightMultiplier);
        } else {
            density = this.computeDensity(upDensityFunction, density, y, upHeightMultiplier);
        }
        return density;

    }

    private float computeDensity(final int densityFunction, final float inDensity, final float y, final float heightMultiplier) {
        float density = inDensity;

        switch (densityFunction) {
            case 1:
                density = linearMultiplication(y, density, heightMultiplier);
                break;
            case 2:
                density = linearDivision(y, density, heightMultiplier);
                break;
            case 3:
                density = exponentialMultiplication(y, density, heightMultiplier);
                break;
            case 4:
                density = exponentialDivision(y, density, heightMultiplier);
                break;
            case 5:
                density = exp3Multiplication(y, density, heightMultiplier);
                break;
            case 6:
                density = exp3Division(y, density, heightMultiplier);
                break;
            case 7:
                density = linearAddition(y, density, heightMultiplier);
                break;
            case 8:
                density = linearSubtraction(y, density, heightMultiplier);
                break;
            case 9:
                density = bruteLinearAddition(y, density, heightMultiplier);
                break;
            case 10:
                density = bruteLinearSubtraction(y, density, heightMultiplier);
                break;
            case -1:
                density = heightMultiplier;
                break;
            case 0:
            default:
        }

        return density;
    }

    /*----------------------------logic-----------------------------------*/

    protected float linearMultiplication(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst * a);
        }
        return denst;
    }

    protected float linearDivision(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst / a);
        }
        return denst;
    }

    protected float exponentialMultiplication(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst * a * a);
        }
        return denst;
    }

    protected float exponentialDivision(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst / (a * a));
        }
        return denst;
    }

    protected float exp3Multiplication(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst * a * a * multiplifier);
        }
        return denst;
    }

    protected float exp3Division(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier + 1));
        if (a != 0) {
            return (float) (denst / (a * a * multiplifier));
        }
        return denst;
    }

    protected float linearAddition(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier));
        if (a != 0) {
            return (float) ((denst + denst * a));
        }
        return denst;
    }

    protected float linearSubtraction(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier));
        if (a != 0) {
            return (float) ((denst - denst * a));
        }
        return denst;
    }

    protected float bruteLinearAddition(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier));
        return (float) (denst + a);

    }

    protected float bruteLinearSubtraction(float y, float denst, float multiplifier) {
        double a = TeraMath.fastAbs(((y + origoOffSet) * multiplifier));
        return (float) (denst - a);
    }

    public String toString(){
        String s=new String();

        s += "origoOffSet: "+ this.origoOffSet +"\n";

        s += "densityFunction: "+ this.densityFunction +"\n";
        s += "densityMultiplier: "+ this.densityMultiplier +"\n";

        s += "upDensityFunction: "+ this.upDensityFunction +"\n";
        s += "upHeightMultiplier: "+ this.upHeightMultiplier +"\n";

        s += "downDensityFunction: "+ this.downDensityFunction +"\n";
        s += "downHeightMultiplier: "+ this.downHeightMultiplier +"\n";

        return s;
    }

    /*------------------------Getters and setters-------------------------------*/

    /**
     * @return the heightMultiplifier
     */
    public double getUpHeightMultiplier() {
        return upHeightMultiplier;
    }

    /**
     * decides how much density changes depending distance to origo. fractions work best.
     * formula used is = ((y+origoOffSet)*upHeightMultiplier+1)
     * so value is in the end added to 1
     *
     * @param heightMultiplifier the heightMultiplifier value to set
     */
    public void setUpHeightMultiplier(float heightMultiplifier) {
        this.upHeightMultiplier = heightMultiplifier;
    }

    /**
     * @return the downHeightMultiplier
     */
    public double getDownHeightMultiplier() {
        return downHeightMultiplier;
    }

    /**
     * decides how much density changes depending distance to origo. fractions work best.
     * formula used is = ((y+origoOffSet)*downHeightMultiplier+1)
     * so value is in the end added to 1
     *
     * @param downheightMultiplifier the downHeightMultiplier to set
     */
    public void setDownHeightMultiplier(float downheightMultiplifier) {
        this.downHeightMultiplier = downheightMultiplifier;
    }

    /**
     * @return the upDensityFunction
     */
    public int getUpDensityFunction() {
        return upDensityFunction;
    }

    /**
     * values should be int in between 0-6
     * 0 no function
     * 1 linear growth
     * 2 linear decrease
     * 3 exponential growth
     * 4 exponential decrease
     * 5 edited exponential growth
     * 6 edited exponential decrease
     * 7 linear increase(means that real mass increases)
     * 8 linear decrease(means that real mass decreases)
     *
     * @param upDensityFunction the upDensityFunction to set
     */
    public void setUpDensityFunction(int upDensityFunction) {
        this.upDensityFunction = upDensityFunction;
    }

    /**
     * @return the downDensityFunction
     */
    public int getDownDensityFunction() {
        return downDensityFunction;
    }

    /**
     * values should be int in between 0-6
     * 0 no function
     * 1 linear growth
     * 2 linear decrease
     * 3 exponential growth
     * 4 exponential decrease
     * 5 edited exponential growth
     * 6 edited exponential decrease
     * 7 linear increase(means that real mass increases)
     * 8 linear decrease(means that real mass decreases)
     *
     * @param downDensityFunction the downDensityFunction to set
     */
    public void setDownDensityFunction(int downDensityFunction) {
        this.downDensityFunction = downDensityFunction;
    }

    /**
     * Set off set off height multification origo
     *
     * @return the origoOffSet
     */
    public int getOrigoOffSet() {
        return origoOffSet;
    }

    /**
     * this offsets origo for density calculations. this value is added to Y axis value in calculations.
     * so +100 would mean that -100 is seen as origo for calculations.
     *
     * @param origoOffSet the origoOffSet to set
     */
    public void setOrigoOffSet(int origoOffSet) {
        this.origoOffSet = origoOffSet;
    }

    /**
     * this offsets origo for density calculations.
     * so +100 would mean that 100 is seen as origo for calculations.
     *
     * @param origoOffSet the origoOffSet to set
     */
    public void setOrigo(int origoOffSet) {
        this.origoOffSet = -origoOffSet;
    }

    /**
     * @return the densityMultiplier
     */
    public double getDensityMultiplier() {
        return densityMultiplier;
    }

    /**
     * multiplier for density function
     *
     * @param densityMultiplier the densityMultiplier to set
     */
    public void setDensityMultiplier(float densityMultiplier) {
        this.densityMultiplier = densityMultiplier;
    }

    /**
     * @return the densityFunction
     */
    public int getDensityFunction() {
        return densityFunction;
    }

    /**
     * this function decides how densities are calculated from noise.
     * default. they are just copied as they are.
     * 1. they are multiplied by densityMultiplier
     * 2. they are taken to power of 2 and multiplied.
     *
     * @param densityFunction the densityFunction to set
     */
    public void setDensityFunction(int densityFunction) {
        this.densityFunction = densityFunction;
    }

}
