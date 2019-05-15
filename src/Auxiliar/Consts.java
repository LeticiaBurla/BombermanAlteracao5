/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auxiliar;

import java.io.File;

/**
 *
 * @author Junio
 */ 
public class Consts {
    //game
    public static final int CELL_SIDE = 50;
    public static final int RES = 11;
    public static final int PERIOD = 80;   
    public static final String PATH = File.separator+"imgs"+File.separator;
    public static final int TIMER = 1;

    //bomberman
    public static final int INVENCIBILITY_TIME = 3;

    //itens
    public static final int TIMER_BOMB = 1760; //keep multiple of const PERIOD
    public static final int BOMB_PROBABILITY = 30;// spaw probability in percent
    public static final int POWERUP_PROBABILITY = 30;// spaw probability in percent
    public static final int LIFEUP_PROBABILITY = 10;// spaw probability in percent

    // number max per stage
    public static final int MAX_POWERUP_STAGE = 4;
    public static final int MAX_BOMB_STAGE = 4;
    public static final int MAX_LIFEUP_STAGE = 1;

    public enum DIRECTION{
        UP,RIGHT,LEFT,DOWN;
    }
}

