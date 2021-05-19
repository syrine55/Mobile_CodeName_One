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
import entity.IMC;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohamed
 */

public class ServiceIMC {

    public ArrayList<IMC> imcs;
    public static ServiceIMC instance;
    public boolean resultOk;
    private ConnectionRequest req;
    
     public ServiceIMC() {
        req =new ConnectionRequest();
    }
    
    public static ServiceIMC getInstance(){
        if(instance == null)
            instance=new ServiceIMC();
        return instance;
    }
    
    //affichage
    public ArrayList<IMC> parseIMCs(String jsonText){
        imcs=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String,Object> imcsListJson;
        try {
            imcsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)imcsListJson.get("root");
            for(Map<String,Object>obj:list){
                IMC i =new IMC();
                float id=Float.parseFloat(obj.get("id").toString());
                i.setId((int)id);
                i.setPoids( Double.parseDouble(obj.get("poids").toString()) );
                i.setTaille( Double.parseDouble(obj.get("taille").toString()) );
                i.setScore( Double.parseDouble(obj.get("score").toString()) );
                i.setStatut(obj.get("statut").toString());
                imcs.add(i);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return imcs;
    }
    
    public ArrayList<IMC> getAllIMCs(){
        String url = Statics.BASE_URL+"/api/imcs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                imcs = parseIMCs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return imcs;
    }
    
     public boolean deletedIMC(int id) {

        String url = Statics.BASE_URL + "/api/deleteIMC/" + id + "";
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
     
    public boolean updateIMC(IMC i, int id) {

        String url = Statics.BASE_URL + "/api/updateIMC/" + id + "";
        String json = ("{\"poids\":" + i.getPoids() + ",\"taille\":" + i.getTaille() + "}");
        req.addRequestHeader("accept", "application/json");
        req.setRequestBody(json);
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
                System.out.println(req.getRequestBody());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    public boolean addIMC(IMC i) {

        String url = Statics.BASE_URL + "/api/addIMC";
        String json = ("{\"id_utilisateur\":" + 1 + ",\"poids\":" + i.getPoids() + ",\"taille\":" + i.getTaille() + "}");
        
        req.addRequestHeader("accept", "application/json");
        req.setRequestBody(json);
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
}
