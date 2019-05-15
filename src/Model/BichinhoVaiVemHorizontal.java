/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;

/**
 *
 * @author Junio
 */
public class BichinhoVaiVemHorizontal extends Element implements Serializable{
    private boolean bRight;

    public BichinhoVaiVemHorizontal(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bRight = true;
    }
    public void autoDraw(){
        if(bRight)
            this.setPosition(pPosition.getLine(), pPosition.getColumn()+1);
        else
            this.setPosition(pPosition.getLine(), pPosition.getColumn()-1);

        super.autoDraw();
        bRight = !bRight;
    }
}
