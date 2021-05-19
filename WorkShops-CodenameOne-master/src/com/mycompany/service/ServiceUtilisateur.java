/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;

import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entite.Utilisateur;
import com.mycompany.gui.ProfileForm;
import com.mycompany.gui.WalkthruForm;
import com.mycompany.utils.Statics;
import static com.mycompany.utils.Statics.BASE_URL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;



/**
 *
 * @author Ajengui
 */
public class ServiceUtilisateur {
      public ArrayList<Utilisateur> util;
       public Utilisateur utilisateur = new Utilisateur();
    public static ServiceUtilisateur instance=null;
            public boolean resultOK;
    public static boolean result=true;
    String json;
    private ConnectionRequest req;
    
    public static ServiceUtilisateur getInstance(){
        if(instance==null){
            instance= new ServiceUtilisateur();
        }
        return instance;
    }
    
     public ServiceUtilisateur(){
         req=new ConnectionRequest();
     }
     
     
     
     
     public Utilisateur  parseUser(String jsonText) {
         Utilisateur u = new Utilisateur();
        //Utilisateur UserL = new Utilisateur();
        try {
            util=new ArrayList<>();
            
            JSONParser j = new JSONParser();

            //Map<String, Object> UserListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println(tasksListJson);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
             
                for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
               
                float id = Float.parseFloat(obj.get("id").toString());
                String email=obj.get("email").toString();
                String mdp=obj.get("mdp").toString();
               
                u.setId((int) id);
                u.setEmail(email);
                u.setMdp(mdp);
               
                util.add(u);
                }
            /*if (UserListJson.get("type").equals("Login succeed")) {

                float id = Float.parseFloat(UserListJson.get("id").toString());
                UserL.setId((int) (id));
                UserL.setEmail(UserListJson.get("email").toString());
                UserL.setMdp(UserListJson.get("mdp").toString());
                UserL.setAddrese(UserListJson.get("addrese").toString());
                UserL.setNom(UserListJson.get("nom").toString());
                UserL.setPrenom(UserListJson.get("prenom").toString());
                UserL.setDateN(UserListJson.get("dateN").toString());
                UserL.setTaille(UserListJson.get("taille").toString());
                UserL.setPoids(UserListJson.get("poids").toString());
                UserL.setImage(UserListJson.get("image").toString());
                UserL.setRole(UserListJson.get("role").toString());
                

            } else {
                return null;
            }*/
            

        } catch (IOException ex) {
            
        }

        return u;
    }
     
     
     
     public String getPasswordEmail(TextField email,Resources res){
        String url=BASE_URL+"/getPasswordEmail?email="+email.getText().toString();
        req=new ConnectionRequest(url,false);
        System.out.println(url);
        req.setUrl(url);
        
        req.addResponseListener(e-> {
          JSONParser j = new JSONParser();
                 json = new String(req.getResponseData())+"";
                try {
                    
                        
                        Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        
                        
                    
                } catch (IOException ex) {        
                    ex.printStackTrace();
                }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       return json;
    }
     
    
    public void signup(TextField email, TextField password, TextField addrese, TextField nom, TextField prenom, TextField dateN, TextField taille, TextField poids, TextField image, TextField role,Resources res){
        String url=BASE_URL+"/register?nom="+nom.getText().toString()+"&prenom="+
                prenom.getText().toString()+"&email="+email.getText().toString()+"&adresse="+
                addrese.getText().toString()+"&mdp="+password.getText().toString()+"&date_naissance="+
                dateN.getText().toString()+"&taille="+taille.getText().toString()+"&poids="+
                poids.getText().toString()+"&role="+role.getText().toString()+"&image="+image.getText().toString();
        System.out.println(url);
        req.setUrl(url);
        
        req.addResponseListener(e-> {
         byte[]data=(byte[]) e.getMetaData();
         String rep=new String(data);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       
    }
     
    
    public void Login(TextField email, TextField password,Resources res) {
        String url = BASE_URL + "/connexion?email="+email.getText().toString()+"&mdp="+password.getText().toString();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent e) {
                JSONParser j = new JSONParser();
                String json = new String(req.getResponseData())+"";
                try {
                    if(json.equals("failed")){
                        Dialog.show("echec authentification","email ou password incorrect","OK",null);
                    }
                    else{
                        
                        Map<String,Object> User = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        if(User.size()>0){
                            new ProfileForm(res).show();
                        }
                        
                    }
                } catch (IOException ex) {        
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        }
        
    

    
   
}
