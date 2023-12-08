package org.TresEnRaya;

public class Square {
    private String type;

    public Square(){
        this.type = " ";
    }

    public Square(String turn){this.type = turn;}

    public boolean isEmpty(){
        return this.type.equals(" ");
    }

    public void mark(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public boolean equals(Square square){
        return this.type.equals(square.type);
    }
}