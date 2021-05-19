/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author timou
 */
public class Categorierecette {
    private int id;
    private String nom;

    public Categorierecette() {
    }
    public Categorierecette(int id) {
        this.id = id;
    }
    public Categorierecette(String nom) {
        this.nom = nom;
    }
    public Categorierecette(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
   
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return ""+nom;
    }
    
}
