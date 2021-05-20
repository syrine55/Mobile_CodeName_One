/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.spinner.TimeSpinner;
import com.codename1.ui.util.Resources;
//import com.mycomany.entities.Consommation;
import com.mycompany.myapp.services.ServicesConsommation;
import entity.Consommation;
import java.util.Date;
/**
 *
 * @author Mohamed MOKHTAR
 */

public class EditConsommation extends BaseForm {
    Form current ;
    Form previous ;
    private Resources res;
   
    public EditConsommation (Consommation consommation ) {
        setTitle("Ajouter votre consommation") ;
        setLayout(BoxLayout.y());
        getStyle().setBgColor(0x1b4f72 );

        
        Picker heure_reveil  = new Picker() ;
        heure_reveil.setType(Display.PICKER_TYPE_TIME);
        heure_reveil.setUIID("TextFieldWhite") ;
        addStringValue("Heure de reveil" , heure_reveil)  ;
      
        Picker heure_dormir = new Picker() ;
        heure_dormir.setType(Display.PICKER_TYPE_TIME);
        heure_dormir.setUIID("TextFieldWhite") ;
        addStringValue("Heure de dormir" , heure_dormir)  ;

        TextField ctrl_consomation = new TextField("" ,"Consommation/Jour") ;
        ctrl_consomation.setUIID("TextFieldWhite") ;
        addStringValue("Contrôler votre consommation" , ctrl_consomation)  ;

        TextField consomation = new TextField("", " Votre consommation") ;
        consomation.setUIID("TextFieldWhite") ;
        addStringValue("Votre consommation / Jr" , consomation)  ;
        
        
        ctrl_consomation.setText(consommation.getCtrl_consomation());
        
        consomation.setText(consommation.getConsomation());
        
        Button bntAdd = new Button("Modifier") ;
        addStringValue("" , bntAdd);
        Button mesListes = new Button("back");
mesListes.addActionListener((e) -> {
    refreshTheme();
        new AddConsommation(AddConsommation.rss).show();
        
            
        });
add(mesListes);
        bntAdd.addActionListener((e)->{
            try {
                if ( heure_reveil.getText() ==""  || heure_dormir.getText() =="" || ctrl_consomation.getText() =="" || consomation.getText() =="" )
                {
                    Dialog.show("Veuillez vérifier les données" , "","Annuler" , "OK") ;
                }
                else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Consommation gestionEau = new Consommation (consommation.getId(),String.valueOf(heure_reveil.getText()).toString() , String.valueOf(heure_dormir.getText()).toString()  ,
                            String.valueOf(ctrl_consomation.getText()).toString() , String.valueOf(consomation.getText()).toString() ) ;
                    System.out.println("DATA = "+gestionEau );
                    ServicesConsommation.getInstance().modif(gestionEau);
                    iDialog.dispose();
//                    new AddConsommation(res).show() ;
                    refreshTheme();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s , "PaddedLabel"))
                .add(BorderLayout.CENTER , v)) ;
        add(createLineSeparator(0xeeeeee));
     
    }
    

}

