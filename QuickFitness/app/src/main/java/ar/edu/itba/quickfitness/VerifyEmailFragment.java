package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.domain.RoutineDomain;
import ar.edu.itba.quickfitness.repository.CycleRepository;
import ar.edu.itba.quickfitness.repository.RoutineRepository;
import ar.edu.itba.quickfitness.repository.UserRepository;
import ar.edu.itba.quickfitness.vo.Status;

public class VerifyEmailFragment extends Fragment {

    private String email;
    private String code;
    private MyApplication application;
    private UserRepository userRepository;
    private RoutineRepository routineRepository;
    private CycleRepository cycleRepository;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_verify_email, container, false);

        Bundle bundle = getArguments();
        if(bundle != null)
            email = bundle.getString(CreateAccountFragment.EMAIL_STRING, null);

        application = (MyApplication) getActivity().getApplication();
        userRepository = application.getUserRepository();
        routineRepository = application.getRoutineRepository();
        cycleRepository = application.getCycleRepository();

        TextView emailView = view.findViewById(R.id.emailReplaceView);
        emailView.setText(email);
        EditText code = view.findViewById(R.id.putCode);

        view.findViewById(R.id.verifyButton).setOnClickListener(v->{
            userRepository.verifyEmail(email, code.getText().toString()).observe(getViewLifecycleOwner(), r->{
                if(r.status == Status.SUCCESS){

                    int routineId;
                    int cycleId;

                    routineRepository.addRoutine("My Bag", "",false, "rookie", new CategoryOrSport(1))
                            .observe(getViewLifecycleOwner(), q->{
                            });


                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
        });


        return view;
    }

}
