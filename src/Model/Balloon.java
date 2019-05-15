/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Auxiliar.Consts;
import Auxiliar.Draw;

/**
 *
 * @author maykl
 */
public class Balloon extends Element{
   
    
    public Balloon(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal=true;
        this.bTransposable = true;
 
    }
    int iTimer; 


    public void autoDraw() {
        double iRandDirection;
        iTimer++;
        if (iTimer == Consts.PERIOD/8) {
            iTimer = 0;
            /*row a number between 0 e 1 and move it*/
            iRandDirection = Math.random();
            if (iRandDirection < 0.40) {
                this.moveDown();
            }else if (iRandDirection > 0.40 && iRandDirection < 0.8) {
                this.moveUp();
            } else if (iRandDirection > 0.8 && iRandDirection <0.9) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        }
        if (!Draw.isValidPosition(pPosition))
            this.getPosition().back();
        super.autoDraw();
    }

    public String toString(){
        return "Monster";
    }
  
}
