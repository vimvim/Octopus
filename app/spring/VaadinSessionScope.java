package spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.vaadin.ui.UI;
import ui.BackendUI;

import java.util.Map;

/**
 *
 */
public class VaadinSessionScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        Map<String,Object> componentsStorage = getComponentsStorage();

        Object object = componentsStorage.get(name);
        if (object==null) {
            object = objectFactory.getObject();
            componentsStorage.put(name, object);
        }

        return object;
    }

    public Object remove(String name) {

        Map<String,Object> componentsStorage = getComponentsStorage();

        return componentsStorage.remove(name);
    }

    public void registerDestructionCallback(String name, Runnable callback) {
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getConversationId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Map<String,Object> getComponentsStorage() {
        BackendUI ui = (BackendUI) UI.getCurrent();
        return ui.getComponentsStorage();
    }

}