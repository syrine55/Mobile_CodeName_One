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
public class CommentaireMeditation {
    private int id;
    private String commentaire,nom,prenom;

    public CommentaireMeditation() {
    }

    public CommentaireMeditation(String commentaire, String nom, String prenom) {
        this.commentaire = commentaire;
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " : " + commentaire ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
}
