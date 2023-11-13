package org.example;

import java.util.List;

public class Partida {

    private Tablero tablero;

    public Partida(){
        this.tablero = new Tablero();

        for (int i = 0; i < 8; i++){
            this.tablero.setPieza(new Peon(Pieza.Blanco),2,i + 1);
        }

        this.tablero.setPieza(new Torre(Pieza.Blanco),1,1);
        this.tablero.setPieza(new Torre(Pieza.Blanco),1,8);

        for (int i = 0; i < 8; i++){
            this.tablero.setPieza(new Peon(Pieza.Negro),7,i + 1);
        }
    }

    public boolean movePieza(Casilla casillaOrg, Casilla casillaDest){
        boolean moved = false;
        Pieza pieza = null;

        if (casillaOrg.estaOcupada()){
            pieza = casillaOrg.getPieza();

            if (pieza.canMoveTo(casillaOrg,casillaDest) && (!casillaDest.estaOcupada() || casillaDest.getColorPieza() != pieza.getColor())){
                if (pieza.getCodId() == Pieza.Caballo){
                    this.tablero.getCasillas()[casillaOrg.getFila()][casillaOrg.getColumna()].desocuparCasilla();
                    this.tablero.getCasillas()[casillaDest.getFila()][casillaDest.getColumna()].ocuparCasilla(pieza);
                    moved = true;
                }else {
                    List<Casilla> casillaList = this.tablero.getCasillasMedio(casillaOrg,casillaDest);
                    boolean ocupado = false;
                    int i = 0;

                    while (i < casillaList.size() && !ocupado){
                        ocupado = casillaList.get(i).estaOcupada();
                        i++;
                    }

                    if (!ocupado){
                        this.tablero.getCasillas()[casillaOrg.getFila()][casillaOrg.getColumna()].desocuparCasilla();
                        this.tablero.getCasillas()[casillaDest.getFila()][casillaDest.getColumna()].ocuparCasilla(pieza);
                        moved = true;
                    }
                }
            }
        }

        if (moved && pieza.getCodId() == Pieza.Peon){
            Peon aux = (Peon) pieza;
            aux.moved();
        } else if (moved && pieza.getCodId() == Pieza.Rey) {
            Rey aux = (Rey) pieza;
            aux.moved();
        } else if (moved && pieza.getCodId() == Pieza.Torre) {
            Torre aux = (Torre) pieza;
            aux.moved();
        }

        return moved;
    }

    public boolean enrocarCorto(int color){
        return false;
    }
}
