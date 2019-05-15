package Auxiliar;

import java.io.Serializable;

public class Position implements Serializable{
    private int line;
    private int column;
    
    private int lastLine;
    private int lastColumn;

    public Position(int line, int column){
        this.setPosition(line,column);
    }

    public boolean setPosition(int line, int column){
        if(line < 0 || line >= Auxiliar.Consts.RES)
            return false;
        if(column < 0 || column >= Auxiliar.Consts.RES)
            return false;
        
        lastLine = this.line;
        this.line = line;
        
        lastColumn = this.column;
        this.column = column;
        
        return true;
    }
    public boolean setPosition(Position p){
        return this.setPosition(p.getLine(), p.getColumn());
    }
    
    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }
    
    public boolean back(){
        return this.setPosition(lastLine, lastColumn);
    }

    public boolean equals(Object position){
        return (line == ((Position)position).getLine() && column == ((Position)position).getColumn());
    }

    public boolean copy(Position position){
        return this.setPosition(position.getLine(), position.getColumn());
    }
    
    public boolean moveUp(){
        return this.setPosition(this.getLine()-1, this.getColumn());
    }
    public boolean moveDown(){
        return this.setPosition(this.getLine()+1, this.getColumn());
    }
    public boolean moveRight(){
        return this.setPosition(this.getLine(), this.getColumn()+1);
    }
    public boolean moveLeft(){
        return this.setPosition(this.getLine(), this.getColumn()-1);
    }
}
