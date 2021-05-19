/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;


import Utils.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
//import com.google.gson.Gson;
import entity.FosUser;
//import static Services.Eventservice.instance;
import Utils.Statics;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author cobwi
 */
public class UserManager {
  public static UserManager instance=null;
    private FosUser persistedUser = null;
    Map<String, Object> users = null;
    List<FosUser> listOfUser = null;
  public boolean resultOK;
    private ConnectionRequest req;

    public UserManager() {
        req = new ConnectionRequest();
    }
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    int test =0;
 
  
    
    
  
  public boolean addFeed(FosUser t) {
        String url = Statics.BASE_URL2 + "api/user/ajouter?email=" + t.getEmail() + "&nom=" + t.getNom()+ "&password=" + t.getPassword()+"&prenom=" + t.getPrenom()+"&adresse=" + t.getAdresse(); 
        req.setUrl(url);
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
   public boolean login(FosUser t) {
        String url = Statics.BASE_URL2 + "api/user/login?email=" + t.getEmail() + "&password=" + t.getPassword(); 
        req.setUrl(url);
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
    public List<FosUser> getAll() {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/api/user/get/all");
        //  req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/");
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            listOfUser = new ArrayList<FosUser>();
            try {
                JSONParser j = new JSONParser();
                users = j.parseJSON(new CharArrayReader(s.toCharArray()));
                List listMappedUser = (List) users.get("root");
                for (int i = 0; i < listMappedUser.size(); i++) {
                    Map get = (Map) listMappedUser.get(i);
                    FosUser user = new FosUser();
                    user.setId(Integer.valueOf(get.get("id").toString().substring(0, get.get("id").toString().indexOf('.'))));
                    user.setNom(get.get("nom").toString());
                    user.setPrenom(get.get("prenom").toString());
                    user.setEmail(get.get("email").toString());
                    listOfUser.add(user);
                }
            } catch (IOException ex) {
            }
            if (s.equals("fail")) {
                System.out.println("Persist : failed");
            } else {
                System.out.println("Persist : success");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return listOfUser;
    }

    public List<FosUser> getAllButNotMe() {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/api/user/get/all");
        //  req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/");
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            listOfUser = new ArrayList<FosUser>();
            try {
                JSONParser j = new JSONParser();
                users = j.parseJSON(new CharArrayReader(s.toCharArray()));
                List listMappedUser = (List) users.get("root");
                for (int i = 0; i < listMappedUser.size(); i++) {
                    Map get = (Map) listMappedUser.get(i);
                   FosUser user = new FosUser();
                    int id = Integer.valueOf(get.get("id").toString().substring(0, get.get("id").toString().indexOf('.')));
                    if (FosUser.getIdOfConnectedUser() != id) {
                        user.setId(id);
                        user.setNom(get.get("nom").toString());
                        user.setPrenom(get.get("prenom").toString());
//                        user.setPhotoProfil(get.get("photo_profile").toString());
                        listOfUser.add(user);
                    }
                }
            } catch (IOException ex) {
            }
            if (s.equals("fail")) {
                System.out.println("Persist : failed");
            } else {
                System.out.println("Persist : success");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return listOfUser;
    }

    public FosUser persist(FosUser user) {
        //Gson g = new Gson();
      //  String jsonString = g.toJson(user);
        // let's send it
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/");
        //  req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/");

        req.setHttpMethod("POST");
      //  req.addArgument("object", jsonString);
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            persistedUser = mapToJson(s);
            System.out.println("RESPON" + s);
            if (s.equals("fail")) {
                //  main.show();
                System.out.println("Persist : failed");
            } else {
                System.out.println("Persist : success");
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return persistedUser;
    }

    public FosUser persist(FosUser user, String socialId) {
        // perform addition check before persisting
        ConnectionRequest req1 = new ConnectionRequest();
        req1.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/login" );
        req1.setHttpMethod("GET");
        req1.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req1.getResponseData();
            String s = new String(data);
            if (s.equals("notfound")) {
                //  main.show();
                System.out.println("NOT FOUND SO CREATIG NEW USER ...");
                //we can call the original persist
               // user.setSocialid(socialId);
                persistedUser = persist(user);
            } else {
                persistedUser = mapToJson(s);
                System.out.println("FOUND WITH ID : success");
            }

        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req1);
        req1.setDisposeOnCompletion(dlg);
        return persistedUser;
    }

    public FosUser checkExistanceBySocialId(String id) {
        ConnectionRequest req1 = new ConnectionRequest();
        req1.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/check/user/" + id);
        req1.setHttpMethod("GET");
        req1.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req1.getResponseData();
            String s = new String(data);
            System.out.println("RESPON" + s);
            if (s.equals("notfound")) {
                //  main.show();
                System.out.println("NOT FOUND ");
                //we can call the original persist
                persistedUser = null;
            } else {
                persistedUser = mapToJson(s);
                System.out.println("FOUND WITH SOCIAL ID : success");
            }
        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req1);
        req1.setDisposeOnCompletion(dlg);
        System.out.println("RETURNED THIS FROM CHECK WITH SOCIAL ID : " + persistedUser);
        return persistedUser;
    }

    public FosUser mapToJson(String json) {
        FosUser user = new FosUser();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));
            int userId = Integer.valueOf(users.get("id").toString().substring(0, users.get("id").toString().indexOf('.')));

            if (users != null) {
                user.setId(userId);
                user.setPassword(users.get("password").toString());
                user.setEmail(users.get("email").toString());
               
               // user.setEmail(users.get("email").toString());
                //user.setEnabled(users.get("enabled").toString().equals("true") ? 1 : 0);
                user.setNom(users.get("nom").toString());
                user.setPrenom(users.get("prenom").toString());
              

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Invalid Data JsonResponse : " + json);
        }
        return user;

    }

}
