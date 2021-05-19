/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;


import entity.FosUser;
import Utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.BaseForm;
import com.mycompany.gui.ConsulterEspaceMeditation;
//import com.codename1.uikit.materialscreens.WalkthruForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ISLEM_PC
 */
public class ServiceLogin{
public static int id_user;
public static String role_user,json;
public String ch;

   public ArrayList<FosUser> fosmobi;

    public static ServiceLogin instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceLogin() {
        req = new ConnectionRequest();
    }

    public static ServiceLogin getInstance() {
        if (instance == null) {
            instance = new ServiceLogin();
        }
        return instance;
    }

    public boolean addTask(FosUser A) {
        
        String url = Statics.BASE_URL2 +"api/user/ajouter?nom=" + A.getNom() + "&email=" + A.getEmail() + "&password=" + A.getPassword() ; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean editTask(FosUser A) {
        
       // String url = "http://localhost/projetquiz/web/app_dev.php/mobile/fos/edit?username="+A.getUsername()+"&email="+A.getEmail()+"&password="+A.getPassword(); //création de l'URL
       // req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<FosUser> parsefos(String jsonText) {

        try {
            // FosUser A = new FosUser();
            fosmobi = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
           //  List listMappedUser = (List) tasksListJson.get("root");
          for (Map<String, Object> obj:list) {
                 System.out.println(obj);
               FosUser A = new FosUser();
              
                   // Map get = (Map) listMappedUser.get(i);
                float id = Float.parseFloat(obj.get("id").toString());
                A.setId((int)id);
                
                A.setEmail(obj.get("email").toString());
                A.setPassword(obj.get("password").toString());
               A.setNom(obj.get("nom").toString());
                
               

                fosmobi.add(A);}
           // }
        } catch (IOException ex) {

        }
        return fosmobi;
    }

    public void login(TextField email, TextField password,Resources res) {
        String url = Statics.BASE_URL2 +"api/loginj?email=" +email.getText().toString()+ "&password=" + password.getText().toString();
        req=new ConnectionRequest(url,false);
        req.setUrl(url);
     //   req.setPost(false);
     
        req.addResponseListener((e)-> {
             JSONParser j = new JSONParser();
         String json=new String(req.getResponseData())+"";
               
      
       try{ 
           if(json.equals("failed")){ 
               Dialog.show("echec", "email ou pwd", "ok",null);
               
           }
           else{
               System.out.println("data=="+json);
              
                Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                System.out.println("user = "+        user.size());
                float x=Float.parseFloat(user.get("id").toString());
                id_user=((int)x);
                role_user=user.get("role").toString();
                System.out.println("======>"+id_user);
                  if (user.size() > 0) {
                  
        
                   
                  //  Dialog.show("Hi", "Welcome back", new Command("OK"));
                       Toolbar.setGlobalToolbar(false);
            new ConsulterEspaceMeditation(res).show();
       
            Toolbar.setGlobalToolbar(true);
                    

                } else {
                    Dialog.show("error", "Connection Failed", new Command("OK"));
                }
           
           
           }
       }catch(Exception ex){
           ex.printStackTrace();
       }
         });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }


    
    /*public String getPasswordEmail(TextField email,Resources res){
        String url=Statics.BASE_URL2+"getPasswordEmail?email="+email.getText().toString();
        String ch;
        req=new ConnectionRequest(url,false);
        System.out.println(url);
        req.setUrl(url);
        
        req.addResponseListener(e-> {
          JSONParser j = new JSONParser();
                 json = new String(req.getResponseData())+"";
                try {
                    
                        System.out.println(json);
                        Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        for (Map<String, Object> obj : password) {
                        ch = obj.get("title").toString();
                    }
                        
                    
                } catch (IOException ex) {        
                    ex.printStackTrace();
                }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       return json;
    }*/
    
    
    public String mdpoublier(String jsonText){
        String ch="";
        JSONParser j=new JSONParser();
        Map<String,Object> categoriesListJson;
        try {
            categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)categoriesListJson.get("root");
            for(Map<String,Object>obj:list){
                ch=obj.get("pass").toString();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return ch;
    }
    
    public String getDupliquerNomMeditation(TextField email,Resources res){
        String url = Statics.BASE_URL2+"getPasswordEmail?email="+email.getText().toString();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ch = mdpoublier(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ch;
    }
}
