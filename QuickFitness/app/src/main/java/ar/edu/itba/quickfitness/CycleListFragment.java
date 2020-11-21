package ar.edu.itba.quickfitness;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.domain.CycleDomain;
import ar.edu.itba.quickfitness.repository.CycleRepository;
import ar.edu.itba.quickfitness.vo.Status;


public class CycleListFragment extends Fragment {
    public final static String ROUTINE_ID = "ar.edu.itba.quickfitness.ROUTINE_ID";
    public final static String CYCLE_ID = "ar.edu.itba.quickfitness.CYCLE_ID";

    private ArrayList<CycleDomain> cycles;
    private MyApplication application;
    private CycleRepository cycleRepository;
    private RecyclerView cycleRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_cycle_list, container, false);
        cycles = new ArrayList<>();
        application = (MyApplication) getActivity().getApplication();
        cycleRepository = application.getCycleRepository();

        cycleRepository.getCycles(savedInstanceState.getInt(CycleListFragment.ROUTINE_ID)).observe(getViewLifecycleOwner(), r ->{
            if(r.status == Status.SUCCESS){
                cycles = new ArrayList<>(r.data);
                cycleRecycler = view.findViewById(R.id.recyclerExercises);
                cycleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                CycleAdapter adapter = new CycleAdapter(cycles);
                cycleRecycler.setAdapter(adapter);
            }
            else {
                Log.d("ERROR", r.message);
            }
        });
        return view;
    }
}
