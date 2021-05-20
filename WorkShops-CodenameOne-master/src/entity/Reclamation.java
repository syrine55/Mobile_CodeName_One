/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author 21692
 */
public class Reclamation {
      private int id ;
    private String adresse , service , type_message , contenu_message ;


    public Reclamation() {
    }

    public Reclamation(int id, String adresse, String service, String type_message, String contenu_message) {
        this.id = id;
        this.adresse = adresse;
        this.service = service;
        this.type_message = type_message;
        this.contenu_message = contenu_message;
    }

    public Reclamation(String adresse, String service, String type_message, String contenu_message) {
        this.adresse = adresse;
        this.service = service;
        this.type_message = type_message;
        this.contenu_message = contenu_message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType_message() {
        return type_message;
    }

    public void setType_message(String type_message) {
        this.type_message = type_message;
    }

    public String getContenu_message() {
        return contenu_message;
    }

    public void setContenu_message(String contenu_message) {
        this.contenu_message = contenu_message;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", service='" + service + '\'' +
                ", type_message='" + type_message + '\'' +
                ", contenu_message='" + contenu_message + '\'' +
                '}';
    }
     
     
     
}
