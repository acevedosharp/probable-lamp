package sabana.mrp1.events;

import org.springframework.context.ApplicationEvent;

public class SceneChangeEvent extends ApplicationEvent {

    public SceneChangeEvent(String route) {
        super(route);
    }

    public String getRoute() {
        return ((String) getSource());
    }

}