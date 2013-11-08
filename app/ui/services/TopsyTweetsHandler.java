package ui.services;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import services.TweetService;

import java.io.*;

/**
 * Handle tweets uploaded in the JSOn from from topsy service.
 */
@Component
@Scope("session")
public class TopsyTweetsHandler implements Upload.Receiver, Upload.SucceededListener {

    TweetService tweetService;

    File file;

    public TopsyTweetsHandler(TweetService tweetService) {
        this.tweetService = tweetService;
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
            JsonNode rootNode = mapper.readTree(fileReader);

            /*** read value from key "name" ***/
            JsonNode nameNode = rootNode.path("name");
            System.out.println(nameNode.getTextValue());

        } catch (Exception ex) {

        }
    }
}
