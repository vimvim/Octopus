package ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ui.events.NavigateViewEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

/**
 *
 */
@org.springframework.stereotype.Component
public class Menu {

    /*
    final String[] items = new String[] {
            "dashboard",
            "sales",
            "transactions",
            "reports",
            "schedule"
    };
    */

    EventBus eventBus;

    CssLayout layout = new CssLayout();

    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

    @Autowired
    public Menu(EventBus _eventBus, SortedMap<String, Class> views) {

        this.eventBus = _eventBus;

        for (final String view : views.keySet()) {

            Button b = new NativeButton(view.substring(0, 1).toUpperCase() + view.substring(1).replace('-', ' '));
            b.addStyleName("icon-" + view);
            b.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {

                    clearMenuSelection();
                    event.getButton().addStyleName("selected");

                    eventBus.post(new NavigateViewEvent(view));

                    // if (!nav.getState().equals("/" + view)) nav.navigateTo("/" + view);
                }
            });

            /** Additional menuitem customization stuff must be moved to the corresponding view.
             *
            if (view.equals("reports")) {
                // Add drop target to reports button
                DragAndDropWrapper reports = new DragAndDropWrapper(b);
                reports.setDragStartMode(DragAndDropWrapper.DragStartMode.NONE);
                reports.setDropHandler(new DropHandler() {

                    @Override
                    public void drop(DragAndDropEvent event) {

                        clearMenuSelection();

                        // viewNameToMenuButton.get("/reports").addStyleName("selected");
                        // autoCreateReport = true;
                        // items = event.getTransferable();

                        eventBus.post(new NavigateViewEvent("reports"));

                        // nav.navigateTo("/reports");
                    }

                    @Override
                    public AcceptCriterion getAcceptCriterion() {
                        return AbstractSelect.AcceptItem.ALL;
                    }

                });

                layout.addStyleName("no-vertical-drag-hints");
                layout.addStyleName("no-horizontal-drag-hints");
            } */

            layout.addComponent(b);

            viewNameToMenuButton.put("/" + view, b);
        }

        layout.addStyleName("menu");
        layout.setHeight("100%");

        // viewNameToMenuButton.get("/dashboard").setHtmlContentAllowed(true);
        // viewNameToMenuButton.get("/dashboard").setCaption("Dashboard<span class=\"badge\">2</span>");

        // Navigate to the layout item according to the uri fragment
        String f = Page.getCurrent().getUriFragment();
        if (f != null && f.startsWith("!")) {
            f = f.substring(1);
        }

        if (f == null || f.equals("") || f.equals("/")) {

            eventBus.post(new NavigateViewEvent(views.firstKey()));
            // nav.navigateTo("/dashboard");

            layout.getComponent(0).addStyleName("selected");

            // helpManager.showHelpFor(DashboardView.class);
        } else {

            eventBus.post(new NavigateViewEvent(f));
            // nav.navigateTo(f);

            // helpManager.showHelpFor(routes.get(f));
            viewNameToMenuButton.get(f).addStyleName("selected");
        }
    }

    public Component getComponent() {
        return layout;
    }

    private void clearMenuSelection() {

        for (Iterator<Component> it = layout.getComponentIterator(); it.hasNext();) {

            Component next = it.next();

            if (next instanceof NativeButton) {

                next.removeStyleName("selected");
            } else if (next instanceof DragAndDropWrapper) {

                ((DragAndDropWrapper) next).iterator().next().removeStyleName("selected");
            }
        }
    }
}
