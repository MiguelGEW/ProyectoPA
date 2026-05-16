package AuraApp.MiddleEnd;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;


public interface Controller {

    void updateModel() throws LikedItemNotFoundException;
}
