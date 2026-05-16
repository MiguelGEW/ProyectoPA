package AuraApp.FrontEnd;

import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;

public interface InformView {

    void modelUpdated() throws LikedItemNotFoundException;
}
