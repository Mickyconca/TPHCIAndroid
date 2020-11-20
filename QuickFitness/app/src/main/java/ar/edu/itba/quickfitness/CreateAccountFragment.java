package ar.edu.itba.quickfitness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.databinding.FragmentCreateAccountBinding;
import ar.edu.itba.quickfitness.repository.UserRepository;

public class CreateAccountFragment  extends Fragment {

    private MyApplication application;
    private UserRepository userRepository;
    private FragmentCreateAccountBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentCreateAccountBinding.inflate(getLayoutInflater());

        application = (MyApplication) getActivity().getApplication();
        userRepository = application.getUserRepository();

        String name = binding.putName.getText().toString();
        String lastName = binding.putLastName.getText().toString();
        String email = binding.putEmail.getText().toString();
        EditText password = binding.putPassword;

        binding.nextCreateButton.setOnClickListener(v->{
            if(name.length() <= 0 || lastName.length() <= 0 || email.length() <= 0 || password.length() <= 0)
                Toast.makeText(getContext(), "Incomplete data", Toast.LENGTH_SHORT).show();
            else {
                String fullName = name + " " + lastName;
            }
        });









        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication) getActivity().getApplication();
        userRepository = application.getUserRepository();

        binding.nextCreateButton.setOnClickListener(v->{
        });
    }
}
