package org.example;

public class Casilla {

    private int fila;
    private int columna;
    private Pieza pieza;

    public Casilla(int fila,int columna){
        this.fila = fila;
        this.columna = columna;
        this.pieza = null;
    }

    public boolean estaOcupada(){
        return pieza != null;
    }

    public void ocuparCasilla(Pieza pieza){
        this.pieza = pieza;
    }

    public void desocuparCasilla(){
        this.pieza = null;
    }

    public int getFila(){
        return this.fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public Pieza getPieza(){
        return this.pieza;
    }

    public int getColorPieza(){
        return this.pieza.getColor();
    }

    public boolean equals(Object object){
        boolean igual = false;

        if(this == object){
            igual = true;
        } else if (object instanceof Casilla) {
            Casilla aux = (Casilla) object;

            if(this.fila == aux.fila && this.columna == aux.columna && this.pieza.equals(aux.pieza)){
                igual = true;
            }
        }

        return igual;
    }
}
