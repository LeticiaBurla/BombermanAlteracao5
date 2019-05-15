/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.*;

import Controller.Screen;
import Model.Bomberman;

/**
 *
 * @author junio
 */
public class Draw implements Serializable {
    static Screen tScreen;
    public static void setScenario(Screen aJScenario) {
        tScreen = aJScenario;
    }

    public static Screen getGameScreen() {
        return tScreen;
    }

    public static Graphics getGraphicsScreen() {
        return tScreen.getGraphicsBuffer();
    }
    
    public static void draw(ImageIcon iImage, int iColumn, int iLine) {
        iImage.paintIcon(tScreen, getGraphicsScreen(), iColumn * Consts.CELL_SIDE, iLine * Consts.CELL_SIDE);
    }
    
    public static boolean isValidPosition(Position p){
        return tScreen.isValidPosition(p);
    }
}
