package ar.edu.itba.quickfitness;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiExerciseService;
import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.database.dao.ExerciseDao;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;
import ar.edu.itba.quickfitness.repository.ExerciseRepository;
import ar.edu.itba.quickfitness.repository.RoutineRepository;
import ar.edu.itba.quickfitness.vo.Status;


public class ExerciseActivity extends AppCompatActivity {

    private ExerciseViewModel currentRoutineViewModel;
    public static String ROUTINE_ID = "ar.edu.itba.quickfitness.ROUTINE_ID";
    public static final String CURRENT_POS = "ar.edu.itba.quickfitness.CURRENT_POS";
    public static final String EXERCISE_NAME = "ar.edu.itba.quickfitness.EXERCISE_NAME";
    public static final String EXERCISE_DESCRIPTION = "ar.edu.itba.quickfitness.EXERCISE_DESCRIPTION";
    public static final String EXERCISE_TIME = "ar.edu.itba.quickfitness.EXERCISE_TIME";

    private int routineId;
    private MyApplication application;
    private ExerciseRepository exerciseRepository;
    private RoutineRepository routineRepository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent auxIntent = getIntent();
        routineId = auxIntent.getIntExtra(ROUTINE_ID, 0);

        ArrayList<ExerciseDomain> exerciseList = new ArrayList<>();

        application = (MyApplication) getApplication();
        exerciseRepository = application.getExerciseRepository();
        routineRepository = application.getRoutineRepository();

        exerciseRepository.getExercises(routineId, routineId * 3 - 4).observe(this, r -> {
            if (r.status == Status.SUCCESS) {
                exerciseList.addAll(r.data);
            } else if (r.status == Status.LOADING)
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
            else {
                Log.d("EXERCISE LIST ERROR", "ERROR");
            }
        });
        exerciseRepository.getExercises(routineId, routineId * 3 - 2).observe(this, r -> {
            if (r.status == Status.SUCCESS) {
                exerciseList.addAll(r.data);
            } else if (r.status == Status.LOADING)
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
            else {
                Log.d("EXERCISE LIST ERROR", "ERROR");
            }
        });
        exerciseRepository.getExercises(routineId, routineId * 3 - 2).observe(this, r -> {
            if (r.status == Status.SUCCESS) {
                exerciseList.addAll(r.data);
                startRoutine(exerciseList);
            } else if (r.status == Status.LOADING)
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
            else {
                Log.d("EXERCISE LIST ERROR", "ERROR");
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        routineRepository.getRoutine(routineId).observe(this, r -> {
            if (r.status == Status.SUCCESS)
                actionBar.setTitle(r.data.getName());

        });

        findViewById(R.id.backToMain).setOnClickListener(v -> {
            onBackPressed();
            onBackPressed();

        });

        findViewById(R.id.backExerciseButton).setOnClickListener(v -> {
            if (currentRoutineViewModel.isFirstExercise())
                onBackPressed();
        });
    }

    private void startRoutine(ArrayList<ExerciseDomain> exerciseList){
        Bundle bundle = new Bundle();
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        currentRoutineViewModel = new ExerciseViewModel(exerciseList);
        TextView exerciseCounterView = findViewById(R.id.exerciseCounter);
        String exerciseCounter = String.valueOf(currentRoutineViewModel.getCurrentPosition()) + "/" + currentRoutineViewModel.getExerciseAmount();
        exerciseCounterView.setText(exerciseCounter);
        bundle.putInt(CURRENT_POS, currentRoutineViewModel.getCurrentPosition());
        bundle.putString(EXERCISE_NAME, currentRoutineViewModel.getExercise(currentRoutineViewModel.getCurrentPosition()).getName());
        bundle.putString(EXERCISE_DESCRIPTION, currentRoutineViewModel.getExercise(currentRoutineViewModel.getCurrentPosition()).getDetail());
        bundle.putInt(EXERCISE_TIME, currentRoutineViewModel.getExercise(currentRoutineViewModel.getCurrentPosition()).getDuration());

        exerciseFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseFragmentContainer, exerciseFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
