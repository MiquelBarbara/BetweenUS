package presentation.views.panels.menu;

import business.exceptions.BusinessException;

import javax.swing.event.ChangeListener;
import java.util.EventListener;

/**
 * Interface used to manage listeners on project's panels
 */
public interface BetweenUsPanel {
    /**
     * Function used to add an Event listener
     * @param listener listener to be added
     */
    void attachListener(EventListener listener);
    /**
     * Function used to detach an Event listener
     * @param listener listener to be detached
     */
    void detachListener(EventListener listener);
}
