/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    private int countDown;
    private int power;
    private boolean readyToExplode;
    public Bomb(String sImageNamePNG, int power) {

        super(sImageNamePNG);
        countDown =0;
        this.power=power;
        isReadyToExplode(false);
    }
    public int getPower(){
        return power;
    }

    public int getCountDown(){
        return countDown;
    }
    public void setCountDown(int time){
        countDown =time;
    }
    
    public void autoDraw(){
        countDown += Consts.PERIOD;

        if(countDown == 80){
            this.bTransposable = false;//Bug aqui...  a bomba fica instransposable. ai nao pode ser explodida pela explosao de outra bomba...
        }else if(countDown >= Consts.TIMER_BOMB){
            this.setbKill(true);
            isReadyToExplode(true);
        }
        super.autoDraw();
    }

    public boolean isExplosable(ArrayList<Element> elements, Position p) {
        for (Element e : elements) {
            if (e.getPosition().equals(p)) {
                if (e.toString().equals("Wall")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBrick(ArrayList<Element> elements, Position p) {
        for (Element e : elements) {
            if (e.getPosition().equals(p)) {
                if (e.toString().equals("Brick")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getReadyToExplode() {
        return readyToExplode;
    }
    public void explodeIt(ArrayList<Element> elements){
        setbKill(true);
        //if(getCountDown()>= Consts.TIMER_BOMB){
        boolean[] explosionLimit = new boolean[]{true, true, true, true};
        BombFire f = new BombFire("explosion.png");

        f.setPosition(getPosition());
        Draw.getGameScreen().addElement(f);
        for (int i = 1; i <= getPower(); i++) {
            String expName;

            if (i == getPower())
                expName = "explosion-L.png";
            else
                expName = "explosion-horizontal.png";
            BombFire fb = new BombFire(expName);
            if (explosionLimit[0] && fb.setPosition(getPosition().getLine(), getPosition().getColumn() - i)) {
                Position p = new Position(getPosition().getLine(), getPosition().getColumn() - i);
                if (isExplosable(elements, p)) {
                    explosionLimit[0] = Draw.getGameScreen().isValidPosition(fb.getPosition());
                    Draw.getGameScreen().addElement(fb);
                } else {
                    explosionLimit[0] = false;
                }
            }

            if (i == getPower())
                expName = "explosion-R.png";
            else
                expName = "explosion-horizontal.png";
            BombFire fb2 = new BombFire(expName);
            if (explosionLimit[1] && fb2.setPosition(getPosition().getLine(), getPosition().getColumn() + i)) {
                Position p = new Position(getPosition().getLine(), getPosition().getColumn() + i);
                if (isExplosable(elements, p)) {
                    explosionLimit[1] = Draw.getGameScreen().isValidPosition(fb2.getPosition());
                    Draw.getGameScreen().addElement(fb2);
                } else {
                    explosionLimit[1] = false;
                }
            }

            if (i == getPower())
                expName = "explosion-U.png";
            else
                expName = "explosion-vertical.png";
            BombFire fb3 = new BombFire(expName);
            if (explosionLimit[2] && fb3.setPosition(getPosition().getLine() - i, getPosition().getColumn())) {
                Position p = new Position(getPosition().getLine() - i, getPosition().getColumn());
                if (isExplosable(elements, p)) {
                    explosionLimit[2] = Draw.getGameScreen().isValidPosition(fb3.getPosition());
                    Draw.getGameScreen().addElement(fb3);
                } else {
                    explosionLimit[2] = false;
                }
            }

            if (i == getPower())
                expName = "explosion-D.png";
            else
                expName = "explosion-vertical.png";
            BombFire fb4 = new BombFire(expName);
            if (explosionLimit[3] && fb4.setPosition(getPosition().getLine() + i, getPosition().getColumn())) {
                Position p = new Position(getPosition().getLine() + i, getPosition().getColumn());
                if (isExplosable(elements, p)) {
                    explosionLimit[3] = Draw.getGameScreen().isValidPosition(fb4.getPosition());
                    //explosionLimit[3] = !isBrick(elements,p);
                    Draw.getGameScreen().addElement(fb4);
                } else {
                    explosionLimit[3] = false;
                }
            }
        }
    }

    public void isReadyToExplode(boolean readyToExplode) {
        if(readyToExplode)
            countDown = Consts.TIMER_BOMB;
        this.readyToExplode = readyToExplode;
    }
}
