package ar.edu.itba.quickfitness;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class RoutinesFragment extends Fragment {

    private RoutineAdapter adapter;
    private RoutinesFragment binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> routineNames = new ArrayList<>();
        String[] names = {"Rutina 1", "Rutina 2", "Rutina 3", "Rutina 4"};
        Collections.addAll(routineNames, names);

        adapter = new RoutineAdapter(routineNames);
        //binding.routineCardList;    //lleno la lista usando el adapter que creé

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.routine__card_list, container, false);
    }
}
