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
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * 
 * @author Esereja
 */
public class ScifiTexture implements Noise2D, Noise3D {
	
	long seed;
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public ScifiTexture(long seed) {
       this.seed=seed;
    }


    /**
     * 2D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin) {
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(x,5)), BitScrampler.subZero(BitScrampler.oaatHash(x+1,5)), xw
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(y,5)), BitScrampler.subZero(BitScrampler.oaatHash(y+1,5)), yw
        		);

        return (float) (-1*Math.sin(
        		(BitScrampler.xor(xn, yn))*2.0943
        		));
    	}

    /**
     * 3D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin, float zin) {
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);
    	int z=s^TeraMath.floorToInt(zin);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(x,5)), BitScrampler.subZero(BitScrampler.oaatHash(x+1,5)), xw
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(y,5)), BitScrampler.subZero(BitScrampler.oaatHash(y+1,5)), yw
        		);

        double zn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(z,5)), BitScrampler.subZero(BitScrampler.oaatHash(z+1,5)), zw
        		);

        return (float) (-1*Math.sin(
        		(BitScrampler.xor(BitScrampler.xor(xn, yn),zn))*2.0943
        		));
    	}


    /**
     * 4D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    public float noise(float xin, float yin, float zin, float win) {
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);
    	int z=s^TeraMath.floorToInt(zin);
    	int w=s^TeraMath.floorToInt(win);
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);
        double ww = win - TeraMath.fastFloor(win);

        double xn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(x,2)), BitScrampler.subZero(BitScrampler.oaatHash(x+1,2)), xw
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(y,2)), BitScrampler.subZero(BitScrampler.oaatHash(y+1,2)), yw
        		);

        double zn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(z,2)), BitScrampler.subZero(BitScrampler.oaatHash(z+1,2)), zw
        		);
        
        double wn = TeraMath.lerp(
        		BitScrampler.subZero(BitScrampler.oaatHash(w,2)), BitScrampler.subZero(BitScrampler.oaatHash(w+1,2)), ww
        		);

        return (float) (Math.sin(
        		(BitScrampler.xor(yn,BitScrampler.xor(xn,BitScrampler.xor(zn,wn))) )*2.0943
        		));     
    	}

}
