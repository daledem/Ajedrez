package org.example;

public class Rey extends Pieza{
    private boolean capturado;

    public Rey(int color) {
        super(color);
        this.setCodId(Pieza.Rey);
        this.capturado = false;
    }

    @Override
    public boolean canMoveTo(Casilla casillaOrg, Casilla casillaDest) {
        boolean move = false;
        boolean filaAlante = casillaOrg.getFila() + 1 == casillaDest.getFila() && (casillaOrg.getColumna() - 1 == casillaDest.getColumna()  || casillaOrg.getColumna() == casillaDest.getColumna() ||  casillaOrg.getColumna() + 1 == casillaDest.getColumna());
        boolean mismaFila = casillaOrg.getFila() == casillaDest.getFila() && (casillaOrg.getColumna() - 1 == casillaDest.getColumna() ||  casillaOrg.getColumna() + 1 == casillaDest.getColumna());
        boolean filaAtras = casillaOrg.getFila() - 1 == casillaDest.getFila() && (casillaOrg.getColumna() - 1 == casillaDest.getColumna()  || casillaOrg.getColumna() == casillaDest.getColumna() ||  casillaOrg.getColumna() + 1 == casillaDest.getColumna());

        if (filaAlante || mismaFila || filaAtras){
            move = true;
        }

        return move;
    }

    public boolean isCapturado(){
        return this.capturado;
    }

    public void capturado(){
        this.capturado = true;
    }
}
