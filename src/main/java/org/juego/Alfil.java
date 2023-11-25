package org.juego;

public class Alfil extends Pieza{

    public Alfil(int color) {
        super(color);
        this.setCodId(Pieza.Alfil);
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest) {
        boolean moved = false;
        boolean mismaDiagonal1 = casillaOrg.getColumna() - casillaOrg.getFila() == casillaDest.getColumna() - casillaDest.getFila();
        boolean mismaDiagonal2 = casillaOrg.getColumna() + casillaOrg.getFila() == casillaDest.getColumna() + casillaDest.getFila();

        if(mismaDiagonal1 || mismaDiagonal2){
            moved = true;
        }

        return moved;
    }
}
