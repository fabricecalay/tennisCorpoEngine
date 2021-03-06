package be.company.fca.utils;

import com.sendgrid.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public class MailUtils {


    //TODO : alternative possible : MailJet

    /**
     * Permet d'envoyer le nouveau mot de passe a un utilisateur
     * @param mailTo
     * @param password
     * @return true si le mail est bien parti
     */
    public static boolean sendPasswordMail(String prenom, String nom, String mailTo, String password){

        // ApiKey a preciser dans l'environnement de production
        // En test, le mot de passe s'inscrira dans la console

        String apiKey = System.getenv("SENDGRID_API_KEY");
        String frontEndUrl = System.getenv("FRONT_END_URL");

        if (!StringUtils.isEmpty(apiKey)){
            Email from = new Email("noreply@tenniscorponamur.be");
            String subject = "Votre mot de passe pour Tennis Corpo Namur";
            Email to = new Email(mailTo);

            String link = "<strong>Tennis Corpo Namur</strong>";
            if (!StringUtils.isEmpty(frontEndUrl)){
                link = "<a href=\"" + frontEndUrl + "\">" + link + "</a>";
            }

            String htmlContent = "<p>Bonjour " + prenom + " " + nom + ",</p>\n" +
                    "<p>Vous trouverez ci-dessous votre nouveau mot de passe pour acc&eacute;der &agrave; l'application " + link + ".</p>\n" +
                    "<p>Nouveau mot de passe : <strong>" + password + "</strong></p>\n" +
                    "<p>Vous pouvez &agrave; tout moment modifier ce mot de passe une fois connect&eacute; &agrave; l'application.</p>\n" +
                    "<p>Salutations,</p>\n" +
                    "<p>L'&eacute;quipe Tennis Corpo Namur</p>";

            Content content = new Content("text/html", htmlContent);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                return true;

            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                return false;
            }

        }else{

            System.err.println("Nouveau mot de passe : " + password);
            return true;

        }

    }

}
