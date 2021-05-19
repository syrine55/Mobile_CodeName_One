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
import entity.Exercise;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohamed
 */
public class ServiceExercice {
    
    public ArrayList<Exercise> exercises;
    public static ServiceExercice instance;
    public boolean resultOk;
    private ConnectionRequest req;
    
     public ServiceExercice() {
        req =new ConnectionRequest();
    }
    
    public static ServiceExercice getInstance(){
        if(instance == null)
            instance=new ServiceExercice();
        return instance;
    }
    
    //affichage
    public ArrayList<Exercise> parseExercises(String jsonText){
        exercises=new ArrayList<>();
        JSONParser j=new JSONParser();
        Map<String,Object> exercisesListJson;
        try {
            exercisesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list= (List<Map<String,Object>>)exercisesListJson.get("root");
            for(Map<String,Object>obj:list){
                Exercise i =new Exercise();
                float id=Float.parseFloat(obj.get("id").toString());
                i.setId((int)id);
                i.setNom(obj.get("nom").toString());
                i.setDescription(obj.get("description").toString());
                i.setType(obj.get("type").toString());
                
                exercises.add(i);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return exercises;
    }
    
    public ArrayList<Exercise> getAllExercises(){
        String url = Statics.BASE_URL+"/api/exercises";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                exercises = parseExercises(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return exercises;
    }
}
