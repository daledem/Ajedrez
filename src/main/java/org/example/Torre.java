package org.example;

public class Torre extends Pieza{

    private boolean moved;

    public Torre(int color) {
        super(color);
        this.setCodId(Pieza.Torre);
        this.moved = false;
    }

    public boolean hasMoved(){
        return this.moved;
    }

    public void moved(){
        this.moved = true;
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
