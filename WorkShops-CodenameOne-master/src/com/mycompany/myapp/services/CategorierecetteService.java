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
import entity.Categorierecette;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 
/**
 *
 * @author timou
 */
public class CategorierecetteService {
    public ArrayList<Categorierecette> tasks;
    
    public static CategorierecetteService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CategorierecetteService() {
         req = new ConnectionRequest();
    }

    public static CategorierecetteService getInstance() {
        if (instance == null) {
            instance = new CategorierecetteService();
        }
        return instance;
    }

  
    public ArrayList<Categorierecette> afficher_categorie(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser(); 

           
          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
              System.out.println(tasksListJson);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
             for(Map<String,Object> obj : list){
                 Categorierecette t = new Categorierecette();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                 t.setNom(obj.get("nom").toString());
                 tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
                }
    
    public ArrayList<Categorierecette> getAllTasks(){
        String url = Statics.BASE_URL+"/categorieR/afficherjson";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String reponsejson=new String(req.getResponseData());
                
                tasks = parseTasks(reponsejson);
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
        public ArrayList<Categorierecette> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser(); 

          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
      
              System.out.println(tasksListJson);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tÃ¢ches Json
            for(Map<String,Object> obj : list){
                //CrÃ©ation des tÃ¢ches et rÃ©cupÃ©ration de leurs donnÃ©es
                Categorierecette t = new Categorierecette();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                 t.setNom(obj.get("nom").toString());
                 tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        } 
        return tasks;
    }
        
           public boolean addCat(Categorierecette t) {
        String url = Statics.BASE_URL + "/categorieR/creerjson?nom=" + t.getNom(); //création de l'URL
      
        req.setUrl(url); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;  
                req.removeResponseListener(this); 
                
                
            }
        });
       
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
           
      public boolean deleteCat(Categorierecette t) {
        String url = Statics.BASE_URL + "/deletejsonc/" + t.getId(); //création de l'URL
      
        req.setUrl(url); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;  
                req.removeResponseListener(this); 
                
                
            }
        });
       
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
  public boolean modifierCat(Categorierecette t) {
        String url = Statics.BASE_URL + "/categorieR/modifierjson/" + t.getId()+"?nom="+ t.getNom();  
      
        req.setUrl(url); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;  
                req.removeResponseListener(this); 
                
                
            }
        });
       
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
