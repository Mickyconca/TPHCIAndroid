package ar.edu.itba.quickfitness;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;
import ar.edu.itba.quickfitness.repository.ExerciseRepository;

public class ExerciseViewModel extends ViewModel {

    private int currentPosition = 0;
    private boolean isLastExercise = false;
    private List<ExerciseDomain> exerciseList;
    private ExerciseRepository repository;
    private LifecycleOwner lifecycleOwner;


    public ExerciseViewModel(ArrayList<ExerciseDomain> arrayList) {
        exerciseList = new ArrayList<>(arrayList);
    }

    public List<ExerciseDomain> getExercises(int routineId) {
        return exerciseList;
    }

    public void increseCurrentExercise(){
        if(!isLastExercise)
            currentPosition++;
        if(currentPosition== exerciseList.size())
            isLastExercise = true;
    }

    public boolean isLastExercise(){
        return isLastExercise;
    }

    public boolean isFirstExercise(){
        return currentPosition == 0;
    }

    public ExerciseDomain getExercise(int pos){
        return exerciseList.get(pos);
    }

    public void decreseCurrentExercise(){
        if(currentPosition<=0)
            return;
        currentPosition--;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getExerciseAmount(){
        return exerciseList.size();
    }

    public ExerciseDomain getCurrentExercise(){
        return exerciseList.get(currentPosition);
    }
}
