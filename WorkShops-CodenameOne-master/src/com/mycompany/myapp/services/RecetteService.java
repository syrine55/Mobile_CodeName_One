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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import entity.Categorierecette;
import entity.Recettes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author timou
 */
public class RecetteService {
     public ArrayList<Recettes> tasks;
    public ArrayList<Long> r;
    public ArrayList<String> medcat;
    public static RecetteService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private RecetteService() {
         req = new ConnectionRequest();
    }

    public static RecetteService getInstance() {
        if (instance == null) {
            instance = new RecetteService();
        }
        return instance;
    }

  
    public ArrayList<Recettes> afficher (String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser(); 

           
          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
              System.out.println(tasksListJson);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
             for(Map<String,Object> obj : list){
                 Recettes t = new Recettes();
                float id = Float.parseFloat(obj.get("id").toString());
                 t.setId((int)id);
                 t.setNom(obj.get("nom").toString());
                 t.setAbout(obj.get("about").toString());
                 t.setImg(obj.get("img").toString());
                 t.setVideo(obj.get("vid").toString());
                   t.setDate_creation((Date)obj.get("date_creation") );
                 tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
                }
    
    public ArrayList<Recettes> getAllTasks(){
        String url = Statics.BASE_URL+"/afficherjson";
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
 
        public ArrayList<Recettes> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser(); 

          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
      
              System.out.println(tasksListJson);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tÃ¢ches Json
            for(Map<String,Object> obj : list){
                //CrÃ©ation des tÃ¢ches et rÃ©cupÃ©ration de leurs donnÃ©es
                Recettes t = new Recettes();
                float id = Float.parseFloat(obj.get("id").toString());
               // DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
               //  String strDate = dateFormat.format(obj.get("dateCreation"));  
                 t.setId((int)id);
                 t.setNom(obj.get("nom").toString());
                 t.setAbout(obj.get("about").toString());
                 t.setImg(obj.get("img").toString());
                 t.setVideo(obj.get("vid").toString());
              //   try{
              //   t.setDate_creation(dateFormat.parse(strDate) ); } catch(Exception ex){
          //  ex.printStackTrace();
     //   }
                 tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        } 
        return tasks;
    }
        
           public boolean addCat(Recettes t,Categorierecette x) {
        String url = Statics.BASE_URL + "/addjson?nom=" + t.getNom()+"&about="+t.getAbout()+"&idcat="+x.getId(); //création de l'URL
      
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
           
      public boolean deleteCat(Recettes t) {
        String url = Statics.BASE_URL + "/deletejson/" + t.getId(); //création de l'URL
      
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
  public boolean modifierCat(Recettes t,Categorierecette x) {
        String url = Statics.BASE_URL + "/modifierjson/" + t.getId()+"?nom="+ t.getNom()+"&about="+t.getAbout() + "&idcat="+x.getId();  
      
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
  
 public ArrayList<Long> chart(){
    
        String url = Statics.BASE_URL+"/RjsonNBR/chart";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 String reponsejson=new String(req.getResponseData());
                 r = parsenbRParCategorie(new String(req.getResponseData()));
                 req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req); 
        return r;
    }
 
   public ArrayList<String> parseParCategorie(String jsonText){
      ArrayList  r=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<String> list= (ArrayList<String>)meditationsListJson.get("root");
            System.out.println(list);
            System.out.println("=>"+list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                r.add(list.get(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }
  public ArrayList<Long> parsenbRParCategorie(String jsonText){
     ArrayList   medcat=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String, Object> response;
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Double> list= (ArrayList<Double>)meditationsListJson.get("root");
            System.out.println(list);
            System.out.println("=>"+list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(Math.round(list.get(i)));
                medcat.add(Math.round(list.get(i)));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return medcat;
    } 
   public ArrayList<Long> getAllnbERParCategorie(){
        String url = Statics.BASE_URL+"/RjsonNBR/chart";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 r= parsenbRParCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return r;
    }
  public ArrayList<String> getAllParCategorie(){
        String url = Statics.BASE_URL+"/RjsonCat/chart";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                medcat = parseParCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return medcat;
    }  
}
