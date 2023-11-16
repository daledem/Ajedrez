package org.example;

public class Jugador {

    private int color;
    private boolean turno;
    private Partida partida;
    private boolean enroqueCorto;
    private boolean enroqueLargo;

    public Jugador(int color){
        this.color = color;
        this.turno = this.color == Pieza.Blanco;
        this.partida = new Partida();
        this.enroqueCorto = true;
        this.enroqueLargo = true;
    }

    public boolean realizarJugada(Casilla casillaOrigen, Casilla casillaDestino){
        boolean jugada = false;

        if(casillaOrigen.getColorPieza() == this.color && this.turno){
            jugada = this.partida.movePieza(casillaOrigen,casillaDestino);
            this.turno = false;
        }

        if (jugada && (casillaDestino.getColorPieza() == Pieza.Rey )){
            this.enroqueCorto = false;
            this.enroqueLargo = false;
        }

        return jugada;
    }

    public boolean enroqueLargo(){
        boolean enrocar = false;

        if(this.turno && this.enroqueLargo){
            enrocar = this.partida.enrocar(this.color,true);
            this.enroqueLargo = false;
            this.enroqueCorto = false;
            this.turno = false;
        }

        return enrocar;
    }

    public boolean enroqueCorto(){
        boolean enrocar = false;

        if(this.turno && this.enroqueCorto){
            enrocar = this.partida.enrocar(this.color,false);
            this.enroqueLargo = false;
            this.enroqueCorto = false;
            this.turno = false;
        }

        return enrocar;
    }

    public void turnoRival(Casilla casillaOrigen, Casilla casillaDestino){
        this.partida.movePieza(casillaOrigen,casillaDestino);
    }


}
