/*
 * Copyright 2013 MovingBlocks
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
package org.terasology.core.world.generator.e.procedural.texture;

import org.terasology.core.emath.BitScrampler;
import org.terasology.core.emath.Statistics;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.random.FastRandom;

/**
 * 
 * @author Esereja
 */
public class StructureTextureV3 implements Noise2D, Noise3D {
	final private int RANDOMS_LENGHT=64;
	
	private int[] randoms;
	
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public StructureTextureV3(long seed) {
       FastRandom rand=new FastRandom(seed);
       randoms=new int[RANDOMS_LENGHT];
       for(int i=0;i<randoms.length;i++){
    	   randoms[i]=rand.nextInt(); 
       }
    }


    /**
     * 2D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin) {
    	int x=TeraMath.floorToInt(xin);
    	int y=TeraMath.floorToInt(yin);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x)%(randoms.length-1) ) 
        				        ]^x
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x+1)%(randoms.length-1) ) 
        				        ]^(x+1)
        				,5 ))
        		, xw );
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y)%(randoms.length-1) ) 
        				        ]^y
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y+1)%(randoms.length-1) ) 
        				        ]^(y+1)
        				,5 ))
        		, yw );

        double[] array=new double[3];
        array[0]=xn;
        array[1]=yn;
        double mean = Statistics.aritmeticMean(array);
        double variance = Statistics.variance(array, mean);
        double r=Statistics.median(array);
        
        return (float) (Math.sin(
        		(r)*3.141*2
        		));
    	} 

    /**
     * 3D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin, float zin) {
    	int x=TeraMath.floorToInt(xin);
    	int y=TeraMath.floorToInt(yin);
    	int z=TeraMath.floorToInt(zin);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x)%(randoms.length-1) ) 
        				        ]^x
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x+1)%(randoms.length-1) ) 
        				        ]^(x+1)
        				,5 ))
        		, xw );
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y)%(randoms.length-1) ) 
        				        ]^y
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y+1)%(randoms.length-1) ) 
        				        ]^(y+1)
        				,5 ))
        		, yw );

        double zn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(z)%(randoms.length-1) ) 
        				        ]^z
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(z+1)%(randoms.length-1) ) 
        				        ]^(z+1)
        				,5 ))
        		, zw );

        double[] array=new double[3];
        array[0]=xn;
        array[1]=yn;
        array[2]=zn;
        double mean = Statistics.aritmeticMean(array);
        double r=Statistics.median(array);
        
        return (float) (Math.sin(
        		(r)*3.141*2
        		));
    	} 


    /**
     * 4D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    public float noise(float xin, float yin, float zin, float win) {
    	int x=TeraMath.floorToInt(xin);
    	int y=TeraMath.floorToInt(yin);
    	int z=TeraMath.floorToInt(zin);
    	int w=TeraMath.floorToInt(win);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);
        double ww = zin - TeraMath.fastFloor(win);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x)%(randoms.length-1) ) 
        				        ]^x
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(x+1)%(randoms.length-1) ) 
        				        ]^(x+1)
        				,5 ))
        		, xw );
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y)%(randoms.length-1) ) 
        				        ]^y
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(y+1)%(randoms.length-1) ) 
        				        ]^(y+1)
        				,5 ))
        		, yw );

        double zn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(z)%(randoms.length-1) ) 
        				        ]^z
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(z+1)%(randoms.length-1) ) 
        				        ]^(z+1)
        				,5 ))
        		, zw );
        
        double wn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(w)%(randoms.length-1) ) 
        				        ]^w
        				,5 )),
        		BitScrampler.subZero(BitScrampler.oaatHash(
        				randoms[
        				        TeraMath.floorToInt(TeraMath.fastAbs(w+1)%(randoms.length-1) ) 
        				        ]^(w+1)
        				,5 ))
        		, zw );

        double[] array=new double[4];
        array[0]=xn;
        array[1]=yn;
        array[2]=zn;
        array[3]=wn;
        double mean = Statistics.aritmeticMean(array);
        double variance = Statistics.variance(array, mean);
        double r=Statistics.median(array);
        
        return (float) (Math.sin(
        		(r)*3.141*2
        		));
    	} 
    
}