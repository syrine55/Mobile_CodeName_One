/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import entity.Categorie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Mega Pc
 */
public class ServiceCategorie {
    public ArrayList<Categorie> categories;
    public static ServiceCategorie instance;
    public boolean resultOk;
    private ConnectionRequest req;
    private String ch;

    public ServiceCategorie() {
        req =new ConnectionRequest();
    }
    
    public static ServiceCategorie getInstance(){
        if(instance == null)
            instance=new ServiceCategorie();
        return instance;
    }
    
    //control saisir pour la duplication
    public String parseDupliquerNom(String jsonText){
        String ch="";
        JSONParser j=new JSONParser();
        Map<String,Object> categoriesListJson;
        try {
            categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)categoriesListJson.get("root");
            for(Map<String,Object>obj:list){
                ch=obj.get("nom").toString();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return ch;
    }
    
    public String getDupliquerNomCAt(String nom){
        String url = Statics.BASE_URL+"/categorieJson/findTitle/"+nom;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ch = parseDupliquerNom(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ch;
    }
    
    public boolean addCategorie(Categorie c){
        String url = Statics.BASE_URL+"/categorieJson/creer?nom="+c.getNom();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt){
                resultOk=req.getResponseCode()==200;
                req.removeExceptionListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    public boolean EditCategorie(Categorie c){
        String url = Statics.BASE_URL+"/categorieJson/modification/"+c.getId()+"?nom="+c.getNom();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt){
                resultOk=req.getResponseCode()==200;
                req.removeExceptionListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    public boolean daleteCategorie(Categorie c){
        String url = Statics.BASE_URL+"/categorieJson/supprimer/"+c.getId()+"/"+ServiceLogin.id_user;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt){
                resultOk=req.getResponseCode()==200;
                req.removeExceptionListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    //affichage
    public ArrayList<Categorie> parseCategories(String jsonText){
        categories=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String,Object> categoriesListJson;
        try {
            categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)categoriesListJson.get("root");
            for(Map<String,Object>obj:list){
                Categorie c =new Categorie();
                float id=Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);
                c.setNom(obj.get("nom").toString());
                categories.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
    
    public ArrayList<Categorie> getAllCategories(){
        String url = Statics.BASE_URL+"/categorieJson/afficher";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
}
