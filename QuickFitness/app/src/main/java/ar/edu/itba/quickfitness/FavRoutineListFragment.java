package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.repository.RoutineRepository;

public class FavRoutineListFragment extends Fragment {


    private ArrayList<Routine> routines;
    private RecyclerView routineRecycler;
    private MyApplication application;
    private RoutineRepository routineRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_routine_list, container, false);
        routines = new ArrayList<>();
        application = (MyApplication) getActivity().getApplication();
        routineRepository = application.getRoutineRepository();

        ApiUserService apiUserService = ApiClient.create(getContext(), ApiUserService.class);

        apiUserService.getCurrentUserFavourites().observe(getViewLifecycleOwner(), r -> {
            if (r.getError() == null) {
                routines = new ArrayList<>(r.getData().getResults());
                routineRecycler = view.findViewById(R.id.recyclerFavRoutinesId);
                routineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                RoutineAdapter adapter = new RoutineAdapter(routines, new RoutineAdapter.MyAdapterListener() {
                    @Override
                    public void onClickAddToFav(View v, int position, int routineId) {
                        apiUserService.removeRoutineFromFavourites(routineId).observe(getViewLifecycleOwner(), q -> {
                            if (q.getError() == null) {
                                ImageButton button = v.findViewById(R.id.favButton);
                                button.setImageResource(R.drawable.icon_fav_black_empty);
                            }
                        });
                    }

                    @Override
                    public void onClickStartRoutine(View v, int position, int routineId) {
                        Intent intent = new Intent(getContext(), ExerciseActivity.class);
                        intent.putExtra(ExerciseActivity.ROUTINE_ID,routineId);
                        startActivity(intent);
                    }


                    @Override
                    public void onClickShareRoutine(View v, int position, int routineId){
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.quickfitness.com/id/1");
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, "null");
                        startActivity(shareIntent);
                    }
                });
                routineRecycler.setAdapter(adapter);
            } else {
                Log.d("ERROR", r.getError().getCode() + "");
            }


        });
        return view;
    }
}
