package ui.views;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import models.Tweet;
import spring.SpringContextHolder;
import ui.services.TopsyTweetsHandler;

import javax.persistence.EntityManager;


/**
 *
 */
public class TransactionsView extends VerticalLayout implements View {

    private JPAContainer<Tweet> tweets;

    public TransactionsView() {
        tweets = JPAContainerFactory.make(Tweet.class, getEntityManager());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        setSizeFull();
        addStyleName("transactions");

        TopsyTweetsHandler tweetsHandler = SpringContextHolder.getContext().getBean(TopsyTweetsHandler.class);

        Upload upload = new Upload("Upload Image Here", tweetsHandler);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(tweetsHandler);

        /*
        Table table = new Table();
        table.setSizeFull();
        table.addStyleName("borderless");
        table.setSelectable(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        table.setContainerDataSource(data);
        table.setVisibleColumns(new Object[] { "TweetID", "Username", "Text"});
        */


        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload);


        Panel panel = new Panel("Panel");
        panel.setContent(panelContent);

        addComponent(panel);



    }

    private EntityManager getEntityManager() {
        return SpringContextHolder.getContext().getBean(EntityManager.class);
    }
}
