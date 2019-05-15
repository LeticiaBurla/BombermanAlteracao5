/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Screen.java
 *
 * Created on 03/03/2011, 18:28:20
 */
package Controller;

import Model.*;
import Auxiliar.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.logging.*;
import java.util.zip.*;
/**
 *
 * @author junio
 */
public class Screen extends javax.swing.JFrame implements MouseListener, KeyListener {

    private Bomberman bBomberman;
    private ArrayList<Element> eElements;
    private ArrayList<Element> fase1;
    private ArrayList<Element> fase2;
    private ArrayList<Element> fase3;
    private GameController gameController = new GameController();
    private Graphics graphics;
    /**
     * Creates new form tabuleiro
     */
    public Screen() {
        Draw.setScenario(this);
        initComponents();
        this.addMouseListener(this); /*mouse*/

        this.addKeyListener(this);   /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.CELL_SIDE+Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        eElements = new ArrayList<Element>(100);

        /*Cria eElements adiciona elementos*/
        bBomberman = new Bomberman("bomberman.png");
        bBomberman.setPosition(0, 0);
        this.addElement(bBomberman);

        //wont place monsters in "notHere" positions
        ArrayList<Position> notHere = new ArrayList<>();

        // set indestrutible wall
        for(int i=0;i<Consts.RES;i++){
            if(i%2!=0)
                for (int j=0;j<Consts.RES;j++){
                    if(j%2!=0) {
                        Wall wall = new Wall("wall.png");
                        wall.setPosition(i, j);
                        notHere.add(wall.getPosition());
                        this.addElement(wall);
                    }
            }
        }

        //set Bricks
        for(int i = 0; i < (Consts.RES);i++){
            for(int j = 0; j < (Consts.RES); j++){
                if(Math.random() < 0.35 && (i != 0 && j!= 0)){
                    if((i%2 == 0 || j%2 == 0) && (i != 0 && j != 10)){
                        Brick p = new Brick("brick.png");
                        p.setPosition(i,j);
                        notHere.add(p.getPosition());
                        this.addElement(p);
                    }
                }
            }
        }

        //create monsters
        Random rand =new Random();
        int numberOfMonsters=rand.nextInt(8);
        while( numberOfMonsters<4){
            numberOfMonsters =rand.nextInt(8);
        }
        for(int i=0;i<numberOfMonsters;i++) {
            Balloon monster1 = new Balloon("bichinho.png");
            int x = rand.nextInt(Consts.RES), y = rand.nextInt(Consts.RES);
            Position aux = new Position(x, y);
            while (!isValidPosition(aux)|| notHere.contains(aux)) {
                x = rand.nextInt(Consts.RES);
                y = rand.nextInt(Consts.RES);
                aux=new Position(x,y);
            }
            monster1.setPosition(x, y);
            notHere.add(monster1.getPosition());
            addElement(monster1);
        }



    }

    /**
     * Get player instance
     * @return Bomberman
     */
    public Bomberman getBomberman(){
        return bBomberman;
    }
    /**
     * get a game element in position
     * @param pos position
     * @return Element or null if not found
     */

    public ArrayList<Element> getElements(Position pos){
        ArrayList<Element> all = new ArrayList<>();
        for(Element el : eElements){
            if(el.getPosition().equals(pos))
                all.add(el);
        }
        return all;
    }

    /**
     * Check if a bomb could be placed
     * @return true could, false if could not
     */
    public boolean couldPlaceBomb(){
        int bombCount=0;
        for(Element el : eElements){
            if(el.toString().equals("Bomb"))
            {
                bombCount++;
                if(bombCount>=bBomberman.getBombs())
                    return false;
            }
        }
        return true;
    }

    /**
     * update non static game Hud elements
     */
    public void updateHUD(){
        //clear screen
        graphics.clearRect(Consts.CELL_SIDE,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        graphics.clearRect(Consts.CELL_SIDE*3,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        graphics.clearRect(Consts.CELL_SIDE*5,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        //lives
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getLives()),Consts.CELL_SIDE,  Consts.CELL_SIDE*12-5);
        //power
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getPower()),Consts.CELL_SIDE*3,  Consts.CELL_SIDE*12-5);
        //bombs
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getBombs()),Consts.CELL_SIDE*5,  Consts.CELL_SIDE*12-5);


    }

/*--------------------------------------------------*/
/*------Não se preocupe com o código a seguir-------*/
/*--------------------------------------------------*/
    public void addElement(Element aElement) {
        eElements.add(aElement);
    }

    public void removeElement(Element aElement) {
        eElements.remove(aElement);
    }

    public boolean isValidPosition(Position p){
        if(p.getColumn()>=Consts.RES)
            return false; //out of screen
        if(p.getLine()>=Consts.RES)
            return false; //out of screen
        return gameController.isValidPosition(this.eElements, p);
    }
    
    public Graphics getGraphicsBuffer(){
        return graphics;
    }
       
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        graphics = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);

        /*Desenha cenário*/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j <= Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "green.png");
                    graphics.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!this.eElements.isEmpty()) {
            this.gameController.drawEverything(eElements);
            this.gameController.processEverything(eElements);
        }
        //create game hud: static
        try{
            graphics.setFont(new Font("Arial", Font.PLAIN, 50));
            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "lifeUp.png");
            graphics.drawImage(newImage,0,  Consts.CELL_SIDE*11, null);

            newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "powerUp.png");
            graphics.drawImage(newImage,Consts.CELL_SIDE*2,  Consts.CELL_SIDE*11, null);


            newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "bombUp.png");
            graphics.drawImage(newImage,Consts.CELL_SIDE*4,  Consts.CELL_SIDE*11, null);


        }catch (IOException er){
            System.out.println(er.getMessage());
        }

        updateHUD();
        g.dispose();
        graphics.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            //this.eElements.clear();
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            /*try {
                File tanque = new File("c:\\temp\\POO.zip");
                FileInputStream canoOut = new FileInputStream(tanque);
                GZIPInputStream compactador = new GZIPInputStream(canoOut);
                ObjectInputStream serializador = new ObjectInputStream(compactador);
                this.eElements = (ArrayList<Element>)serializador.readObject();
                serializador.close();
            } catch (Exception ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            /*try {
                File tanque = new File("c:\\temp\\POO.zip");
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
                ObjectOutputStream serializador = new ObjectOutputStream(compactador);
                serializador.writeObject(this.eElements);
                serializador.flush();
                serializador.close();
            } catch (IOException ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            bBomberman.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            bBomberman.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            bBomberman.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            bBomberman.moveRight();
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(couldPlaceBomb()){
                Bomb b = new Bomb("bomba.png",getBomberman().getPower());
                b.setPosition(bBomberman.getPosition());
                this.addElement(b);
            }
        }
        if (!this.isValidPosition(bBomberman.getPosition())) {
            bBomberman.backToLastPosition();
        }

        this.setTitle("-> Cell: " + (bBomberman.getPosition().getColumn()) + ", "
                + (bBomberman.getPosition().getLine()));

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }

    public void mousePressed(MouseEvent e) {
        /* Clique do mouse desligado
         int x = eElements.getX();
         int y = eElements.getY();
     
         this.setTitle("X: "+ x + ", Y: " + y +
         " -> Cell: " + (y/Consts.CELL_SIDE) + ", " + (x/Consts.CELL_SIDE));
        
         this.bBomberman.getPosition().setPosition(y/Consts.CELL_SIDE, x/Consts.CELL_SIDE);
         */
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2015-1 - Adventures of lolo");
        setAutoRequestFocus(false);
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
