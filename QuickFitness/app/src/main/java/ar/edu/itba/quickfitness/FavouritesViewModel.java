package ar.edu.itba.quickfitness;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.quickfitness.api.model.Routine;

public class FavouritesViewModel extends ViewModel {
    private ArrayList<Routine> routineList;

    public FavouritesViewModel(List<Routine> routineList) {
        this.routineList = new ArrayList<>(routineList);
    }

    public ArrayList<Routine> getRoutineList() {
        return routineList;
    }

    public void setRoutineList(List<Routine> routineList) {
        this.routineList = new ArrayList<>(routineList);
    }
}
