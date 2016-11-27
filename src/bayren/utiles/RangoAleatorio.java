/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bayren.utiles;

import java.util.Random;

/**
 *
 * @author Xosio
 */
public class RangoAleatorio extends Random {
     
    public int nextIncInc(int min, int max) {
        return nextInt(max - min + 1) + min;
    }

    public int nextExcInc(int min, int max) {
        return nextInt(max - min) + 1 + min;
    }

    public int nextExcExc(int min, int max) {
        return nextInt(max - min - 1) + 1 + min;
    }

    public int nextIncExc(int min, int max) {
        return nextInt(max - min) + min;
    }
    
}
