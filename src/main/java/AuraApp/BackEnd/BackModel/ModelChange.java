package AuraApp.BackEnd.BackModel;


import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import java.util.List;

public interface ModelChange {
    void updateModel(String songName, Algorithm<TableWithLabels,List<Double>,Integer> algorithm, int numRec) throws LikedItemNotFoundException;
}
