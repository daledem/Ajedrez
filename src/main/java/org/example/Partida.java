package org.example;

import java.util.List;

public class Partida {

    private Tablero tablero;
    private boolean terminado;

    public Partida(){
        this.tablero = new Tablero();

        this.terminado = false;

        for (int i = 0; i < 8; i++){
            this.tablero.setPieza(new Peon(Pieza.Blanco),2,i + 1);
        }

        this.tablero.setPieza(new Torre(Pieza.Blanco),1,1);
        this.tablero.setPieza(new Caballo(Pieza.Blanco),1,2);
        this.tablero.setPieza(new Alfil(Pieza.Blanco),1,3);
        this.tablero.setPieza(new Reina(Pieza.Blanco),1,4);
        this.tablero.setPieza(new Rey(Pieza.Blanco), 1, 5);
        this.tablero.setPieza(new Alfil(Pieza.Blanco),1,6);
        this.tablero.setPieza(new Caballo(Pieza.Blanco),1,7);
        this.tablero.setPieza(new Torre(Pieza.Blanco),1,8);

        for (int i = 0; i < 8; i++){
            this.tablero.setPieza(new Peon(Pieza.Negro),7,i + 1);
        }

        this.tablero.setPieza(new Torre(Pieza.Negro),1,1);
        this.tablero.setPieza(new Caballo(Pieza.Negro),1,2);
        this.tablero.setPieza(new Alfil(Pieza.Negro),1,3);
        this.tablero.setPieza(new Reina(Pieza.Negro),1,4);
        this.tablero.setPieza(new Rey(Pieza.Negro), 1, 5);
        this.tablero.setPieza(new Alfil(Pieza.Negro),1,6);
        this.tablero.setPieza(new Caballo(Pieza.Negro),1,7);
        this.tablero.setPieza(new Torre(Pieza.Negro),1,8);
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
                        this.terminado = this.tablero.getCasillas()[casillaDest.getFila()][casillaDest.getColumna()].getCodPieza() == Pieza.Rey;
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

    //Pre: color debe ser un color de la clase estatica Pieza, largo indica si el enroque se va realizar con la
    // torre de la columna 1 si es true o con la de la columna 8 si es false
    //Post: Realiza un enroque con las casillas en las que se encontraban el rey y la torre de la columna indicada
    // sin importar si se han movido o no
    public boolean enrocar(int color, boolean largo){
        int fila;
        int columnaTorre;
        int variacion;
        boolean enroque = false;
        boolean lineaVacia = true;

        if(color == Pieza.Blanco){
            fila = 0;

        }else {
            fila = 7;
        }

        if (largo){
            columnaTorre = 0;
            variacion = -2;
        }else {
            columnaTorre = 7;
            variacion = 2;
        }

        List<Casilla> casillaList = this.tablero.getCasillasMedio(this.tablero.getCasillas()[fila][4],this.tablero.getCasillas()[fila][columnaTorre]);
        int numCasillas = casillaList.size();
        int aux = 0;

        while (aux < numCasillas && lineaVacia){
            lineaVacia = !casillaList.get(aux).estaOcupada();
            aux++;
        }

        if (lineaVacia){
            Pieza rey = this.tablero.getCasillas()[fila][4].getPieza();
            Pieza torre = this.tablero.getCasillas()[fila][columnaTorre].getPieza();

            this.tablero.getCasillas()[fila][4].desocuparCasilla();
            this.tablero.getCasillas()[fila][4 + variacion].ocuparCasilla(rey);

            this.tablero.getCasillas()[fila][columnaTorre].desocuparCasilla();
            this.tablero.getCasillas()[fila][columnaTorre - variacion].ocuparCasilla(torre);

            enroque = true;
        }

        return enroque;
    }

    public boolean isTerminado(){
        return this.terminado;
    }
}
