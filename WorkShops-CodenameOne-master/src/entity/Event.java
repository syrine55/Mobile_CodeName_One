package entity;

import java.util.Date;

/**
 *
 * @author fedi
 */
public class Event {

	private String titre;
	private int id;
	private String date;
	private String heure;
	private String addresse;
	private String type;
	private String descr;
	private int nombre;
	private int nombreDispo;

	private String validation;
	private String lien;

	public Event(String titre, String date, String heure, String addresse, String type, int nombre, String validation) {
		this.titre = titre;
		this.date = date;
		this.heure = heure;
		this.addresse = addresse;
		this.type = type;
		this.nombre = nombre;
		this.validation = validation;
	}

	public Event(String titre, String date, String heure, String addresse, String type, int nombre, int nombreDispo,
			String lien, int id) {
		this.titre = titre;
		this.date = date;
		this.heure = heure;
		this.addresse = addresse;
		this.type = type;
		this.nombre = nombre;
		this.nombreDispo = nombreDispo;
		this.lien = lien;
		this.id = id;
	}

	public Event(String titre, String date, String heure, String type, int nombre, String lien, String addresse,
			String descr) {
		this.titre = titre;
		this.date = date;
		this.heure = heure;
		this.addresse = addresse;
		this.type = type;
		this.nombre = nombre;
		this.descr = descr;

		this.lien = lien;

	}

	public Event(String titre, String date, String heure, String type, int nombre, String lien, String addresse,
			String descr, int id) {
		this.titre = titre;
		this.date = date;
		this.heure = heure;
		this.addresse = addresse;
		this.type = type;
		this.nombre = nombre;
		this.descr = descr;

		this.lien = lien;
		this.id = id;

	}

	public void setId(int id) {
		this.id = id;
	}

	public Event() {
		// TODO Auto-generated constructor stub
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAdresse(String addresse) {
		this.addresse = addresse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public int getNombreDispo() {
		return nombreDispo;
	}

	public void setNombreDispo(int nombreDispo) {
		this.nombreDispo = nombreDispo;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}

	public int getId() {
		return id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

}