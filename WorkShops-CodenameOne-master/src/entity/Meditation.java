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
public class Meditation {
    private int id;
    private String nom,aPropos,video,image,categorie;

    public Meditation(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Meditation(int id, String nom, String aPropos) {
        this.id = id;
        this.nom = nom;
        this.aPropos = aPropos;
    }

    

    public Meditation() {
    }

    public Meditation(String nom, String aPropos) {
        this.nom = nom;
        this.aPropos = aPropos;
    }

    public Meditation(int id, String nom,String categorie, String aPropos, String image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.categorie=categorie;
        this.aPropos = aPropos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setaPropos(String aPropos) {
        this.aPropos = aPropos;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getaPropos() {
        return aPropos;
    }

    public String getVideo() {
        return video;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Meditation{" + "id=" + id + ", nom=" + nom + ", aPropos=" + aPropos + ", video=" + video + ", image=" + image + '}';
    }
    
}
