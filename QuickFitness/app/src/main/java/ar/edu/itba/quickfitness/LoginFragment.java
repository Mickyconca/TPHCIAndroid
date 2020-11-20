package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.concurrent.atomic.AtomicInteger;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.repository.UserRepository;

public class LoginFragment extends Fragment {

    private MyApplication myApplication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        myApplication = (MyApplication) getActivity().getApplication();
        UserRepository userRepository  = myApplication.getUserRepository();

        EditText username = view.findViewById(R.id.putUsername);
        EditText password = view.findViewById(R.id.putPassword);
        ImageButton showPassword = view.findViewById(R.id.showPasswordButton);
        int covered = password.getInputType();

        AtomicInteger aux = new AtomicInteger();
        showPassword.setOnClickListener(v->{
            if(aux.get() %2 == 0) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else
                password.setInputType(covered);
            aux.getAndIncrement();
        });

        view.findViewById(R.id.loginButton).setOnClickListener(v -> {

            if (username.getText().toString().length() <= 0)
                Toast.makeText(getContext(), R.string.invalid_username, Toast.LENGTH_LONG).show();
            else if (password.getText().toString().length() <= 0)
                Toast.makeText(getContext(), R.string.invalid_password, Toast.LENGTH_LONG).show();
            else {
                userRepository.login(username.getText().toString(), password.getText().toString()).observe(getViewLifecycleOwner(), r->{
                    switch (r.status){
                        case LOADING:
                            Toast.makeText(getContext(), "Cargando", Toast.LENGTH_SHORT).show();
                            break;
                        case SUCCESS:
                            myApplication.getPreferences().setAuthToken(r.data);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                        case ERROR:
                            Toast.makeText(getContext(), r.message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        view.findViewById(R.id.createAccountButton).setOnClickListener(v->{
            CreateAccountFragment createAccountFragment = new CreateAccountFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.loginFrameContainer, createAccountFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            //transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
}
