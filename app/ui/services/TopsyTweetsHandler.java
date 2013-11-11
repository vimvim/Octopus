package ui.services;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import models.SocialUser;
import models.Tweet;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import repositories.SocialUsersRepo;
import repositories.TweetsRepo;
import scala.Function1;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;
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

    TweetsRepo tweetsRepo;

    SocialUserService socialUserService;

    SocialUsersRepo socialUsersRepo;

    File file;

    @Autowired
    public TopsyTweetsHandler(
            @Qualifier("tweetService") TweetService tweetService,
            @Qualifier("tweetsRepo") TweetsRepo tweetsRepo,
            @Qualifier("socialUserService") SocialUserService socialUserService,
            @Qualifier("socialUsersRepo") SocialUsersRepo socialUsersRepo
    ) {
        this.tweetService = tweetService;
        this.tweetsRepo = tweetsRepo;
        this.socialUserService = socialUserService;
        this.socialUsersRepo = socialUsersRepo;
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

                final String tweetId = rootNode.path("id_str").getTextValue();
                final String tweetText = rootNode.path("text").getTextValue();

                JsonNode entities = rootNode.path("entities");

                JsonNode user = rootNode.path("user");
                JsonNode topsy = rootNode.path("topsy");

                final String userID = user.path("id").getTextValue();
                final String screenName = user.path("screen_name").getTextValue();
                final String userName = user.path("name").getTextValue();

                SocialUser _socialUser = socialUsersRepo.findBySchemaAttr("twitter", "id", userID);
                // SocialUser socialUser = socialUsersRepo.findByAttribute("twitter", "screen_name");
                if (_socialUser==null) {

                    _socialUser = socialUserService.create(new AbstractFunction1<SocialUser,BoxedUnit>(){

                         @Override
                         public BoxedUnit apply(SocialUser socialUser) {

                             socialUser.setAttribute("twitter", "id", userID);

                            return null;
                         }
                    });
                }

                final SocialUser socialUser = _socialUser;

                Tweet tweet = tweetsRepo.findByTweetId(tweetId);
                if (tweet==null) {

                    tweet = tweetService.create(new AbstractFunction1<Tweet, BoxedUnit>() {

                        @Override
                        public BoxedUnit apply(Tweet tweet) {

                            tweet.setTweetId(tweetId);
                            tweet.setText(tweetText);
                            tweet.setSocialUser(socialUser);

                            return null;
                        }
                    });
                }

            }

            /*** read value from key "name" ***/
            // JsonNode nameNode = rootNode.path("name");
            // System.out.println(nameNode.getTextValue());

        } catch (Exception ex) {

        }
    }
}
