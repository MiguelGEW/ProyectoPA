package AuraApp.MiddleEnd;


import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;

import AuraApp.BackEnd.ModelChange;

import AuraApp.FrontEnd.AskView;

public class ImplementedController implements Controller{

    private ModelChange model;
    private AskView view;


    public void setModel(ModelChange model) {
        this.model = model;
    }

    public void setView(AskView view) {
        this.view = view;
    }

    @Override
    public void updateModel() throws LikedItemNotFoundException {

        String songName = view.getChosenSong();
        Algorithm algorithm = view.getSelectedAlgorithm();
        int numRec = view.getRecommendSize();
        model.updateModel(songName,  algorithm, numRec);
    }
}
