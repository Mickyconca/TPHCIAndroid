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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.model.Routine;

public class RoutineListFragment extends Fragment {

    private ArrayList<Routine> routines;
    private RecyclerView routineRecycler;
    private RoutineAdapter.RecyclerViewClickListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_list, container, false);
        routines = new ArrayList<>();

        ApiUserService apiUserService = ApiClient.create(getContext(), ApiUserService.class);

        apiUserService.getCurrentUserFavourites().observe(getViewLifecycleOwner(), r->{
            if(r.getError() == null){
                routines = new ArrayList<>(r.getData().getResults());
                    routineRecycler = view.findViewById(R.id.recyclerRoutinesId);
                    routineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                    setOnClickListener();

                    RoutineAdapter adapter = new RoutineAdapter(routines, listener);
                    routineRecycler.setAdapter(adapter);
            }
            else {
                Log.d("ERROR", r.getError().getCode()+"");
            }


        });

//        routineRecycler = view.findViewById(R.id.recyclerRoutinesId);
//        routineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        setOnClickListener();
//
//        Log.d("RUTINA", routines.size()+"");
//        RoutineAdapter adapter = new RoutineAdapter(routines, listener);
//        routineRecycler.setAdapter(adapter);

        return view;
    }

    private void setOnClickListener(){
        listener = (view, position) -> {
            Intent intent = new Intent(getContext(),ExerciseActivity.class);
            startActivity(intent);
        };
    }
}