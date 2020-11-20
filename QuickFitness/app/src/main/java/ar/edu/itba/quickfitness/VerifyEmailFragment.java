package ar.edu.itba.quickfitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiCycleService;
import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.ApiCategoryService;
import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.CategoryOrSportCredentials;
import ar.edu.itba.quickfitness.domain.CategoryDomain;
import ar.edu.itba.quickfitness.domain.UserDomain;
import ar.edu.itba.quickfitness.repository.CategoryRepository;
import ar.edu.itba.quickfitness.repository.CycleRepository;
import ar.edu.itba.quickfitness.repository.RoutineRepository;
import ar.edu.itba.quickfitness.repository.UserRepository;
import ar.edu.itba.quickfitness.vo.Status;

public class VerifyEmailFragment extends Fragment {

    private String email, username, password;
    private EditText code;
    private int routineId, cycleId;
    private MyApplication application;
    private UserRepository userRepository;
    private RoutineRepository routineRepository;
    private CycleRepository cycleRepository;
    private CategoryRepository categoryRepository;
    private CategoryDomain category;


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
        if (bundle != null) {
            email = bundle.getString(CreateAccountFragment.EMAIL_STRING, null);
            username = bundle.getString(CreateAccountFragment.USERNAME_STRING, null);
            password = bundle.getString(CreateAccountFragment.PASSWORD_STRING, null);
        }
        application = (MyApplication) getActivity().getApplication();
        userRepository = application.getUserRepository();
        routineRepository = application.getRoutineRepository();
        cycleRepository = application.getCycleRepository();
        categoryRepository = application.getCategoryRepository();

        TextView emailView = view.findViewById(R.id.emailReplaceView);
        emailView.setText(email);
        code = view.findViewById(R.id.putCode);

        view.findViewById(R.id.verifyButton).setOnClickListener(v -> {
            userRepository.verifyEmail(email, code.getText().toString().toUpperCase()).observe(getViewLifecycleOwner(), r -> {

                AtomicBoolean routineFlag = new AtomicBoolean(false);
                AtomicBoolean cyclesFlag = new AtomicBoolean(true);

                if (r.status == Status.SUCCESS) {

                    userRepository.login(username, password).observe(getViewLifecycleOwner(), q -> {
                        if (q.status == Status.SUCCESS) {

                            routineFlag.set(createFakeRoutine());
                            if (routineFlag.get()) {
                                cyclesFlag.set(createFakeCycles(routineId));
                            }
                        }
                    });
                    if (routineFlag.get() && cyclesFlag.get()) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                } else if (r.status == Status.ERROR)
                    Toast.makeText(getContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
            });
        });
        return view;
    }

    private boolean createFakeRoutine() {
        AtomicBoolean flag = new AtomicBoolean(false);

        categoryRepository.getCategory(1).observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.SUCCESS)
                category = r.data;
            else if(r.status == Status.ERROR){
                if (r.message.compareTo("Unauthorized") != 0 ) {
                    categoryRepository.addCategory("Magic Category", "")
                            .observe(getViewLifecycleOwner(), q -> {
                                if (q.status == Status.SUCCESS)
                                    category = q.data;
                            });
                }
            }
        });

        if (category == null)
            routineRepository.addRoutine("My Bag", "", false, "rookie", category)
                    .observe(getViewLifecycleOwner(), q -> {
                        if (q.status == Status.SUCCESS) {
                            routineId = q.data.getId();
                            flag.set(true);
                        }
                    });
        return flag.get();
    }

    private boolean createFakeCycles(int routineId) {
        AtomicBoolean flag = new AtomicBoolean(true);
        cycleRepository.addCycle(routineId, "Cycle", "",
                "warmup", 1, 1).observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.SUCCESS)
                cycleId = r.data.getId();
            else if (r.status == Status.ERROR)
                flag.set(false);
        });
        cycleRepository.addCycle(routineId, "Cycle",
                "", "exercise", 2, 1).observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.ERROR)
                flag.set(false);
        });
        cycleRepository.addCycle(routineId, "Cycle",
                "", "cooldown", 3, 1).observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.ERROR)
                flag.set(false);
        });
        return flag.get();
    }

    private void updatePhoneRoutineId() {
        AtomicReference<UserDomain> currentUser = null;
        userRepository.getCurrentUser().observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.SUCCESS)
                currentUser.set(r.data);
        });
        userRepository.modifyUser(currentUser.get().getFullName(), String.valueOf(routineId), currentUser.get().getAvatarUrl());
    }

    private void updatePhoneCycleId() {
        AtomicReference<UserDomain> currentUser = null;
        userRepository.getCurrentUser().observe(getViewLifecycleOwner(), r -> {
            if (r.status == Status.SUCCESS)
                currentUser.set(r.data);
        });

        String newPhone = currentUser.get().getPhone() + "/" + cycleId;
        userRepository.modifyUser(currentUser.get().getFullName(), newPhone, currentUser.get().getAvatarUrl());
    }


//    async updateMyRoutineID(routineID) {
//        let routineString = routineID.toString();
//        let credentials = new Credentials(this.username, this.password, this.fullName, this.email, routineString);
//        try {
//            await UserApi.update(credentials);
//            return routineID;
//        } catch (error) {
//            console.log(error);
//        }
//    },

//    async updateMyCycleID(cycleID, firstPhone) {
//        let cycle = cycleID.toString();
//
//        let secondPhone = firstPhone.toString().concat("/", cycle);   //"1/1"
//
//        let credentials = new Credentials(this.username, this.password, this.fullName, this.email, secondPhone);
//        try {
//            await UserApi.update(credentials);
//        } catch (error) {
//            console.log(error);
//        }
//    },
}
