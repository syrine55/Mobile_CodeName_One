/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceCategorie;
import com.mycompany.myapp.services.ServiceMeditation;
import entity.Categorie;
import entity.Meditation;
import java.io.IOException;

/**
 *
 * @author Mega Pc
 */
public class AddMeditationForm extends BaseForm {

    public AddMeditationForm(Resources res) {
        super("Ajouter un Espace", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter un Espace");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("med.jpg"), spacer1, "", "", "Bienvenue dans nos espace de méditation.");
        //addTab(swipe, res.getImage("dog.jpg"), spacer2, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");

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

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Méditation", barGroup);
        all.setUIID("SelectBar");
        RadioButton addmed = RadioButton.createToggle("Ajouter", barGroup);
        addmed.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, addmed),
                FlowLayout.encloseBottom(arrow)
        ));

        addmed.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(addmed, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(addmed, arrow);
        
        all.addActionListener(e -> new GestionMeditationForm(res).show());
        addmed.addActionListener(e -> new AddMeditationForm(res).show());


        TextField tfName =new TextField("","Nom espace");
        tfName.setUIID("TextFieldBlack");
        ComboBox categorie=new ComboBox();
        for (Categorie c : ServiceCategorie.getInstance().getAllCategories()) {
            categorie.addItem(c);
        }

        TextField desc =new TextField("","Description..........");
        desc.setUIID("TextFieldBlack");
        
       // TextField tfphoto =new TextField("","Image..........");
       // desc.setUIID("TextFieldBlack");
        
        Button btnUpload = new Button("Upload image");
        
        Button btnValider = new Button("Ajouter");
        
        btnValider.addActionListener(e->{
                if (tfName.getText().equals("") || desc.getText().equals("")) {
                Dialog.show("Alert", "Merci de remplir tout les champs", new Command("ok"));
            } else {
                if (new ServiceMeditation().getDupliquerNomMeditation(tfName.getText()).equals(tfName.getText())) {
                    Dialog.show("Alert", "le nom de l'espace " + tfName.getText() + " existe deja merci de le changer", new Command("ok"));
                } else {
                    Meditation m = new Meditation(tfName.getText(), desc.getText());
                    if (new ServiceMeditation().addMeditation(m, (Categorie) categorie.getSelectedItem())) {
                        Dialog.show("Succcess", "Nouveau espace ajouter avec une publication sur notre page FACEBOOK", new Command("ok"));
                        tfName.setText("");
                        desc.setText("");
                    } else {
                        Dialog.show("Error", "Server error", new Command("ok"));
                    }
                }
            }
        });
        addAll(tfName,categorie,desc,btnUpload,btnValider);//c,tfphoto

        btnUpload.addActionListener(e
                -> {
            String path = Capture.capturePhoto(1,1);
            if (path != null) {
                try {
                    Image img = Image.createImage(path);
                   // String ch= path.substring(45);
                   System.out.println(img.getImageName());
                    System.out.println(path);
                   // tfphoto.setText(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            }         
        );
         
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });


    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        //FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        //likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        //FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}

