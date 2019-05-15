/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Draw;

import java.util.ArrayList;

/**
 *
 * @author ICMC
 */
public class LifeUp extends Item {

    public LifeUp(String sImageNamePNG) {
        super(sImageNamePNG);
    }
/*
    public void autoDraw(){
        super.autoDraw();
        ArrayList<Element> posElements= Draw.getGameScreen().getElements(pPosition);

        if(posElements.size()>1){
            for(Element el : posElements){
                if(el instanceof BombFire){//break this brick
                    Draw.getGameScreen().removeElement(this);
                }
            }
        }
    }*/
    
}
