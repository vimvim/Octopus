package ui.services;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import repositories.SocialUsersRepo;
import services.SocialUserService;
import services.TweetService;

import java.io.*;

/**
 * Handle tweets uploaded in the JSOn from from topsy service.
 */
@Component
@Scope("session")
public class TopsyTweetsHandler implements Upload.Receiver, Upload.SucceededListener {

    TweetService tweetService;

    SocialUserService socialUserService;

    SocialUsersRepo socialUsersRepo;

    File file;

    @Autowired
    public TopsyTweetsHandler(
            @Qualifier("tweetService") TweetService tweetService,
            @Qualifier("socialUserService") SocialUserService socialUserService,
            SocialUsersRepo socialUsersRepo,
    ) {
        this.tweetService = tweetService;
        this.socialUserService = socialUserService;
    }

    public OutputStream receiveUpload(String filename, String mimeType) {

        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File("/tmp/uploads/" + filename);
            fos = new FileOutputStream(file);

        } catch (final java.io.FileNotFoundException e) {

            new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

    public void uploadSucceeded(Upload.SucceededEvent event) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            JsonNode rootNode;
            while ((rootNode = mapper.readTree(fileReader))!=null) {

                String id = rootNode.path("id_str").getTextValue();

                String text = rootNode.path("text").getTextValue();

                JsonNode entities = rootNode.path("entities");

                JsonNode user = rootNode.path("user");
                JsonNode topsy = rootNode.path("topsy");

                String userID = user.path("id").getTextValue();
                String screenName = user.path("screen_name").getTextValue();
                String userName = user.path("name").getTextValue();

                socialUsersRepo.findByAttribute("twitter", );

                socialUserService.repo()
            }

            /*** read value from key "name" ***/
            // JsonNode nameNode = rootNode.path("name");
            // System.out.println(nameNode.getTextValue());

        } catch (Exception ex) {

        }
    }
}
