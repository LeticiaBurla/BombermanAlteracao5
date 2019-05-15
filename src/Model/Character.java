package Model;

import Auxiliar.Consts;

public class Character extends Element {

    private Consts.DIRECTION direction;

    public Character(String sNomeImagePNG) {
        super(sNomeImagePNG);
        direction=Consts.DIRECTION.DOWN;
    }

    public Consts.DIRECTION getDirection(){
        return direction;
    }
    public void setDirection(Consts.DIRECTION direction){
        this.direction = direction;
    }


}

