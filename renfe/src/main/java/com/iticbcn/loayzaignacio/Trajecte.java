package com.iticbcn.loayzaignacio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;

@Entity
public class Trajecte implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Un trajecte té una estació d'origen i una de destí

    @ManyToOne(cascade=CascadeType.ALL)   
    @JoinColumn(name="idEstacioOrigen",foreignKey =
    @ForeignKey(name="FK_TRA_EST_ORI"))
    private Estacio estOrigen;

    @ManyToOne(cascade=CascadeType.ALL)   
    @JoinColumn(name="idEstacioDesti",foreignKey =
    @ForeignKey(name="FK_TRA_EST_DEST"))
    private Estacio estDesti;

    //Un trajecte es de moltes companyias
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER,mappedBy = "trajectescom")
    private Set<Companyia> companyies = new HashSet<>();
    
    @OneToMany(mappedBy="trajecte",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},fetch=FetchType.EAGER)
    private Set<Horari> horaris;

    //Constructores, setters, getters, toString
    public Trajecte(){}

    public Trajecte(Estacio estOrigen, Estacio estDesti, Set<Companyia> companyies){
        this.estOrigen = estOrigen;
        this.estDesti = estDesti;
        this.companyies = companyies;
    }
    public Trajecte(Estacio estOrigen, Estacio estDesti, Set<Companyia> companyies, Set<Horari> horaris){
        this.estOrigen = estOrigen;
        this.estDesti = estDesti;
        this.companyies = companyies;
        this.horaris = horaris;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estacio getEstOrigen() {
        return estOrigen;
    }

    public void setEstOrigen(Estacio estOrigen) {
        this.estOrigen = estOrigen;
    }

    public Estacio getEstDesti() {
        return estDesti;
    }

    public void setEstDesti(Estacio estDesti) {
        this.estDesti = estDesti;
    }

    
    public Set<Companyia> getCompanyies() {
        return companyies;
    }
    
    public void setCompanyies(Set<Companyia> companyies) {
        this.companyies = companyies;
    }
    
    
    public Set<Horari> getHoraris() {
        return horaris;
    }
    
    public void setHoraris(Set<Horari> horaris) {
        this.horaris = horaris;
    }

    @Override
    public String toString() {
        return String.format("%d Estació d'origen: %s Estació de destí: %s",id, estOrigen, estDesti);
    }

    @PreRemove
    private void preRemove() {
        if (estOrigen != null) {
            estOrigen.getTrajectesOrigen().remove(this);
            this.estOrigen = null;
        }
        if (estDesti != null) {
            estDesti.getTrajectesDesti().remove(this);
            this.estDesti = null;
        }

        // Desvincular compañías
        if (companyies != null) {
            for (Companyia companyia : companyies) {
                companyia.getTrajectescom().remove(this);
            }
            companyies.clear();
        }

        // Desvincular horarios si es necesario
        if (horaris != null) {
            for (Horari horari : horaris) {
                horari.setTrajecte(null);
            }
            horaris.clear();
        }
    }
}
