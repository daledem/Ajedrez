package org.example;

public class Caballo extends Pieza{

    public Caballo(int color) {
        super(color);
        this.setCodId(Pieza.Caballo);
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest) {
        boolean move = false;
        boolean salto1 = casillaOrg.getFila() + 2 == casillaDest.getFila() && (casillaOrg.getColumna() - 1 == casillaDest.getColumna() || casillaOrg.getColumna() + 1 == casillaDest.getColumna());
        boolean salto2 = casillaOrg.getFila() + 1 == casillaDest.getFila() && (casillaOrg.getColumna() - 2 == casillaDest.getColumna() || casillaOrg.getColumna() + 2 == casillaDest.getColumna());
        boolean salto3 = casillaOrg.getFila() - 1 == casillaDest.getFila() && (casillaOrg.getColumna() - 2 == casillaDest.getColumna() || casillaOrg.getColumna() + 2 == casillaDest.getColumna());
        boolean salto4 = casillaOrg.getFila() - 2 == casillaDest.getFila() && (casillaOrg.getColumna() - 1 == casillaDest.getColumna() || casillaOrg.getColumna() + 1 == casillaDest.getColumna());

        if (salto1 || salto2 || salto3 || salto4){
            move = true;
        }

        return move;
    }
}
