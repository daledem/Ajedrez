package org.example;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private Casilla [][] casillas;

    public Tablero(){
        this.casillas = new Casilla[8][8];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                this.casillas[i][j] = new Casilla(i + 1,j + 1);
            }
        }
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public void setPieza(Pieza pieza, int fila, int columa) {
        this.casillas[fila - 1][columa - 1].ocuparCasilla(pieza);
    }

    public List<Casilla> getCasillasMedio(Casilla cas1, Casilla cas2){
        List<Casilla> casillaList = new ArrayList<>();
        boolean mismaFila = cas1.getFila() == cas2.getFila();
        boolean mismaColumna = cas1.getColumna() == cas2.getColumna();
        boolean mismaDiagonal1 = cas1.getColumna() - cas1.getFila() == cas2.getColumna() - cas2.getFila();
        boolean mismaDiagonal2 = cas1.getColumna() + cas1.getFila() == cas2.getColumna() + cas2.getFila();
        int menorFil;
        int mayorFil;
        int menorCol;
        int mayorCol;

        if(mismaFila){
            int fila = cas1.getFila();

            if (cas1.getColumna() < cas2.getColumna()){
                menorCol = cas1.getColumna();
                mayorCol = cas2.getColumna();
            }else {
                mayorCol = cas1.getColumna();
                menorCol = cas2.getColumna();
            }

            menorCol++;

            while (menorCol < mayorCol){
                casillaList.add(this.casillas[fila][menorCol]);
                menorCol++;
            }
        } else if (mismaColumna) {
            int columna = cas1.getColumna();

            if (cas1.getFila() < cas2.getFila()){
                menorFil = cas1.getFila();
                mayorFil = cas2.getFila();
            }else {
                mayorFil = cas1.getFila();
                menorFil = cas2.getFila();
            }

            menorFil++;

            while (menorFil < mayorFil){
                casillaList.add(this.casillas[menorFil][columna]);
                menorFil++;
            }
        } else if (mismaDiagonal1) {
            if (cas1.getFila() < cas2.getFila()){
                menorFil = cas1.getFila();
                menorCol = cas1.getColumna();
                mayorFil = cas2.getFila();
            }else {
                mayorFil = cas1.getFila();
                menorFil = cas2.getFila();
                menorCol = cas2.getColumna();
            }

            menorFil++;
            menorCol++;

            while (menorFil < mayorFil){
                casillaList.add(this.casillas[menorFil][menorCol]);
                menorFil++;
                menorCol++;
            }
        } else if (mismaDiagonal2) {
            if (cas1.getFila() < cas2.getFila()){
                menorFil = cas1.getFila();
                mayorCol = cas1.getColumna();
                mayorFil = cas2.getFila();
            }else {
                mayorFil = cas1.getFila();
                menorFil = cas2.getFila();
                mayorCol = cas2.getColumna();
            }

            menorFil++;
            mayorCol--;

            while (menorFil < mayorFil){
                casillaList.add(this.casillas[menorFil][mayorCol]);
                menorFil++;
                mayorCol--;
            }
        }else {
            casillaList = null;
        }

        return casillaList;
    }

}
