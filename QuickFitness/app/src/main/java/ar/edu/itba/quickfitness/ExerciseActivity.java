package ar.edu.itba.quickfitness;


import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiExerciseService;
import ar.edu.itba.quickfitness.api.model.Exercise;


public class ExerciseActivity extends AppCompatActivity {

    private RoutineViewModel currentRoutineViewModel;
    public static String ROUTINE_ID = "ar.edu.itba.quickfitness.ROUTINE_ID";
    private int routineId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent auxIntent = getIntent();
        routineId = auxIntent.getIntExtra(ROUTINE_ID, 0);

        ExerciseFragment exerciseFragment = new ExerciseFragment();

        ArrayList<Exercise> exerciseList = new ArrayList<>();
        ApiExerciseService exerciseService = ApiClient.create(this, ApiExerciseService.class);
        int cycleId;
        for (int i = 2; i >= 0; i--) {
            cycleId = ((routineId * 3) - 2) - i;
            exerciseService.getAllExercises(routineId, cycleId).observe(this, r -> {
                if (r.getError() == null) {
                    exerciseList.addAll(r.getData().getResults());
                } else {
                    Log.d("EXERCISE LIST ERROR", "ERROR");
                }
            });

            currentRoutineViewModel = new RoutineViewModel(exerciseList);
            TextView exerciseCounterView = findViewById(R.id.exerciseCounter);
            String exerciseCounter = String.valueOf(currentRoutineViewModel.getCurrentPos())+"/" + currentRoutineViewModel.getExerciseAmount();
            exerciseCounterView.setText(exerciseCounter);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseFragmentContainer, exerciseFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        findViewById(R.id.backToMain).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
