package org.juego;

public abstract class Pieza {

    private int codId;
    public static int Rey = 0;
    public static int Reina = 1;
    public static int Torre = 2;
    public static int Alfil = 3;
    public static int Caballo = 4;
    public static int Peon = 5;

    private int color;
    public static int Blanco = 0;
    public static int Negro = 1;

    public Pieza(int color){
        this.color = color;
    }

    public int getColor(){
        return this.color;
    }

    public int getCodId() {
        return codId;
    }

    protected void setCodId(int codId) {
        this.codId = codId;
    }


    public boolean equals(Object object){
        boolean igual = false;

        if(this == object){
            igual = true;
        } else if (object instanceof Pieza) {
            Pieza aux = (Pieza) object;

            if(this.color == aux.getColor() && this.codId == aux.getCodId()){
                igual = true;
            }
        }

        return igual;
    }

    public abstract boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest);
}
