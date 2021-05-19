/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceLogin;
import com.mycompany.myapp.services.ServiceMeditation;
import entity.CommentaireMeditation;
import entity.Meditation;
import java.util.ArrayList;

/**
 *ServiceLogin.id_user
 * @author Mega Pc
 */
public class ShowEspaceMeditationForm extends BaseForm{
    private ArrayList<Integer> result;
    public ShowEspaceMeditationForm(Image img, String title,int likeCount,int DislikeCount,int checklike,int checkDislike, int commentCount,Resources res,Meditation mm ){
       super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        
        
        Label NomCat = new Label("Categorie : "+mm.getCategorie());
        
        Container content = BoxLayout.encloseY(
                createLineSeparator(),
                createLineSeparator(),
                FlowLayout.encloseCenter(NomCat)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        
        setTitle("Nom espace: "+mm.getNom());
        setLayout(BoxLayout.y());
            /*ImageViewer img2 = new ImageViewer();
            EncodedImage enc = EncodedImage.createFromImage(res.getImage("round.png"), false);
            URLImage urlimage = URLImage.createToStorage(enc, "http://127.0.0.1/pidev/public/uploads/"+mm.getImage(), "http://127.0.0.1/pidev/public/uploads/"+mm.getImage());
            img2.setImage(urlimage);*/
            EncodedImage enc = EncodedImage.createFromImage(res.getImage("news-item-1.jpg"), false);
            Image i = URLImage.createToStorage(enc, "http://127.0.0.1/pidev/public/uploads/" + mm.getImage(), "http://127.0.0.1/pidev/public/uploads/" + mm.getImage(), URLImage.RESIZE_SCALE);
            int height2 = Display.getInstance().convertToPixels(11.5f);
           int width2 = Display.getInstance().convertToPixels(14f);
           Button img2 = new Button(i.fill(width2, height2));
           img2.setUIID("Label");
           img2.getStyle().setBgColor(0xFBFBEF);
           img2.getStyle().setBackgroundGradientEndColor(0xFBFBEF);
           img2.getStyle().setFgColor(0xFBFBEF);
           //img2.getStyle().setBgTransparency();
            
            //Label nom1 = new Label("Nom de l'espace de méditation :"+mm.getNom(),"Label");
            //Label cat1 = new Label("Categorie :"+mm.getCategorie(),"Label");
            SpanLabel desc = new SpanLabel(mm.getaPropos(),"Label"); 
            
            
            Label like = new Label("Like "+likeCount+"                                                     ","Label");
            like.setTextPosition(LEFT);
            if (checklike == 0) {
               // like.getStyle().setFgColor(0xFF0000);
               FontImage.setMaterialIcon(like, FontImage.MATERIAL_THUMB_UP);
           } else {
               Style ss1 = new Style(like.getUnselectedStyle());
               ss1.setFgColor(0x2d55ff);
               FontImage heartImage2 = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, ss1);
               like.setIcon(heartImage2);
           }
            Label dislike = new Label("DisLike "+DislikeCount);
            like.setTextPosition(RIGHT);            
            if (checkDislike== 0) {
               FontImage.setMaterialIcon(dislike, FontImage.MATERIAL_THUMB_DOWN);
               //like.getStyle().setFgColor(0x5677FB);
           } else {
               Style ss2 = new Style(dislike.getUnselectedStyle());
               ss2.setFgColor(0xe0002b);
               FontImage heartImage2 = FontImage.createMaterial(FontImage.MATERIAL_THUMB_DOWN, ss2);
               dislike.setIcon(heartImage2);
           }
            
            Container cnt2 = new Container(BoxLayout.x());
            cnt2.addAll(like,dislike);
            
            like.addPointerPressedListener(e->{
                result=new ArrayList<>();
                result=ServiceMeditation.getInstance().getEffectuerLikeParUnSeulEspace(ServiceLogin.id_user,mm.getId());
                //likeCount,DislikeCount,checklike,checkDislike
                //result.get(3),result.get(4),result.get(1),result.get(2)
                new ShowEspaceMeditationForm(img,title,ServiceMeditation.getInstance().getNbLikeParUnSeulEspace(mm.getId()),ServiceMeditation.getInstance().getNbDisLikeParUnSeulEspace(mm.getId()),result.get(2),result.get(3),commentCount, res, mm).show();
            });
            dislike.addPointerPressedListener(e->{
                result=new ArrayList<>();
                result=ServiceMeditation.getInstance().getEffectuerUnLikeParUnSeulEspace(ServiceLogin.id_user,mm.getId());
                System.out.println("==>"+result+"||");
                //likeCount,DislikeCount=>mta3 user,checklike,checkDislike=>mta3 total user
                //result.get(3),result.get(4),result.get(1),result.get(2)
                new ShowEspaceMeditationForm(img,title,ServiceMeditation.getInstance().getNbLikeParUnSeulEspace(mm.getId()),ServiceMeditation.getInstance().getNbDisLikeParUnSeulEspace(mm.getId()),result.get(2),result.get(3),commentCount, res, mm).show();
            });
            
            img2.getStyle().setTextDecoration(0x585858);
            getStyle().setBgColor(0x585858);
            Container cnt1 = new Container(new FlowLayout(CENTER,CENTER));
            cnt1.add(img2);
            
            TextField tfcom = new TextField("", "Saisir votre commentaire ici ....");
            //tfcom.setUIID("TextFieldBlack");
            
            int heightcmt = Display.getInstance().convertToPixels(4f);
            int widthcmt = Display.getInstance().convertToPixels(4f);
            Image imgcmt =res.getImage("cmt.png");
            Button btnValider = new Button(imgcmt.fill(widthcmt, heightcmt));
            btnValider.setUIID("Label");
            //btnValider.getStyle().setTextDecoration(0x0087FF);
            Container cnt3 = new Container(BoxLayout.x());
            cnt3.addAll(tfcom,btnValider);
            
            btnValider.addActionListener(e->{
                ServiceMeditation.getInstance().addcommentaire(ServiceLogin.id_user, mm.getId(), tfcom.getText());
                new ShowEspaceMeditationForm(img,title,likeCount, DislikeCount,checklike,checkDislike,commentCount,res,mm ).show();
            });
            
            addAll(cnt1,cnt2,new Label("Description :","Label"),desc,cnt3);
            getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->new ConsulterEspaceMeditation(res).show());
            
            add(new Label("List des commentaire :","Label"));
            for(CommentaireMeditation cm:ServiceMeditation.getInstance().getAllcommentaires(mm.getId())){
                add(new Label(cm.toString(),"SkipButton"));
            }
            
            show();
            
    }
}
