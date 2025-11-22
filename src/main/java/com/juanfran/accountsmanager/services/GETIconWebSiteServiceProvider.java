package com.juanfran.accountsmanager.services;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class GETIconWebSiteServiceProvider {

    private GETIconWebSiteServiceProvider(){

    }

    /**
     * Este método se encarga de
     * obtener la Url de la api
     * @param urlWebSite
     * @param size
     * @return
     */
    private static String getUrl(String urlWebSite,Integer size){
        return "https://www.google.com/s2/favicons?domain="+urlWebSite+"&sz="+size;
    }

    /**
     * Este método se encarga
     * obtener icon de una página
     * web en png
     * @param urlWebSite
     * @param size
     * @return
     */
    public static Image getPNGIcon(String urlWebSite, Integer size){
        InputStream inputStream = null;
        BufferedImage svgImage = null;
        Image imageIcon = null;

        try
        {
            String faviconUrl = getUrl(urlWebSite,size);
            URL url = new URL(faviconUrl);
            inputStream = url.openStream();

            try{
                svgImage = ImageIO.read(inputStream);
                imageIcon = SwingFXUtils.toFXImage(svgImage,null);
            }catch(Exception e){
                OrchestratorProyectDependences.getLogger().error(e.getMessage());
            }
            finally {
                if(svgImage != null){
                    svgImage.flush();
                    svgImage = null;
                }
            }

        } catch (IOException e) {
            OrchestratorProyectDependences.getLogger().error(e.getMessage());
        }
        finally{
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    OrchestratorProyectDependences.getLogger().error(e.getMessage());
                }
            }
        }

        return imageIcon;
    }
}
