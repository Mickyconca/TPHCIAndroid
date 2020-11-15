package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
public class RoutineListFragment extends Fragment {

    private ArrayList<AuxiliarRoutine> routines;
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
        fillList(routines);

        routineRecycler = view.findViewById(R.id.recyclerRoutinesId);
        routineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        setOnClickListener();
        RoutineAdapter adapter = new RoutineAdapter(routines, listener);
        routineRecycler.setAdapter(adapter);

        return view;
    }

    private void setOnClickListener(){
        listener = new RoutineAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(),ExerciseActivity.class);
                startActivity(intent);
            }
        };
    }

    private void fillList(ArrayList<AuxiliarRoutine> arrayList){
        AuxiliarRoutine aux1 = new AuxiliarRoutine("Rutina 1", "Patrick", 3,400);
        AuxiliarRoutine aux2 = new AuxiliarRoutine("Rutina 2", "Micky", 5,1100);
        AuxiliarRoutine aux3 = new AuxiliarRoutine("Rutina 3", "Leo", 5,200);
        AuxiliarRoutine aux4 = new AuxiliarRoutine("Rutina 4", "Frano", 4,400);
        AuxiliarRoutine aux5 = new AuxiliarRoutine("Rutina 5", "Robert", 4,400);

        AuxiliarRoutine[] auxArray = { aux1, aux2, aux3, aux4, aux5};
        Collections.addAll(arrayList, auxArray);
    }
}