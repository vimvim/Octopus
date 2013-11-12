package ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import spring.SpringContextHolder;
import ui.services.TopsyTweetsHandler;


/**
 *
 */
public class TransactionsView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        setSizeFull();
        addStyleName("transactions");

        TopsyTweetsHandler tweetsHandler = SpringContextHolder.getContext().getBean(TopsyTweetsHandler.class);

        Upload upload = new Upload("Upload Image Here", tweetsHandler);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(tweetsHandler);

        // Put the components in a panel
        Panel panel = new Panel("Panel");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload);
        panel.setContent(panelContent);

        addComponent(panel);
    }
}
