package com.iticbcn.loayzaignacio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;

@Entity
public class Estacio implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int idEstacio;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "estOrigen", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Trajecte> trajectesOrigen = new HashSet<>();

    @OneToMany(mappedBy = "estDesti", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Trajecte> trajectesDesti = new HashSet<>();

    //Constructores, setters, getters, toString
    public Estacio(){}
    public Estacio(String name){
        this.name = name;
    }
    public Estacio(String name, Set<Trajecte> trajectesOrigen, Set<Trajecte> trajectesDesti){
        this.name = name;
        this.trajectesDesti = trajectesDesti;
        this.trajectesOrigen = trajectesOrigen;
    }
    public int getIdEstacio() {
        return idEstacio;
    }

    public void setIdEstacio(int idEstacio) {
        this.idEstacio = idEstacio;
    }

    public String getName() {
        return name;
    }

    public void setName(String nomEstacio) {
        this.name = nomEstacio;
    }

    public Set<Trajecte> getTrajectesOrigen() {
        return trajectesOrigen;
    }
    public void setTrajectesOrigen(Set<Trajecte> trajectesOrigen) {
        this.trajectesOrigen = trajectesOrigen;
    }
    public Set<Trajecte> getTrajectesDesti() {
        return trajectesDesti;
    }
    public void setTrajectesDesti(Set<Trajecte> trajectesDesti) {
        this.trajectesDesti = trajectesDesti;
    }
    @Override
    public String toString() {
        return String.format("%d %s", idEstacio, name);
    }
    @PreRemove
    private void preRemove() {
        // Evitar eliminación de Trajecte y setear a null las referencias
        for (Trajecte trajecte : trajectesOrigen) {
            trajecte.setEstOrigen(null);
        }

        for (Trajecte trajecte : trajectesDesti) {
            trajecte.setEstDesti(null);
        }
    }
}
