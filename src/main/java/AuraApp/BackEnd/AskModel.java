package AuraApp.BackEnd;

import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;

import java.util.List;

public interface AskModel {
    List<String> viewModel() throws LikedItemNotFoundException;

}
