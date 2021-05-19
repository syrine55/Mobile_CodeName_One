/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the .
 */
package com.mycompany.myapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entity.Event;
import com.mycompany.gui.EditEventForm;
import com.mycompany.gui.GestionEvent;
import com.mycompany.myapp.utils.Statics;

public class ServiceEvent {
	private int userID = 76;

	public ArrayList<Event> events;
	public static int nbrSalle,nbrEnligne;

	public static ServiceEvent instance = null;
	public boolean resultOK;
	private ConnectionRequest req;

	private ServiceEvent() {
		req = new ConnectionRequest();
	}

	public static ServiceEvent getInstance() {
		if (instance == null) {
			instance = new ServiceEvent();
		}
		return instance;
	}

	public boolean addTask(Event e) {
		String url = Statics.BASE_URL + "/apifedi/addEvent?titre=" + e.getTitre() + "&date=" + e.getDate() + "&heure="
				+ e.getHeure() + "&nombre=" + e.getNombre() + "&type=" + e.getType() + "&descr=" + e.getDescr()
				+ "&lienMeet=" + e.getLien() + "&addresse=" + e.getAddresse();
		req.setUrl(url);// Insertion de l'URL de notre demande de connexion
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
				req.removeResponseListener(this); // Supprimer cet actionListener
				/*
				 * une fois que nous avons termin� de l'utiliser. La ConnectionRequest req est
				 * unique pour tous les appels de n'importe quelle m�thode du Service task, donc
				 * si on ne supprime pas l'ActionListener il sera enregistr� et donc �x�cut�
				 * m�me si la r�ponse re�ue correspond � une autre URL(get par exemple)
				 */

			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public boolean modif(Event e) {
		String url = Statics.BASE_URL + "/apifedi/editEvent?id=" + e.getId() + "&titre=" + e.getTitre() + "&date="
				+ e.getDate() + "&heure=" + e.getHeure() + "&nombre=" + e.getNombre() + "&type=" + e.getType()
				+ "&descr=" + e.getDescr() + "&lienMeet=" + e.getLien() + "&addresse=" + e.getAddresse();
		req.setUrl(url);// Insertion de l'URL de notre demande de connexion
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
				req.removeResponseListener(this); // Supprimer cet actionListener
				/*
				 * une fois que nous avons termin� de l'utiliser. La ConnectionRequest req est
				 * unique pour tous les appels de n'importe quelle m�thode du Service task, donc
				 * si on ne supprime pas l'ActionListener il sera enregistr� et donc �x�cut�
				 * m�me si la r�ponse re�ue correspond � une autre URL(get par exemple)
				 */

			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public ArrayList<Event> parseTasks(String jsonText) {
		try {
			events = new ArrayList<>();
			JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du r�sultat
											// json

			/*
			 * On doit convertir notre r�ponse texte en CharArray � fin de permettre au
			 * JSONParser de la lire et la manipuler d'ou vient l'utilit� de new
			 * CharArrayReader(json.toCharArray())
			 * 
			 * La m�thode parse json retourne une MAP<String,Object> ou String est la cl�
			 * principale de notre r�sultat. Dans notre cas la cl� principale n'est pas
			 * d�finie cela ne veux pas dire qu'elle est manquante mais plut�t gard�e � la
			 * valeur par defaut qui est root. En fait c'est la cl� de l'objet qui englobe
			 * la totalit� des objets c'est la cl� d�finissant le tableau de t�ches.
			 */
			Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			/*
			 * Ici on r�cup�re l'objet contenant notre liste dans une liste d'objets json
			 * List<MAP<String,Object>> ou chaque Map est une t�che.
			 * 
			 * Le format Json impose que l'objet soit d�finit sous forme de cl� valeur avec
			 * la valeur elle m�me peut �tre un objet Json. Pour cela on utilise la
			 * structure Map comme elle est la structure la plus ad�quate en Java pour
			 * stocker des couples Key/Value.
			 * 
			 * Pour le cas d'un tableau (Json Array) contenant plusieurs objets sa valeur
			 * est une liste d'objets Json, donc une liste de Map
			 */
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

			// Parcourir la liste des t�ches Json
			for (Map<String, Object> obj : list) {
				// Cr�ation des t�ches et r�cup�ration de leurs donn�es
				Event e = new Event();
				String titre = obj.get("titre").toString();
				e.setTitre(titre);
				e.setDescr(obj.get("descr").toString());
				e.setType(obj.get("type").toString());
				e.setDate(obj.get("descr").toString());
				e.setHeure(obj.get("heure").toString());
				e.setNombre((int) Float.parseFloat(obj.get("nombre").toString()));
				e.setNombreDispo((int) Float.parseFloat(obj.get("nombreDispo").toString()));
				// e.setNombre(5);
				e.setLien(obj.get("lienMeet").toString());
				e.setAddresse(obj.get("addresse").toString());
				e.setId((int) Float.parseFloat(obj.get("id").toString()));
				// Ajouter la t�che extraite de la r�ponse Json � la liste
				events.add(e);
			}

		} catch (IOException ex) {

		}
		/*
		 * A ce niveau on a pu r�cup�rer une liste des t�ches � partir de la base de
		 * donn�es � travers un service web
		 * 
		 */
		return events;
	}

	public boolean supp(Event e) {
		String url = Statics.BASE_URL + "/apifedi/deleteEvent?id=" + e.getId();
		req.setUrl(url);// Insertion de l'URL de notre demande de connexion
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
				req.removeResponseListener(this); // Supprimer cet actionListener
				/*
				 * une fois que nous avons termin� de l'utiliser. La ConnectionRequest req est
				 * unique pour tous les appels de n'importe quelle m�thode du Service task, donc
				 * si on ne supprime pas l'ActionListener il sera enregistr� et donc �x�cut�
				 * m�me si la r�ponse re�ue correspond � une autre URL(get par exemple)
				 */

			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public boolean participer(Event e) {
		String url = Statics.BASE_URL + "/apifedi/usereventsParticiper/?userID=" + ServiceLogin.id_user + "&eventID=" + e.getId();
		req.setUrl(url);// Insertion de l'URL de notre demande de connexion
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
				req.removeResponseListener(this); // Supprimer cet actionListener
				/*
				 * une fois que nous avons termin� de l'utiliser. La ConnectionRequest req est
				 * unique pour tous les appels de n'importe quelle m�thode du Service task, donc
				 * si on ne supprime pas l'ActionListener il sera enregistr� et donc �x�cut�
				 * m�me si la r�ponse re�ue correspond � une autre URL(get par exemple)
				 */

			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public Container getAllTasks() {
		String url = Statics.BASE_URL + "/apifedi/events/";
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				events = parseTasks(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		Container container1All = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		nbrEnligne=0;
		nbrSalle=0;
		for (Event event : events) {
			if(event.getType().equals("En Ligne")) {
				
				nbrEnligne++;
			}
			else if(event.getType().equals("En Salle")) {
				
				nbrSalle++;
			}
			

			Container container = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
			Container container1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
			Container container2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

			Button supprimer = new Button("Supprimer");
			Button edit = new Button("Modifier");

			supprimer.addActionListener((e) -> {
				if (Dialog.show("Valider", "Voulez vous vraiment supprimer ce evenement ? ", "Confirmer", "Annuler")) {
					this.supp(event);

				}

			});

			edit.addActionListener((e) -> {
				new EditEventForm(GestionEvent.res1,event).show();

			});

			container1.addAll(supprimer, edit);
			// container1.add(btn2);
			container2.add(event.getTitre());
			container2.add(event.getType());
			container1All.addAll(container2, container1);
		}
		return container1All;
	}

	public Container getEventsUser() {
		String url = Statics.BASE_URL + "/apifedi/userevents/";
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				events = parseTasks(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		Container container1All = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		for (Event event : events) {

			Container container = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
			Container container1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
			Container container2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

			Button part = new Button("Participer");
			Button edit = new Button("Modifier");

			part.addActionListener((e) -> {
				if (Dialog.show("Valider", "Voulez vous vraiment participer a ce evenement ? ", "Confirmer",
						"Annuler")) {
					this.participer(event);

				}

			});

			container1.add(part);
			// container1.add(btn2);
			container2.add(event.getTitre());
			container2.add(event.getType());
			container1All.addAll(container2, container1);
		}
		return container1All;
	}

	public Container mesEventLoader() {
		String url = Statics.BASE_URL + "/apifedi/usereventDataloader/?userID=" + ServiceLogin.id_user;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				events = parseTasks(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		Container container1All = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		for (Event event : events) {

			Container container = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
			Container container1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
			Container container2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

			// container1.add(part);
			// container1.add(btn2);
			container2.add(event.getTitre());
			container2.add(event.getType());
			container1All.addAll(container2, container1);
			
		}
		Label empty = new Label("pas d'evenement");

		if(events.isEmpty()) {
			container1All.add(empty);
		}
		return container1All;
	}
}
