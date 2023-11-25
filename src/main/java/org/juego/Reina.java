package org.juego;

public class Reina extends Pieza{

    public Reina(int color) {
        super(color);
        this.setCodId(Pieza.Reina);
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest) {
        boolean moved = false;
        boolean mismaFila = casillaOrg.getFila() == casillaDest.getFila();
        boolean mismaColumna = casillaOrg.getColumna() == casillaDest.getColumna();
        boolean mismaDiagonal1 = casillaOrg.getColumna() - casillaOrg.getFila() == casillaDest.getColumna() - casillaDest.getFila();
        boolean mismaDiagonal2 = casillaOrg.getColumna() + casillaOrg.getFila() == casillaDest.getColumna() + casillaDest.getFila();

        if(mismaFila || mismaColumna || mismaDiagonal1 || mismaDiagonal2){
            moved = true;
        }

        return moved;
    }
}
