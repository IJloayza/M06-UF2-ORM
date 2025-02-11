package com.iticbcn.loayzaignacio;

import java.io.Serializable;

public class Horari implements Serializable{
    private int idHorari;
    private String hourDepart;
    private String hourArribe;
    private Trajecte trajecte;

    //Constructores, setters, getters, toString
    public Horari(){}
    public Horari(String horaDep, String horaArri, Trajecte trajecte){
        this.hourDepart = horaDep;
        this.hourArribe = horaArri;
        this.trajecte = trajecte;
    }
    public int getIdHorari() {
        return idHorari;
    }

    public void setIdHorari(int idHorari) {
        this.idHorari = idHorari;
    }

    public String getHourDepart() {
        return hourDepart;
    }

    public void setHourDepart(String hourDepart) {
        this.hourDepart = hourDepart;
    }

    public String getHourArribe() {
        return hourArribe;
    }

    public void setHourArribe(String hourArribe) {
        this.hourArribe = hourArribe;
    }
    public Trajecte getTrajecte() {
        return trajecte;
    }
    public void setTrajecte(Trajecte trajecte) {
        this.trajecte = trajecte;
    }

    @Override
    public String toString() {
        return String.format("%d Hora de partida: %s  Hora d'arrivada: %s Trajecte: %s", idHorari, hourDepart, hourArribe, trajecte);
    }
}
