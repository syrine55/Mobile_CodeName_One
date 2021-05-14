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
import entity.CommentaireMeditation;
import entity.Meditation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mega Pc
 */
public class ServiceMeditation {
    public ArrayList<Long> mednbr;
    public ArrayList<String> medcat;
    public ArrayList<Meditation> meditations;
    public ArrayList<CommentaireMeditation> commentaires;
    public static ServiceMeditation instance;
    public boolean resultOk;
    private ConnectionRequest req;
    private int nb;
    private ArrayList<Integer> result_apprecitation;
    private String ch;
    public ServiceMeditation() {
        req =new ConnectionRequest();
    }
    
    public static ServiceMeditation getInstance(){
        if(instance == null)
            instance=new ServiceMeditation();
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
                ch=obj.get("title").toString();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return ch;
    }
    
    public String getDupliquerNomMeditation(String nom){
        String url = Statics.BASE_URL+"/MeditationJson/findTitle/"+nom;
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
    
    public boolean addMeditation(Meditation m,Categorie c){
        String url = Statics.BASE_URL+"/meditationJson/creer?title="+m.getNom()+"&apropos="+m.getaPropos()+"&idCategorie="+c.getId();
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
    
    public boolean EditMeditation(Meditation m,Categorie c){
        String url = Statics.BASE_URL+"/MeditationJson/modification/"+m.getId()+"?title="+m.getNom()+"&aPropos="+m.getaPropos()+"&idCategorie="+c.getId();
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
    
    public boolean deleteMeditation(Meditation m){
        String url = Statics.BASE_URL+"/MeditationJson/supprimer/"+m.getId();
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
    public ArrayList<Meditation> parseMeditations(String jsonText){
        meditations=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String, Object> response;
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)meditationsListJson.get("root");
            for(Map<String,Object>obj:list){
                Meditation m =new Meditation();
                float id=Float.parseFloat(obj.get("id").toString());
                m.setId((int)id);
                m.setNom(obj.get("title").toString());
                m.setImage(obj.get("image").toString());
                response = (Map<String, Object>) obj.get("idCategorie");
                m.setCategorie(response.get("nom").toString());
                m.setaPropos(obj.get("aPropos").toString());
                m.setVideo(obj.get("video").toString());
                meditations.add(m);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return meditations;
    }
    
    public ArrayList<Meditation> getAllMeditations(){
        String url = Statics.BASE_URL+"/meditationJson/afficher";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                meditations = parseMeditations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return meditations;
    }
    //Categorie
    public ArrayList<String> parseParCategorie(String jsonText){
        medcat=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<String> list= (ArrayList<String>)meditationsListJson.get("root");
            System.out.println(list);
            System.out.println("=>"+list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                medcat.add(list.get(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return medcat;
    }
    
    public ArrayList<String> getAllParCategorie(){
        String url = Statics.BASE_URL+"/meditationjsonCat/chart";
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
    //nbEspaseParCategorie chart
    public ArrayList<Long> parsenbEspaseParCategorie(String jsonText){
        mednbr=new ArrayList<>();
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
                mednbr.add(Math.round(list.get(i)));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return mednbr;
    }
    
    public ArrayList<Long> getAllnbEspaseParCategorie(){
        String url = Statics.BASE_URL+"/meditationjsonNBR/chart";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                mednbr = parsenbEspaseParCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return mednbr;
    }
    //nbr like et dislike  par un seul espace ou si user effectue like ou non
    public int parseNbLikeDislikeParUnSeulEspace(String jsonText){        
        JSONParser j=new JSONParser();
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)meditationsListJson.get("root");
            for(Map<String,Object>obj:list){
                float id=Float.parseFloat(obj.get("1").toString());
                nb=(int)id;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
    
    public int getNbLikeParUnSeulEspace(int id){
        String url = Statics.BASE_URL+"/count/like/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                nb = parseNbLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nb;
    }
    public int getNbDisLikeParUnSeulEspace(int id){
        String url = Statics.BASE_URL+"/count/dislike/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                nb = parseNbLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nb;
    }
    public int checkLikeParUnSeulEspace(int idu,int idm){
        String url = Statics.BASE_URL+"/check/like/user/"+idu+"/"+idm;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                nb = parseNbLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nb;
    }
    public int checkdisLikeParUnSeulEspace(int idu,int idm){
        String url = Statics.BASE_URL+"/check/dislike/user/"+idu+"/"+idm;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                nb = parseNbLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nb;
    }
    //effectuer like dislike sur un espace
    /*
    "checklikeUser": 1,
    "checkunlikeUser": 0,
    "countlike": 1,
    "countunlike": 0
    */
    public ArrayList<Integer> parseEffectuerLikeDislikeParUnSeulEspace(String jsonText){    
        result_apprecitation=new ArrayList<>();
         JSONParser j=new JSONParser();
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)meditationsListJson.get("root");
            for(Map<String,Object>obj:list){
                float checklikeUser=Float.parseFloat(obj.get("checklikeUser").toString());
                result_apprecitation.add((int)checklikeUser);
                float checkunlikeUser=Float.parseFloat(obj.get("checkunlikeUser").toString());
                result_apprecitation.add((int)checkunlikeUser);
                float countlike=Float.parseFloat(obj.get("countlike").toString());
                result_apprecitation.add((int)countlike);
                float countunlike=Float.parseFloat(obj.get("countunlike").toString());
                result_apprecitation.add((int)countunlike);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result_apprecitation;
    }
    
    public ArrayList<Integer>  getEffectuerLikeParUnSeulEspace(int idu,int idm){
        String url = Statics.BASE_URL+"/meditationClientJson/effectuer/like/"+idu+"/"+idm;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result_apprecitation = parseEffectuerLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result_apprecitation;
    }
    public ArrayList<Integer>  getEffectuerUnLikeParUnSeulEspace(int idu,int idm){
        String url = Statics.BASE_URL+"/meditationClientJson/effectuer/unlike/"+idu+"/"+idm;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result_apprecitation = parseEffectuerLikeDislikeParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result_apprecitation;
    }
    //add commentaire
    public boolean addcommentaire(int idu,int idm,String ch){
        String url = Statics.BASE_URL+"/effectuer/comment/"+idu+"/"+idm+"?commentaire="+ch;
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
    //affichage com client
    public ArrayList<CommentaireMeditation> parseCommentaires(String jsonText){
        commentaires=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String, Object> response;
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)meditationsListJson.get("root");
            for(Map<String,Object>obj:list){
                CommentaireMeditation cm =new CommentaireMeditation();
                float id=Float.parseFloat(obj.get("id").toString());
                cm.setId((int)id);
                String ch=obj.get("commentaire").toString();
                cm.setCommentaire(ch);
                
                response = (Map<String, Object>) obj.get("idUser");
                cm.setNom(response.get("nom").toString());
                cm.setPrenom(response.get("prenom").toString());
                commentaires.add(cm);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return commentaires;
    }
    
    public ArrayList<CommentaireMeditation> getAllcommentaires(int idm){
        String url = Statics.BASE_URL+"/clientJson/commentaire/meditation/"+idm;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentaires = parseCommentaires(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }
   //nbr commentaire par un seul espace
    public int parseNbCommentParUnSeulEspace(String jsonText){        
        JSONParser j=new JSONParser();
        Map<String,Object> meditationsListJson;
        try {
            meditationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)meditationsListJson.get("root");
            for(Map<String,Object>obj:list){
                float id=Float.parseFloat(obj.get("1").toString());
                nb=(int)id;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
    
    public int getNbCommentParUnSeulEspace(int id){
        String url = Statics.BASE_URL+"/count/comment/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                nb = parseNbCommentParUnSeulEspace(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nb;
    }
}
