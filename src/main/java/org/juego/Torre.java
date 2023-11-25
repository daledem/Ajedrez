package org.juego;

public class Torre extends Pieza{

    public Torre(int color) {
        super(color);
        this.setCodId(Pieza.Torre);
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest) {
        boolean moved = false;
        boolean mismaFila = casillaOrg.getFila() == casillaDest.getFila();
        boolean mismaColumna = casillaOrg.getColumna() == casillaDest.getColumna();

        if(mismaFila || mismaColumna){
            moved = true;
        }

        return moved;
    }
}
