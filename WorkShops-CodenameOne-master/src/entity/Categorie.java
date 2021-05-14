/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;







/**
 *
 * @author Mega Pc
 */
public class Categorie {
    private int id;
    private String nom;
//    private Date date_de_creation,Date_de_modification;

    public Categorie() {
    }

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    /*public Date getDate_de_creation() {
        return date_de_creation;
    }

    public Date getDate_de_modification() {
        return Date_de_modification;
    }*/

    @Override
    public String toString() {
        return nom;
    }

    
    
}
