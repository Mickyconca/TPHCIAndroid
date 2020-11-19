package ar.edu.itba.quickfitness;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.model.Exercise;

public class RoutineViewModel extends ViewModel {
    private ArrayList<Exercise> exerciseList;
    private int currentPos = 0;
    private int exerciseAmount;

    public int getCurrentPos() {
        return currentPos;
    }

    public RoutineViewModel(ArrayList<Exercise> arrayList) {
        exerciseList = arrayList;
        exerciseAmount = arrayList.size();
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void increseCurrentPos(){
        currentPos++;
    }

    public int getExerciseAmount() {
        return exerciseAmount;
    }
}
