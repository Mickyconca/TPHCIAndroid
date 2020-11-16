package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.AppPreferences;

public class FavouritesFragment extends Fragment {

    FavouritesViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        view.findViewById(R.id.logoutButton).setOnClickListener(v->{
            ApiUserService userService = ApiClient.create(getContext(),ApiUserService.class);
            userService.getCurrentUserFavourites()
                    .observe(getViewLifecycleOwner(), r->{
                        if(r.getError() == null){
                            viewModel.setRoutineList(r.getData().getResults());
                        }
                    });
        });
    }
}