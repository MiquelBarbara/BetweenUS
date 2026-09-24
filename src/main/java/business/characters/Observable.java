package business.characters;

import business.exceptions.BusinessException;

public interface Observable {

    /**
     * Function that adds a new observer to the observable object
     * @param observer observer that is going to observ the observable object
     */
    void addObserver(Observer observer);

    /**
     * Function that notifies the observers that a new movement is going to be done
     */
    void notifyNewMovement();

    /**
     * Function that notifies the observers that a new action is going to be done
     */
    void notifyNewAction() throws BusinessException;
}
