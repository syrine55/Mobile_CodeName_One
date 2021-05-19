/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.messaging.Message;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceIMC;
import entity.IMC;
import java.util.ArrayList;
/**
 *
 * @author Mohamed
 */
public class AddIMCForm extends BaseForm{
   
    Form current;
    public AddIMCForm(Resources res){
      super("Newsfeed",BoxLayout.y());
      Toolbar tb=new Toolbar(true);
      current=this;
      setToolbar(tb);
      getTitleArea().setUIID("Container");
      setTitle("Ajout IMC");
      getContentPane().setScrollVisible(false);
      
      
      tb.addSearchCommand(e->{
          
      });
      Tabs swipe =new Tabs();
      Label sw1=new Label();
      Label sw2=new Label();
      Image img=res.getImage("back-logo-imc.jpg");
     //   System.out.println(img);
      
      addTab(swipe,sw1,res.getImage("back-logo-imc.jpg"),"" ,res);
      
       addTab(swipe,sw2,res.getImage("back-logo-imc.jpg"),"" ,res);
      
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
        
       
       //////////////////////////////////////
      
      TextField objet1=new TextField("","Saisir poids ");
      objet1.setUIID("TextFieldBlack");
      addStringValue("Poids",objet1);  
      
      

       TextField objet2=new TextField("","Saisir taille");
      objet2.setUIID("TextFieldBlack");
      addStringValue("Taille",objet2);
      
      
       Button btnAjouter=new Button("Ajouter");
      addStringValue("",btnAjouter);
      btnAjouter.addActionListener((e)->{
        try{
            if (objet1.getText()==""||objet2.getText()==""){
               Dialog.show("Erreur","Veuillez verifier vos données", "Annuler", "ok");
                
            }else{
                 InfiniteProgress ip=new  InfiniteProgress();
                final Dialog d=ip.showInfiniteBlocking();
                 IMC imc=new IMC();
                 imc.setId_utilisateur(1);
                 imc.setPoids(Double.valueOf(objet1.getText()));
                 imc.setTaille(Double.valueOf(objet2.getText()));
                
                ServiceIMC.getInstance().addIMC(imc);
               // Message m = new Message("Poids : "+imc.getPoids()+" Taille : "+imc.getTaille());
//Display.getInstance().sendMessage(new String[] {"mohamedamine.bellili@esprit.tn"}, "StressFree", m);
                d.dispose();
                
                new ListIMCForm(res).show();
                refreshTheme();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }); 
    }
        
  private void addStringValue(String s,Component v){
   add(BorderLayout.west(new Label(s,"PaddedLabel"))
           .add(BorderLayout.CENTER,v));
add(createLineSeparator(0xeeeeee));   
  }  

    private void addTab(Tabs swipe, Label spacer, Image image,   String text, Resources res) {
        int size=Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
        if(image.getHeight()<size){
            image=image.scaledHeight(size);
        }
        if(image.getHeight()> Display.getInstance().getDisplayHeight()/2){
            image=image.scaledHeight(Display.getInstance().getDisplayHeight()/2);
        }
        
        ScaleImageLabel imagesc=new ScaleImageLabel(image);
        imagesc.setUIID("Container");
        imagesc.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overl=new Label("","ImageOverlay");
        Container page1=
                LayeredLayout.encloseIn(
                imagesc,
                        overl,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                new SpanLabel(text,"LargeWhiteText"),
                                      FlowLayout.encloseIn( ),
                                        spacer
                                        )));
        swipe.addTab("",res.getImage("logo.png"),page1);
        
    }
    
    public void bindButtonSelection(Button btn,Label l){
        btn.addActionListener(e->{if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
}
  
    private void updateArrowPosition(Button btn, Label l) {
       l.getUnselectedStyle().setMargin(LEFT, btn.getX()+btn.getWidth()/2 -l.getWidth()/2);
       l.getParent().repaint();
    }
    
   
     }
