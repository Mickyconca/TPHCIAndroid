package ar.edu.itba.quickfitness;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BreakFragment extends Fragment {

    private CountDownTimer timer;
    private long timeLeft = 10*1000; //secs
    private boolean paused = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_break, container, false);
        // Inflate the layout for this fragment

        startTimer(view);

//


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
                timeLeft = 10 * 1000;

                getParentFragmentManager().popBackStack();
            }
        }.start();

    }
}
