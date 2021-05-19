/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class HomeEvent extends Form {
	String role = "cl";

	Form current;
	/*
	 * Garder traçe de la Form en cours pour la passer en paramètres aux interfaces
	 * suivantes pour pouvoir y revenir plus tard en utilisant la méthode showBack
	 */

	public HomeEvent() {
		current = this; // Récupération de l'interface(Form) en cours
		setTitle("Home");
		setLayout(BoxLayout.y());

		add(new Label("Choose an option"));
		Button btnAddTask = new Button("Add Event");
		Button btnListTasks = new Button("List Events");
		Button btnParticiper = new Button("Participer");
		Button btnUserEvent = new Button("Mes Event");
		if (role.equals("spec")) {
			btnAddTask.addActionListener(e -> new AddEventForm(current).show());
			btnListTasks.addActionListener(e -> new ListEvent(current).show());
			addAll(btnAddTask, btnListTasks);

		} else {
			btnParticiper.addActionListener(e -> new ListUserEvent(current).show());
			btnUserEvent.addActionListener(e -> new MesEventLoader(current).show());
			addAll(btnParticiper, btnUserEvent);

		}

	}

}
