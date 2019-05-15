/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Junio
 */
public abstract class Element implements Serializable {

    protected ImageIcon iImage;
    protected Position pPosition;
    protected boolean bTransposable; /*Pode passar por cima?*/
    protected boolean bMortal;       /*Se encostar, o Bomberman morre?*/
    private boolean bKill;        /*No proximo processamento, retirar da lista de elementos*/

    public boolean isbKill() {
        return bKill;
    }

    public boolean isbMortal() {
        return bMortal;
    }
 
    protected Element(String sImageNamePng) {
        this.pPosition = new Position(1, 1);
        this.bTransposable = true;
        this.bMortal = false;
        this.setbKill(false);
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sImageNamePng);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Position getPosition() {
        /*TODO: Retirar este método para que objetos externos nao possam operar
         diretamente sobre a posição do Element*/
        return pPosition;
    }

    public boolean isbTransposable() {
        return bTransposable;
    }

    public void setbTransposable(boolean bTransposable) {
        this.bTransposable = bTransposable;
    }

    public void autoDraw(){
        Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
    }

    public boolean setPosition(int line, int column) {
        return pPosition.setPosition(line, column);
    }

    public boolean setPosition(Position p) {
        return pPosition.setPosition(p);
    }    
    
    public boolean moveUp() {
        return this.pPosition.moveUp();
    }

    public boolean moveDown() {
        return this.pPosition.moveDown();
    }

    public boolean moveRight() {
        return this.pPosition.moveRight();
    }

    public boolean moveLeft() {
        return this.pPosition.moveLeft();
    }
    public boolean interacts(Element aElement){
        return false;
    }

    public void changeAppearance(String sNewAppearance){
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNewAppearance);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String toString(){//avoid instanceof
        try{
            // String s = getClass().getEnclosingClass().getName();
            return getClass().getName().substring(6);//remove "Model." and return only classname
        }catch (Exception e){
            return getClass().getName();
        }
    }

    public void setbKill(boolean bKill) {
        this.bKill = bKill;
    }
}
