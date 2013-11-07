package ui.events;

/**
 *
 */
public class NavigateViewEvent {

    String route;

    public NavigateViewEvent(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}
