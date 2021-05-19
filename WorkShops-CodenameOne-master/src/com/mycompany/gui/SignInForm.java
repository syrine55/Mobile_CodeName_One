/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceLogin;
import java.io.IOException;

/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class SignInForm extends BaseForm {
    Resources bader;
    public SignInForm(Resources res) {
        
        super(new BorderLayout());
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        bader=res;
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));
        
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Login");
        
        Button face = new Button("Facebook logoin");
            //face.addActionListener(e -> {   
          //  FBLogin6 fb = new FBLogin6();
           //fb.start();});
        
        Button signUp = new Button("S'inscrire");
        signUp.addActionListener(e -> new SignUpForm(res).show());
        
        signUp.setUIID("Link");
        Button doneHaveAnAccount = new Button("Mot de passe Oublié?");
        signUp.setUIID("Link");
        doneHaveAnAccount.addActionListener(e -> new ActivateForm(res).show());
        Container content = BoxLayout.encloseY(
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                face,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener(e -> ServiceLogin.getInstance().login(username, password, res));
        face.addActionListener(e -> facebookLogin(this,bader));
        //new ConsulterEspaceMeditation(res).show()
    }
    
    
    private void showIfNotLoggedIn(SignInForm form,Resources res) {
     
        try {
            form.getContentPane().removeAll();
            Storage.getInstance().writeObject("token", "");
            
            ScaleImageLabel myPic = new ScaleImageLabel();
           // Image img = Image.createImage("/anonimo.jpg");
         //   myPic.setIcon(img);
            Dimension d = new Dimension(50, 50);
            myPic.setPreferredSize(d);
            
            form.add(myPic);
            
            form.add(new Label("User not connected"));
            
            Button buttonLogin = new Button("Login");
            buttonLogin.addActionListener((e) -> {
                facebookLogin(form,res);
            });
            form.add(buttonLogin);
            
            form.revalidate();
            form.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showIfLoggedIn(SignInForm form,Resources res) {
        // theme = UIManager.initNamedTheme("/theme", "Theme");
        String token = (String) Storage.getInstance().readObject("token");
        FaceBookAccess.setToken(token);
            final User me = new User();
            try {
                FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String miNombre = me.getName();
                        
                        form.getContentPane().removeAll();
                        
                        form.add(new Label(miNombre));
                        
                        Button buttonLogout = new Button("Logout");
                        buttonLogout.addActionListener((e) -> {
                         //   facebookLogout(form);
                            showIfNotLoggedIn(form,res);
                        });
                         Button app = new Button("go for app");
                        app.addActionListener((e) -> {
                       //     new  WalkthruForm(theme).show();
                        });

                        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                        URLImage background = URLImage.createToStorage(placeholder, "fbuser.jpg",
                                "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                        background.fetch();
                        ScaleImageLabel myPic = new ScaleImageLabel();
                        myPic.setIcon(background);
                        
                       form.add(myPic);
                        form.add(buttonLogout);
                        form.add(app);
                        
                        form.revalidate();
                         new ConsulterEspaceMeditation(res).show();
                    }

                    
                });
            } catch (IOException ex) {
                ex.printStackTrace();
                showIfNotLoggedIn(form,res);
            }
    }
    private void facebookLogin(SignInForm form,Resources bader) {
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
        String clientId = "465632321179711";
        String redirectURI = "http://localhost/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "6e18c5bc6e0cfb2c35a8319d91d30249";
        Login fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                System.out.println("Failed el login");
                Storage.getInstance().writeObject("token", "");
                showIfNotLoggedIn(form,bader);
                //new ConsulterEspaceMeditation(bader).show();
            }

            @Override
            public void loginSuccessful() {
                System.out.println("successful");
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
               showIfLoggedIn(form,bader);
                 //new ConsulterEspaceMeditation(bader).show();
            }
            
        });
        //trigger the login if not already logged in
        if(!fb.isUserLoggedIn()){
            
            fb.doLogin();
           // new ConsulterEspaceMeditation(bader).show();
        }else{
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            Storage.getInstance().writeObject("token", token);
            showIfLoggedIn(form,bader);
                     //new ConsulterEspaceMeditation(bader).show();
        }
    }
}
