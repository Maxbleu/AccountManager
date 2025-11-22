package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.managers.UserManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserModel {
    private Integer idUser;
    private byte[] photoUserProfile;
    private String email;
    private String name;
    private Integer idPassword;

    //  Pongo aquí la clave simétrica porque
    //  considero que el usuario es el único
    //  que puede ver su información y por lo
    //  cual debe de ser el único que pueda
    //  acceder a ella
    private SecretKey secretKey;

    public UserModel(Integer idUser, String email, String name, Integer password) {
        this.idUser = idUser;
        this.email = email;
        this.name = name;
        this.idPassword = password;
    }

    public UserModel(String email, String name, Integer password){
        this.email = email;
        this.name = name;
        this.idPassword = password;
    }

    public UserModel(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public UserModel(){}

    public Integer getIdUser() {
        if(this.idUser==null){
            UserManager userManager = (UserManager) OrchestratorProyectDependences.getService(UserManager.class);
            if(userManager.Users.size() > 0){
                Integer lastPosition = userManager.Users.size()-1;
                this.idUser = userManager.Users.get(lastPosition).idUser+1;
            }else{
                this.idUser = 1;
            }
        }
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(Integer password) {
        this.idPassword = password;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public SecretKey getSecretKey(){
        return this.secretKey;
    }

    public Image getPhotoUserProfile() {
        if(this.photoUserProfile != null){
            ByteArrayInputStream bais = null;
            BufferedImage bufferedImage = null;
            Image userProfile = null;
            try {
                bais = new ByteArrayInputStream(this.photoUserProfile);
                bufferedImage = ImageIO.read(bais);
                userProfile = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (IOException e) {
                OrchestratorProyectDependences.getLogger().error(e.getMessage());
            }
            return userProfile;
        }
        return new Image(Main.class.getResource("Images/defaultUserPhoto.png").toString());
    }
    public void setPhotoUserProfile(Image photoUserProfile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(photoUserProfile, null), "png", baos);
        } catch (IOException e) {
            OrchestratorProyectDependences.getLogger().error(e.getMessage());
        }
        this.photoUserProfile = baos.toByteArray();
    }
    public void setPhotoUserProfile(byte[] bytesPhotoUserProfile){
        this.photoUserProfile = bytesPhotoUserProfile;
    }
}
