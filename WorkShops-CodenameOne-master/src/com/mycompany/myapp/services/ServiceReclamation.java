
package com.mycompany.myapp.services;

/**

 *
 * @author 21692
 */
    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import static com.mycompany.myapp.utils.Statics.BASE_URL;
import entity.Reclamation;
//import com.mycomany.entities.Reclamation;
//import com.mycomany.utils.Statics;
//import static com.mycomany.utils.Statics.BASE_URL;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceReclamation {

    public ArrayList<Reclamation> reclamation;
    
    public static ServiceReclamation instance=null;
    public static boolean resultOK=true;
    private ConnectionRequest req;

    private int userId = ServiceLogin.id_user;
    
    private ServiceReclamation() {
         req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public boolean addReclamation(Reclamation reclamation) {
        String url = BASE_URL+"/AddQuestion/new?ad="+reclamation.getAdresse()+"&ser="+reclamation.getService()+"&type="+reclamation.getType_message()+"&msg="+reclamation.getContenu_message();
        


        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Reclamation> parseReclamation(String jsonText){
        try {
            reclamation=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du rÃ©sultat json

            
            
            Map<String,Object> reclamtionListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)reclamtionListJson.get("root");
            
            //Parcourir la liste des tÃ¢ches Json
            for(Map<String,Object> obj : list){
                //CrÃ©ation des tÃ¢ches et rÃ©cupÃ©ration de leurs donnÃ©es
                Reclamation t = new Reclamation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
               
                t.setAdresse(obj.get("adresse").toString());
                t.setService(obj.get("service").toString());
                t.setType_message(obj.get("type_message").toString());
                t.setContenu_message(obj.get("contenu_message").toString());
                
                reclamation.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu rÃ©cupÃ©rer une liste des tÃ¢ches Ã  partir
        de la base de donnÃ©es Ã  travers un service web
        
        */
        return reclamation;
    }
    
    public ArrayList<Reclamation> getAllReclamation(){
        String url = Statics.BASE_URL+"AllQuestion";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamation = parseReclamation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamation;
    }
public boolean deleteReclamation(int id){
String url = Statics.BASE_URL+"/DetailQuestion?id="+id;
req.setUrl(url);
req.addResponseListener(new ActionListener<NetworkEvent>()
{
public void actionPerformed(NetworkEvent evt)
{
req.removeResponseCodeListener(this);
}
});
NetworkManager.getInstance().addToQueueAndWait(req);
return resultOK;
}
   public boolean modifierReclamation(Reclamation reclamation){
        String url = BASE_URL+"/EditReclamation?id="+reclamation.getId()+"&ad="+reclamation.getAdresse()+"&ser="+reclamation.getService()+"&type="+reclamation.getType_message()+"&msg="+reclamation.getContenu_message();

req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
   public String mailreclamation(String service,String msg)
    {
        String url = Utils.Statics.BASE_URL+"apijson/reclamation/"+userId+"/"+service+"/"+msg;
        ConnectionRequest req = new ConnectionRequest(url) ;
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode()==200 ;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return "dd" ;

    }  
}


