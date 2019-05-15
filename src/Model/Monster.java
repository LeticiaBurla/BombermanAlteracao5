/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
//import javafx.geometry.Pos;

import java.util.Random;

/**
 *
 * @author ICMC
 */
public class Monster extends Character {

    private int timer;

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.timer =0;
        changeDirection();
    }

    //Randonly change moster's direction
    private void changeDirection(){
        Random r = new Random();
        Consts.DIRECTION nextDirection;
        int iRandDirection = r.nextInt(4);

        if (iRandDirection == 0) {
            nextDirection=Consts.DIRECTION.DOWN;
        } else if (iRandDirection == 1) {
            nextDirection=Consts.DIRECTION.LEFT;
        } else if (iRandDirection == 2) {
            nextDirection=Consts.DIRECTION.UP;
        } else {
            nextDirection=Consts.DIRECTION.RIGHT;
        }
        setDirection(nextDirection);
    }

    private void move(){
        switch (getDirection()){
            case DOWN:
                moveDown();
                break;
            case UP:
                moveUp();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }

        Position next = getPosition();
        if (!Draw.isValidPosition(next)) {
            this.getPosition().back();
            changeDirection();
        }
    }

    //move to a certain direction for a certain squares until cant move.
    public void autoDraw() {
        int monsterPeriod=Consts.PERIOD/8;
        timer++;
        if (timer == monsterPeriod) {
            timer = 0;
            move();
        }
        super.autoDraw();
    }

}
