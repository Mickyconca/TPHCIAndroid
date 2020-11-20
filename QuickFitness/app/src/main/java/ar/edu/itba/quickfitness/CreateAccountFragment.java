package ar.edu.itba.quickfitness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.repository.UserRepository;
import ar.edu.itba.quickfitness.vo.Status;

public class CreateAccountFragment extends Fragment {

    public static String EMAIL_STRING = "ar.edu.itba.quickfitnness.CreateAccountFragment";

    private MyApplication application;
    private UserRepository userRepository;
    private TextView nameView, lastNameView, usernameView,emailView,passwordView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        application = (MyApplication) getActivity().getApplication();
        userRepository = application.getUserRepository();

        //ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(UserRepository.class,application.getUserRepository());


        nameView = view.findViewById(R.id.putName);
        lastNameView = view.findViewById(R.id.putLastName);
        usernameView = view.findViewById(R.id.putUsername);
        emailView = view.findViewById(R.id.putEmail);
        passwordView = view.findViewById(R.id.putPassword);

        view.findViewById(R.id.nextCreateButton).setOnClickListener(v -> {
            if (emptyData())
                Toast.makeText(getContext(), "Incomplete data", Toast.LENGTH_SHORT).show();
            else {
                String fullName = nameView.getText() + " " + lastNameView.getText();
                userRepository.createUser(usernameView.getText().toString(),
                        fullName,passwordView.getText().toString(),emailView.getText().toString())
                        .observe(getViewLifecycleOwner(),r->{
                            if(r.status == Status.SUCCESS){
                                VerifyEmailFragment verifyEmailFragment = new VerifyEmailFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(EMAIL_STRING, emailView.getText().toString());
                                verifyEmailFragment.setArguments(bundle);
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                transaction.replace(R.id.loginFrameContainer, verifyEmailFragment);
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                transaction.commit();
                            }
                            else if(r.status == Status.ERROR){
                                Toast.makeText(getContext(),r.message, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        view.findViewById(R.id.backToLoginButton).setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        return view;
    }

    private boolean emptyData(){
        return nameView.getText().length() <= 0 || lastNameView.getText().length() <= 0
                || usernameView.getText().length() <= 0 || emailView.getText().length() <= 0 || passwordView.getText().length() <=0;
    }
}
