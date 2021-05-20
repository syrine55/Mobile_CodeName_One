/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Consommation {

    int id,user_id ;
    String heure_reveil , heure_dormir , ctrl_consomation , consomation ;
public Consommation() {
    }

    public Consommation(int id, String heure_reveil, String heure_dormir, String ctrl_consomation, String consomation) {
        this.id = id;
        this.heure_reveil = heure_reveil;
        this.heure_dormir = heure_dormir;
        this.ctrl_consomation = ctrl_consomation;
        this.consomation = consomation;
    }

    public Consommation(String heure_reveil, String heure_dormir, String ctrl_consomation, String consomation) {
        this.heure_reveil = heure_reveil;
        this.heure_dormir = heure_dormir;
        this.ctrl_consomation = ctrl_consomation;
        this.consomation = consomation;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeure_reveil() {
        return heure_reveil;
    }

    public void setHeure_reveil(String heure_reveil) {
        this.heure_reveil = heure_reveil;
    }

    public String getHeure_dormir() {
        return heure_dormir;
    }

    public void setHeure_dormir(String heure_dormir) {
        this.heure_dormir = heure_dormir;
    }

    public String getCtrl_consomation() {
        return ctrl_consomation;
    }

    public void setCtrl_consomation(String ctrl_consomation) {
        this.ctrl_consomation = ctrl_consomation;
    }

    public String getConsomation() {
        return consomation;
    }

    public void setConsomation(String consomation) {
        this.consomation = consomation;
    }

    @Override
    public String toString() {
        return "Consommation{" + "id=" + id + ", heure_reveil=" + heure_reveil + ", heure_dormir=" + heure_dormir + ", ctrl_consomation=" + ctrl_consomation + ", consomation=" + consomation + '}';
    }
    
    
    
    
}









