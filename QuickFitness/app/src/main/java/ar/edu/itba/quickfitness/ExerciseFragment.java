package ar.edu.itba.quickfitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import ar.edu.itba.quickfitness.api.model.Exercise;

public class ExerciseFragment extends Fragment {

    private CountDownTimer timer;
    private long timeLeft; //secs
    private String name;
    private String detail;
    private boolean paused = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        // Inflate the layout for this fragment

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        timeLeft = bundle.getInt(ExerciseActivity.EXERCISE_TIME, 30)*1000;
        name = bundle.getString(ExerciseActivity.EXERCISE_NAME, null);
        detail = bundle.getString(ExerciseActivity.EXERCISE_DESCRIPTION, null);
        startTimer(view);

        TextView exerciseName = view.findViewById(R.id.exerciseName);
        exerciseName.setText(name);
        TextView exerciseDetail = view.findViewById(R.id.exerciseDescription);
        exerciseDetail.setText(detail);

        return view;
    }

    private void startTimer(View view){
        paused = false;
        timer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft= millisUntilFinished;
                int minutes = (int) timeLeft / 60000;
                int secs = (int) timeLeft%60000 /1000;

                StringBuilder timeLeftStr = new StringBuilder();
                if(minutes == 0)
                    timeLeftStr.append("00");
                else
                    timeLeftStr.append(minutes);
                timeLeftStr.append(":");
                if(secs<10)
                    timeLeftStr.append(0);
                timeLeftStr.append(secs);

                TextView chrono = view.findViewById(R.id.chrono);
                chrono.setText(timeLeftStr.toString());


            }

            @Override
            public void onFinish() {
                timer.cancel();
                timeLeft = 30 * 1000;

//                BreakFragment fragment = new BreakFragment();
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.exerciseFragmentContainer, fragment);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        }.start();

    }
}
