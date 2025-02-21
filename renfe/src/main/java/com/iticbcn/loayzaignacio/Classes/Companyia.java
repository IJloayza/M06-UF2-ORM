package com.iticbcn.loayzaignacio.Classes;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Companyia implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    //Companyia posseix molts trajectes
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name="CompanyiaTrajecte",
    joinColumns = @JoinColumn(name="Company",foreignKey = @ForeignKey(name="FK_COM_TRA")),
    inverseJoinColumns = { @JoinColumn(name="Trajecte",foreignKey = @ForeignKey(name="FK_TRA_COM")) })
    private Set<Trajecte> trajectescom = new HashSet<>();


    //Constructores, setters, getters, toString
    public Companyia(){}
    public Companyia(String compName){
        this.name = compName; 
    }
    public Companyia(String compName, Set<Trajecte> trajectescom){
        this.name = compName; 
        this.trajectescom = trajectescom;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String compName) {
        this.name = compName;
    }

    public Set<Trajecte> getTrajectescom() {
        return trajectescom;
    }

    public void setTrajectescom(Set<Trajecte> trajectescom) {
        this.trajectescom = trajectescom;
    }

    @Override
    public String toString() {
        return String.format("%d %s", id, name);
    }
}
