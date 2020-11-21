package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;
import ar.edu.itba.quickfitness.repository.ExerciseRepository;
import ar.edu.itba.quickfitness.vo.Status;

public class ExerciseListFragment extends Fragment {

    private ArrayList<ExerciseDomain> exercises;
    private MyApplication application;
    private ExerciseRepository exerciseRepository;
    private RecyclerView exerciseRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        exercises = new ArrayList<>();
        application = (MyApplication) getActivity().getApplication();
        exerciseRepository = application.getExerciseRepository();

        exerciseRepository.getExercises(savedInstanceState.getInt(CycleListFragment.ROUTINE_ID), savedInstanceState.getInt(CycleListFragment.CYCLE_ID)).observe(getViewLifecycleOwner(), r ->{
            if(r.status == Status.SUCCESS){
                exercises = new ArrayList<>(r.data);
                exerciseRecycler = view.findViewById(R.id.recyclerExercises);
                exerciseRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                ExerciseAdapter adapter = new ExerciseAdapter(exercises);
                exerciseRecycler.setAdapter(adapter);
            }
            else {
                Log.d("ERROR", r.message);
            }
        });
        return view;
    }
}