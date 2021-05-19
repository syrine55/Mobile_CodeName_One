/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceExercice;
import com.mycompany.myapp.services.ServiceIMC;
import entity.Exercise;
import entity.IMC;
import java.util.ArrayList;

/**
 *
 * @author Mohamed
 */
public class ListExerciceForm extends BaseForm {

    Form current = this;

    public ListExerciceForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);

        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Espace Exercice");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e -> {

        });
        Tabs swipe = new Tabs();
        Label sw1 = new Label();
        Label sw2 = new Label();
        Image img = res.getImage("back-logo-imc.jpg");
        //   System.out.println(img);

        addTab(swipe, sw1, res.getImage("back-logo-imc.jpg"), "", res);

        addTab(swipe, sw2, res.getImage("back-logo-imc.jpg"), "", res);

        ///////////////////////////////////
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, sw1, sw2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes IMCs", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Exercice", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter IMC ", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            refreshTheme();
            new ListIMCForm(res).show();

        });

        liste.addActionListener((e) -> {
            refreshTheme();
            new ListExerciceForm(res).show();

        });
        partage.addActionListener((e) -> {
            refreshTheme();
            new AddIMCForm(res).show();

        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList<IMC> listimc = ServiceIMC.getInstance().getAllIMCs();
        IMC imc1= listimc.get(listimc.size()-1);
        double d= ( (imc1.getTaille() /100) * (imc1.getTaille() /100) ) *21;
        
        String targetw = String.valueOf((int) Math.round(d)  );
        String currentstatus= getStatus(imc1.getScore());
        TextArea label1 = new TextArea("Poids parfait : "+targetw);
        label1.setUIID("NewsTopLine");
        label1.setEditable(false);
        
        TextArea label2 = new TextArea("Poid actuelle : "+String.valueOf((int) Math.round(imc1.getPoids())  ));
        label2.setUIID("NewsTopLine");
        label2.setEditable(false);
        
        TextArea label3 = new TextArea("Status  : "+currentstatus);
        label3.setUIID("NewsTopLine");
        label3.setEditable(false);
        
         TextArea label4 = new TextArea("List des exercices: ");
        label4.setUIID("NewsTopLine");
        label4.setEditable(false);
        
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);

        ArrayList<Exercise> list = ServiceExercice.getInstance().getAllExercises();
        for (Exercise rec : list) {
            if (imc1.getScore()<21) {
                if (rec.getType().equals("minceur")) {
                    String urlimg = "Logo.png";
            Image plch = Image.createImage(120, 90);
            
            EncodedImage e = EncodedImage.createFromImage(plch, false);
            URLImage urlim = URLImage.createToStorage(e, urlimg, urlimg, URLImage.RESIZE_SCALE);
            addButton(urlim, rec, res);
            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container cimg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                    
                }
                
            } else {
                
                if (rec.getType().equals("grossir")) {
                    String urlimg = "Logo.png";
            Image plch = Image.createImage(120, 90);
            
            EncodedImage e = EncodedImage.createFromImage(plch, false);
            URLImage urlim = URLImage.createToStorage(e, urlimg, urlimg, URLImage.RESIZE_SCALE);
            addButton(urlim, rec, res);
            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container cimg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                    
                }
            
            }
            

        }
        //////////////////////////////////////

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imagesc = new ScaleImageLabel(image);
        imagesc.setUIID("Container");
        imagesc.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overl = new Label("", "ImageOverlay");
        Container page1
                = LayeredLayout.encloseIn(
                        imagesc,
                        overl,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(),
                                        spacer
                                )));
        swipe.addTab("", res.getImage("logo.png"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Image img, Exercise rec, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container c = BorderLayout.west(image);
        TextArea t = new TextArea("Nom: " + rec.getNom());
        t.setUIID("NewsTopLine");
        t.setEditable(false);
        TextArea t2 = new TextArea("Description: " + rec.getDescription());
        t2.setUIID("NewsTopLine");
        t2.setEditable(false);
        TextArea t3 = new TextArea("Type: " + rec.getType());
        t3.setUIID("NewsTopLine");
        t3.setEditable(false);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        c.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(t), t2, t3));
        add(c);

    }
    String getStatus(double score){
        if(score >= 25) { 
          return "Grossir";
        } else if (score >= 18.5) {
            return  "Normal. Bon travail!";
        }else {
            return  "Minceur";
        }
    }
}
