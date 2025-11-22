package com.juanfran.accountsmanager.managers;


import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IGmailManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class GmailManager implements IGmailManager {
    private final String gmail;
    private final String password;
    private final Logger logger;

    @Autowired
    public GmailManager(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
        this.logger = OrchestratorProyectDependences.getLogger();

    }

    /**
     * Este método se encarga de enviar un
     * email de bienvenida al usuario registrado
     * en la aplicación
     * @param to
     */
    public void sendWelcomeEmail(String to){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(gmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(gmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Welcome to Accounts");
            message.setContent("<!doctype html><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><title></title><!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><style type=\"text/css\">#outlook a { padding:0; }\n" +
                    "      body { margin:0;padding:0;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%; }\n" +
                    "      table, td { border-collapse:collapse;mso-table-lspace:0pt;mso-table-rspace:0pt; }\n" +
                    "      img { border:0;height:auto;line-height:100%; outline:none;text-decoration:none;-ms-interpolation-mode:bicubic; }\n" +
                    "      p { display:block;margin:13px 0; }</style><!--[if mso]>\n" +
                    "    <noscript>\n" +
                    "    <xml>\n" +
                    "    <o:OfficeDocumentSettings>\n" +
                    "      <o:AllowPNG/>\n" +
                    "      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                    "    </o:OfficeDocumentSettings>\n" +
                    "    </xml>\n" +
                    "    </noscript>\n" +
                    "    <![endif]--><!--[if lte mso 11]>\n" +
                    "    <style type=\"text/css\">\n" +
                    "      .mj-outlook-group-fix { width:100% !important; }\n" +
                    "    </style>\n" +
                    "    <![endif]--><!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\"><style type=\"text/css\">@import url(https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700);</style><!--<![endif]--><style type=\"text/css\">@media only screen and (min-width:480px) {\n" +
                    "        .mj-column-per-100 { width:100% !important; max-width: 100%; }\n" +
                    ".mj-column-per-50 { width:50% !important; max-width: 50%; }\n" +
                    "      }</style><style media=\"screen and (min-width:480px)\">.moz-text-html .mj-column-per-100 { width:100% !important; max-width: 100%; }\n" +
                    ".moz-text-html .mj-column-per-50 { width:50% !important; max-width: 50%; }</style><style type=\"text/css\">[owa] .mj-column-per-100 { width:100% !important; max-width: 100%; }\n" +
                    "[owa] .mj-column-per-50 { width:50% !important; max-width: 50%; }</style><style type=\"text/css\">@media only screen and (max-width:479px) {\n" +
                    "      table.mj-full-width-mobile { width: 100% !important; }\n" +
                    "      td.mj-full-width-mobile { width: auto !important; }\n" +
                    "    }</style><style type=\"text/css\"></style></head><body style=\"word-spacing:normal;background-color:#F4F4F4;\"><div style=\"background-color:#F4F4F4;\"><!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"transparent\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><v:rect style=\"width:600px;\" xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\"><v:fill origin=\"0.5, 0\" position=\"0.5, 0\" src=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6km5y.png\" color=\"transparent\" type=\"tile\" /><v:textbox style=\"mso-fit-shape-to-text:true\" inset=\"0,0,0,0\"><![endif]--><div style=\"background:transparent url('https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6km5y.png') center top / auto repeat;background-position:center top;background-repeat:repeat;background-size:auto;margin:0px auto;max-width:600px;\"><div style=\"line-height:0;font-size:0;\"><table align=\"center\" background=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6km5y.png\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:transparent url('https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6km5y.png') center top / auto repeat;background-position:center top;background-repeat:repeat;background-size:auto;width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:50px 0px 50px 0px;padding-bottom:50px;padding-left:0px;padding-right:0px;padding-top:50px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td style=\"vertical-align:top;padding:0 0 0 0;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\"><tbody><tr><td style=\"width:133px;\"><img alt=\"\" src=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6km28.png\" style=\"border:none;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px;\" width=\"133\" height=\"auto\"></td></tr></tbody></table></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;padding-top:10px;padding-right:25px;padding-bottom:10px;padding-left:25px;word-break:break-word;\"><div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:29px;line-height:1;text-align:left;color:#000000;\"><p class=\"text-build-content\" style=\"text-align: center; margin: 10px 0; margin-top: 10px; margin-bottom: 10px;\" data-testid=\"6bTUu16ZoedqRJroCXWLP\"><span style=\"color:#ffffff;font-family:Arial, sans-serif;font-size:29px;line-height:22px;\"><b>Accounts</b></span></p></div></td></tr></tbody></table></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div></div><!--[if mso | IE]></v:textbox></v:rect></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"#68a0d5\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#68a0d5;background-color:#68a0d5;margin:0px auto;max-width:600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#68a0d5;background-color:#68a0d5;width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:0px 0px 0px 0px;padding-bottom:0px;padding-left:0px;padding-right:0px;padding-top:0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:300px;\" ><![endif]--><div class=\"mj-column-per-50 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td style=\"vertical-align:top;padding:0 0 0 0;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td align=\"center\" style=\"background:#ffffff;font-size:0px;padding:0px 0px 0px 0px;padding-top:0px;padding-right:0px;padding-bottom:0px;padding-left:0px;word-break:break-word;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\"><tbody><tr><td style=\"width:300px;\"><img alt=\"Incurved TV\" src=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwtt6/6kmgs.png\" style=\"border:none;border-radius:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px;\" width=\"300\" height=\"auto\"></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></div><!--[if mso | IE]></td><td class=\"\" style=\"vertical-align:top;width:300px;\" ><![endif]--><div class=\"mj-column-per-50 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td style=\"vertical-align:top;padding:0 0 0 0;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;padding-top:0px;padding-right:25px;padding-bottom:0px;padding-left:25px;word-break:break-word;\"><div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:34px;line-height:1;text-align:left;color:#000000;\"><p class=\"text-build-content\" data-testid=\"sQeE8k_inK7NwxXiOm-ts\" style=\"margin: 10px 0; margin-top: 10px;\"><span style=\"color:#ffffff;font-size:34px;\"><b>Bienvenido</b></span></p><p class=\"text-build-content\" data-testid=\"sQeE8k_inK7NwxXiOm-ts\" style=\"margin: 10px 0; margin-bottom: 10px;\"><span style=\"color:rgb(255,255,255);font-family:Arial;font-size:18px;line-height:22px;\">Gracias por unirte a Accounts, el servicio donde tus cuentas personales se encuentran a salvo.</span></p></div></td></tr></tbody></table></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"margin:0px auto;max-width:600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0px 20px 0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td style=\"vertical-align:top;padding:0;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\"><tbody><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:center;color:#000000;\"><p style=\"margin: 10px 0;\">Este email fue enviado a [[EMAIL_TO]], <a href=\"[[UNSUB_LINK_ES]]\" style=\"color:inherit;text-decoration:none;\" target=\"_blank\">pulse aquí para cancelar la suscripción</a>.</p></div></td></tr><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:center;color:#000000;\"><p style=\"margin: 10px 0;\">   ES</p></div></td></tr></tbody></table></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></div></body></html>","text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de enviar un
     * email de con el código de recuperación
     * de su contraseña
     * @param to
     * @param code
     */
    public void sendRecoverPasswordCodeEmail(String to, Integer code){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(gmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(gmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Recover Password Code");
            message.setContent("<!doctype html><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><title></title><!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><style type=\"text/css\">#outlook a { padding:0; }\n" +
                    "      body { margin:0;padding:0;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%; }\n" +
                    "      table, td { border-collapse:collapse;mso-table-lspace:0pt;mso-table-rspace:0pt; }\n" +
                    "      img { border:0;height:auto;line-height:100%; outline:none;text-decoration:none;-ms-interpolation-mode:bicubic; }\n" +
                    "      p { display:block;margin:13px 0; }</style><!--[if mso]>\n" +
                    "    <noscript>\n" +
                    "    <xml>\n" +
                    "    <o:OfficeDocumentSettings>\n" +
                    "      <o:AllowPNG/>\n" +
                    "      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                    "    </o:OfficeDocumentSettings>\n" +
                    "    </xml>\n" +
                    "    </noscript>\n" +
                    "    <![endif]--><!--[if lte mso 11]>\n" +
                    "    <style type=\"text/css\">\n" +
                    "      .mj-outlook-group-fix { width:100% !important; }\n" +
                    "    </style>\n" +
                    "    <![endif]--><!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\"><style type=\"text/css\">@import url(https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700);</style><!--<![endif]--><style type=\"text/css\">@media only screen and (min-width:480px) {\n" +
                    "        .mj-column-per-100 { width:100% !important; max-width: 100%; }\n" +
                    "      }</style><style media=\"screen and (min-width:480px)\">.moz-text-html .mj-column-per-100 { width:100% !important; max-width: 100%; }</style><style type=\"text/css\">[owa] .mj-column-per-100 { width:100% !important; max-width: 100%; }</style><style type=\"text/css\"></style><style type=\"text/css\"></style></head>\n" +
                    "      <body style=\"word-spacing:normal;background-color:#F4F4F4;\">\n" +
                    "        <div style=\"background-color:#F4F4F4;\"><!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><v:rect style=\"width:600px;\" xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\"><v:fill origin=\"0.5, 0\" position=\"0.5, 0\" src=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwt92/6kmq7.png\" type=\"tile\" /><v:textbox style=\"mso-fit-shape-to-text:true\" inset=\"0,0,0,0\"><![endif]-->\n" +
                    "          <div style=\"background:url('https://08ggy.mjt.lu/tplimg/08ggy/b/mwt92/6kmq7.png') center top / auto repeat;background-position:center top;background-repeat:repeat;background-size:auto;margin:0px auto;max-width:600px;\"><div style=\"line-height:0;font-size:0;\">\n" +
                    "            <table align=\"center\" background=\"https://08ggy.mjt.lu/tplimg/08ggy/b/mwt92/6kmq7.png\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:url('https://08ggy.mjt.lu/tplimg/08ggy/b/mwt92/6kmq7.png') center top / auto repeat;background-position:center top;background-repeat:repeat;background-size:auto;width:100%;\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td style=\"direction:ltr;font-size:0px;padding:0px 0px 50px 0px;padding-bottom:50px;padding-left:0px;padding-right:0px;padding-top:0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]-->\n" +
                    "                    <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                    "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                    "                        <tbody>\n" +
                    "                          <tr>\n" +
                    "                            <td style=\"vertical-align:top;padding:0 0 0 0;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                    "                              <tbody>\n" +
                    "                                <tr>\n" +
                    "                                  <td align=\"left\" style=\"font-size:0px;padding:10px 25px 0px 25px;padding-top:10px;padding-right:25px;padding-bottom:0px;padding-left:25px;word-break:break-word;\">\n" +
                    "                                    <div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:25px;line-height:1;text-align:left;color:#000000;\">\n" +
                    "                                      <p class=\"text-build-content\" style=\"text-align: center; margin: 10px 0; margin-top: 10px; margin-bottom: 10px;\" data-testid=\"ZevsbQ6s1GcHi_AEy175v\">\n" +
                    "                                        <span style=\"color:#ffffff;font-size:25px;\">\n" +
                    "                                          <b>Recover Password Code</b>\n" +
                    "                                        </span>\n" +
                    "                                      </p>\n" +
                    "                                    </div>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                  <td align=\"left\" style=\"font-size:0px;padding:40px 25px 0px 25px;padding-top:40px;padding-right:25px;padding-bottom:0px;padding-left:25px;word-break:break-word;\">\n" +
                    "                                    <div style=\"font-family:Arial, sans-serif;font-size:30px;letter-spacing:normal;line-height:1;text-align:left;color:#000000;\">\n" +
                    "                                      <p class=\"text-build-content\" style=\"text-align: center; margin: 10px 0; margin-top: 10px; margin-bottom: 10px;\" data-testid=\"KL-8a19bCQTkIGCt6DJvo\">\n" +
                    "                                        <span style=\"color:#f8f8f8;font-family:Arial;font-size:30px;\">\n" +
                    "                                          <b>"+code+"</b>\n" +
                    "                                        </span>\n" +
                    "                                      </p>\n" +
                    "                                    </div>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </tbody>\n" +
                    "                            </table>\n" +
                    "                          </td>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </div><!--[if mso | IE]></td></tr></table><![endif]-->\n" +
                    "                </td>\n" +
                    "              </tr>\n" +
                    "            </tbody>\n" +
                    "          </table>\n" +
                    "        </div>\n" +
                    "      </div><!--[if mso | IE]></v:textbox></v:rect></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->\n" +
                    "        <div style=\"margin:0px auto;max-width:600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                    "          <tbody>\n" +
                    "            <tr>\n" +
                    "              <td style=\"direction:ltr;font-size:0px;padding:20px 0px 20px 0px;text-align:center;\">\n" +
                    "                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]-->\n" +
                    "                  <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                    "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                    "                      <tbody>\n" +
                    "                        <tr>\n" +
                    "                          <td style=\"vertical-align:top;padding:0;\">\n" +
                    "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                    "                              <tbody>\n" +
                    "                                <tr>\n" +
                    "                                  <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                    "                                    <div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:center;color:#000000;\">\n" +
                    "                                      <p style=\"margin: 10px 0;\">Este email fue enviado a [[EMAIL_TO]], <a href=\"[[UNSUB_LINK_ES]]\" style=\"color:inherit;text-decoration:none;\" target=\"_blank\">pulse aquí para cancelar la suscripción</a>.</p>\n" +
                    "                                    </div>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                  <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                    "                                    <div style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:center;color:#000000;\">\n" +
                    "                                      <p style=\"margin: 10px 0;\">   ES</p>\n" +
                    "                                    </div>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </tbody>\n" +
                    "                            </table>\n" +
                    "                          </td>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </div><!--[if mso | IE]></td></tr></table><![endif]-->\n" +
                    "                </td>\n" +
                    "              </tr>\n" +
                    "            </tbody>\n" +
                    "          </table>\n" +
                    "        </div><!--[if mso | IE]></td></tr></table><![endif]-->\n" +
                    "      </div>\n" +
                    "    </body>\n" +
                    "  </html>","text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            this.logger.error(e.getMessage());
        }
    }
}