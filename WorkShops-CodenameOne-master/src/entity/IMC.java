/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;







/**
 *
 * @author Mohamed
 */
public class IMC {
    
    private int id,id_utilisateur;
    private double poids,taille,score;
    private String statut;
    

    public IMC() {
    }

    public IMC(int id, int id_utilisateur, double poids, double taille,double score,String statut) {
        this.id = id;
        this.id_utilisateur = id_utilisateur;
        this.poids = poids;
        this.taille = taille;
        this.score = score;
        this.statut=statut;
        
    }

    public IMC(int id_utilisateur, double poids, double taille) {
        this.id_utilisateur = id_utilisateur;
        this.poids = poids;
        this.taille = taille;
        double s= poids / (taille*taille);
        
        this.score =s;
        if(s >= 25) { 
          this.statut = "Vous avez un poids corporel supérieur à la normale.";
        } else if (s >= 18.5) {
            this.statut = "Vous avez un poids corporel normal. Bon travail!";
        }else {
            this.statut = "Vous avez un poids corporel inférieur à la normale.";
        }
        
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    
    
    
}
