/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author timou
 */
public class Recettes {
    private int id, idcategorie,createur;
    private String nom;
   
    private String about;
    private String img;
    private String video;
    private Date date_creation;
    public int like;

    public Recettes() {
    }
public Recettes(int idcategorie) {this.idcategorie=idcategorie;
    }

    public int getCreateur() {
        return createur;
    }

    public Recettes(String nom, String about, String img, String video) {
        this.nom = nom;
        this.about = about;
        this.img = img;
        this.video = video;
    }

public Recettes(int idcategorie, String nom,  String about, String img, String video, int createur) {
        this.idcategorie = idcategorie;

        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
         this.createur=createur;
    }
public Recettes(int id,int idcategorie, String nom,  String about, String img, String video,Date date_creation, int createur) {
        
    this.id=id;
    this.idcategorie = idcategorie;

        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
        this.date_creation=date_creation;
         this.createur=createur;
    }


    public Recettes(int id,int idcategorie, String nom,  String about, String img, String video, Date date_creation) {
        this.id = id;
        this.idcategorie = idcategorie;

        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
        this.date_creation = date_creation;
    }
    public Recettes(int idcategorie, String nom,  String about, String img, String video, Date date_creation) {
        this.idcategorie = idcategorie;

        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
        this.date_creation = date_creation;
    }
    
    public Recettes(int recetteId, int idcategorie ,String nom,  String about, String img, String video) {
          this.id = recetteId;
        this.idcategorie=idcategorie;
        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
       
    }

    @Override
    public String toString() {
        return "Recette{" + "id=" + id + ", idcategorie=" + idcategorie + ", nom=" + nom + ", about=" + about + ", img=" + img + ", video=" + video + ", date_creation=" + date_creation + ", like=" + like + '}';
    }
    
public Recettes(  int idcategorie ,String nom,  String about, String img, String video) {
           
        this.idcategorie=idcategorie;
        this.nom = nom;
     
        this.about = about;
        this.img = img;
        this.video = video;
       
    }
    public int getIdcategorie() {
        return idcategorie;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    

    public String getAbout() {
        return about;
    }

    public String getVideo() {
        return video;
    }

    

    public String getNom() {
        return nom;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(String video) {
        this.video = video;
    }

   

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImg(String img) {
        this.img = img;
    }

     
}
