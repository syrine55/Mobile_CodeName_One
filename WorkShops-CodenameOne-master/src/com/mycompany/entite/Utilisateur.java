/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entite;

/**
 *
 * @author Badr Day
 */
public class Utilisateur {
    
    private int id;

    private String email;

    private String mdp;

    private String addrese;

    private String nom;

    private String prenom;
    
    private String dateN;
    
    private int taille;
    
    private int poids;
    
    private String image;
     
    private String role;

    public Utilisateur() {
    }

    
    public Utilisateur(int id, String email, String mdp, String addrese, String nom, String prenom, String dateN, int taille, int poids, String role,String image) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.addrese = addrese;
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.taille = taille;
        this.poids = poids;
        this.image = image;
        this.role = role;
    }


        
    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public int getPoids ()
    {
        return poids;
    }

    public void setPoids (int poids)
    {
        this.poids = poids;
    }

    public int getTaille ()
    {
        return taille;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setAddrese(String addrese) {
        this.addrese = addrese;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getMdp() {
        return mdp;
    }

    public String getAddrese() {
        return addrese;
    }

    

    public String getPrenom ()
    {
        return prenom;
    }

    public void setPrenom (String prenom)
    {
        this.prenom = prenom;
    }

    public String getNom ()
    {
        return nom;
    }

    public void setDateN(String dateN) {
        this.dateN = dateN;
    }

    public String getDateN() {
        return dateN;
    }

    public void setNom (String nom)
    {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public String getRole() {
        return role;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "email=" + email + ", mdp=" + mdp + ", addrese=" + addrese + ", nom=" + nom + ", prenom=" + prenom + ", dateN=" + dateN + ", taille=" + taille + ", poids=" + poids + ", image=" + image + ", role=" + role + '}';
    }

   
    
}
