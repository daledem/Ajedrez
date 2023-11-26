package org.servidor;




public class Usuario {

    private String nombre;
    private int elo;
    private String ip;
    private int puerto;
    private boolean esperandoEnMesa;

    public Usuario(String nombre, int elo, String ip){
        this.nombre = nombre;
        this.elo = elo;
        this.ip = ip;
        this.puerto = -1;
        this.esperandoEnMesa = false;
    }

    public Usuario(String nombre, int elo, String ip, int puerto){
        this.nombre = nombre;
        this.elo = elo;
        this.ip = ip;
        this.puerto = puerto;
        this.esperandoEnMesa = true;
    }

    public int getElo() {
        return elo;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public boolean isEsperandoEnMesa() {
        return esperandoEnMesa;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setEsperandoEnMesa(boolean esperandoEnMesa) {
        this.esperandoEnMesa = esperandoEnMesa;
    }

    @Override
    public String toString() {
        String mensaje;

        if (this.esperandoEnMesa){
            mensaje = nombre + " " + elo + " " + ip + " " + puerto;
        }else {
            mensaje = nombre + " " + elo + " " + ip;
        }

        return mensaje;
    }
}
