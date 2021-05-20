/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

/**
 *
 * @author Mohamed MOKHTAR
 */
import com.codename1.io.*;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
//import com.mycomany.entities.Consommation;
import Utils.Statics;
import com.mycompany.gui.EditConsommation;
import entity.Consommation;

import java.io.IOException;
import java.util.ArrayList;


public class ServicesConsommation {

   
    public ArrayList<Consommation> consommationEntitys ;

    public static ServicesConsommation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    private int userId = ServiceLogin.id_user;
    public static double ctrl , cons ; 
    
    private ServicesConsommation() {
        req = new ConnectionRequest();
    }

    public static ServicesConsommation getInstance() {
        if (instance == null) {
            instance = new ServicesConsommation();
        }
        return instance;
    }

    Consommation consommationEntity ;

    public boolean addConsommation(Consommation consommationEntity)
    {
        String url = Statics.BASE_URL+"AddGestionEau/new?hr="+consommationEntity.getHeure_reveil()+"&hd="+consommationEntity.getHeure_dormir()+"&cc="+consommationEntity.getCtrl_consomation()
                +"&c="+consommationEntity.getConsomation();
        ConnectionRequest req = new ConnectionRequest(url) ;
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode()==200 ;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK ;

    }

   
public ArrayList<Consommation> parseTasks(String jsonText) {
		try {
			consommationEntitys = new ArrayList<>();
			JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat
											// json

			/*
			 * On doit convertir notre réponse texte en CharArray à fin de permettre au
			 * JSONParser de la lire et la manipuler d'ou vient l'utilité de new
			 * CharArrayReader(json.toCharArray())
			 * 
			 * La méthode parse json retourne une MAP<String,Object> ou String est la clé
			 * principale de notre résultat. Dans notre cas la clé principale n'est pas
			 * définie cela ne veux pas dire qu'elle est manquante mais plutôt gardée à la
			 * valeur par defaut qui est root. En fait c'est la clé de l'objet qui englobe
			 * la totalité des objets c'est la clé définissant le tableau de tâches.
			 */
			Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			/*
			 * Ici on récupère l'objet contenant notre liste dans une liste d'objets json
			 * List<MAP<String,Object>> ou chaque Map est une tâche.
			 * 
			 * Le format Json impose que l'objet soit définit sous forme de clé valeur avec
			 * la valeur elle même peut être un objet Json. Pour cela on utilise la
			 * structure Map comme elle est la structure la plus adéquate en Java pour
			 * stocker des couples Key/Value.
			 * 
			 * Pour le cas d'un tableau (Json Array) contenant plusieurs objets sa valeur
			 * est une liste d'objets Json, donc une liste de Map
			 */
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

			// Parcourir la liste des tâches Json
			for (Map<String, Object> obj : list) {
				// Création des tâches et récupération de leurs données
				Consommation e = new Consommation();
				String heure_reveil = obj.get("heure_reveil").toString();
                                e.setUser_id((int) Float.parseFloat(obj.get("id_user").toString()));
				e.setHeure_reveil(heure_reveil);
				e.setHeure_dormir(obj.get("heure_dormir").toString());
				e.setCtrl_consomation(obj.get("ctrl_consomation").toString());
				e.setConsomation(obj.get("consomation").toString());

				e.setId((int) Float.parseFloat(obj.get("id").toString()));
                                
//                                
//                                if ( (int) Float.parseFloat(obj.get("id").toString()) == userId )
//                               
//                                {
//                                    ctrl = (int) Float.parseFloat(obj.get("ctrl_consomation").toString()) ;
//                                    cons = (int) Float.parseFloat(obj.get("consomation").toString()) ;
//                                }
				// Ajouter la tâche extraite de la réponse Json à la liste
				consommationEntitys.add(e);
			}

		} catch (IOException ex) {

		}
		/*
		 * A ce niveau on a pu récupérer une liste des tâches à partir de la base de
		 * données à travers un service web
		 * 
		 */
		return consommationEntitys ;
	}


    public boolean supprimer (Consommation c ) {
        String url = Statics.BASE_URL + "DeleteGestionEau?id="+c.getId();
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

   
    
    public boolean modif(Consommation c) {
		String url = Statics.BASE_URL + "EditGestionEau?id=" + c.getId() + "&hr=" + c.getHeure_reveil()+ "&hd="
				+ c.getHeure_dormir()+ "&cc=" + c.getCtrl_consomation()+ "&c=" + c.getConsomation()  ;
		req.setUrl(url);// Insertion de l'URL de notre demande de connexion
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
				req.removeResponseListener(this); // Supprimer cet actionListener
				/*
				 * une fois que nous avons terminé de l'utiliser. La ConnectionRequest req est
				 * unique pour tous les appels de n'importe quelle méthode du Service task, donc
				 * si on ne supprime pas l'ActionListener il sera enregistré et donc éxécuté
				 * même si la réponse reçue correspond à une autre URL(get par exemple)
				 */

			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}
    
    
    
    
     public Container getAllGestionEau(){
        String url = Statics.BASE_URL+"AllGestionEau/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                consommationEntitys = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        Container container1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container container2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container container3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container container4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container container5 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button supprimer=new Button("Supprimer");
        Button edit=new Button("Modifier");
        Consommation consommation = new Consommation() ;
        for (Consommation cc:consommationEntitys)
        {
            if(cc.getUser_id() == userId )
                
            {
                consommationEntity=cc ;
                
        container1.add(consommationEntity.getHeure_reveil()) ;
        container2.add(consommationEntity.getHeure_dormir()) ;
        container3.add(consommationEntity.getConsomation()) ;
        container4.add(consommationEntity.getCtrl_consomation()) ;
        container5.addAll(container1 , container2 ,container3,container4,supprimer , edit ) ;
        
     ctrl = Double.parseDouble(consommationEntity.getCtrl_consomation() );
    cons =Double.parseDouble(consommationEntity.getConsomation() );
        System.out.println("ctrllllllllll =="+ctrl );
            }

        }
        supprimer.addActionListener((e) -> {
            if(Dialog.show("Valider", "Voulez vous vraiment supprimer ? ", "Confirmer", "Annuler"))
            {
                this.supprimer(consommationEntity);
            }
                } );

     
        edit.addActionListener((e) -> {
				new EditConsommation(consommationEntity).show();

			});
        
        return container5 ;
        }
     
 
  
   public String mailfeedbacl(String ch)
    {
        String url = Statics.BASE_URL+"apijson/contact/"+userId+"/"+ch;
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








