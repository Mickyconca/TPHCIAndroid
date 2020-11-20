package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.AppPreferences;
import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.databinding.FragmentCreateAccountBinding;
import ar.edu.itba.quickfitness.repository.UserRepository;
import ar.edu.itba.quickfitness.vo.Status;

public class ProfileFragment extends Fragment {

    private MyApplication myApplication;
    private UserRepository userRepository;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        userRepository = myApplication.getUserRepository();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.logoutButton).setOnClickListener(v -> {
            userRepository.logout().observe(getViewLifecycleOwner(), r -> {
                if (r.status == Status.SUCCESS) {
                    Intent intent = new Intent(getContext(), LogInActivity.class);
                    startActivity(intent);
                }

            });
        });
    }
}