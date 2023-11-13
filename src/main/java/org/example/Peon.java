package org.example;

public class Peon extends Pieza{

    private boolean moved;

    public Peon(int color) {
        super(color);
        this.setCodId(Pieza.Peon);
        this.moved = false;
    }

    /*
    * Pre: codPieza es el identificador de la pieza que se puede obtener de los valores
    * estaticos de la clase Pieza
    * Post: Devuelve true si el peon se convierte en otra pieza a eleccion del usuario a excepcion del
    * rey. En caso de que codPieza corresponda al rey o no corresponda a ninguna pieza devuelve false
     */
    public boolean evolve(int codPieza){
        boolean b = false;

        if(0 < codPieza && codPieza < 5){
            this.setCodId(codPieza);
            b = true;
        }
        return b;
    }

    public boolean hasMoved(){
        return this.moved;
    }

    public void moved(){
        this.moved = true;
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg,Casilla casillaDest) {
        boolean move = false;
        boolean adelante = !casillaDest.estaOcupada() && (casillaOrg.getFila() + 1 == casillaDest.getFila() || (casillaOrg.getFila() + 2 == casillaDest.getFila() && !this.moved)) && casillaOrg.getColumna() == casillaDest.getColumna();
        boolean capturar = casillaOrg.getFila() + 1 == casillaDest.getFila() && (casillaOrg.getColumna() + 1 == casillaDest.getColumna() || casillaOrg.getColumna() - 1 == casillaDest.getColumna()) && casillaDest.estaOcupada() && this.getColor() != casillaDest.getColorPieza();

        if (adelante || capturar){
            move = true;
        }

        return move;
    }
}
