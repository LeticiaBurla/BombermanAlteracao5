/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

import java.io.Serializable;

/**
 *
 * @author junio
 */
public class Caveira extends Element implements Serializable{
    private int iContaIntervalos;
    
    public Caveira(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransposable = true;
        this.iContaIntervalos = 0;
    }

    public void autoDraw() {
        super.autoDraw();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == 6){
            this.iContaIntervalos = 0;
            Fogo f = new Fogo("fire.png");
            f.setPosition(pPosition.getLine(), pPosition.getColumn()+1);
            Draw.getGameScreen().addElement(f);
        }
    }    
}
