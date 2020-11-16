package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.quickfitness.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends AppCompatActivity {

    //private ActivityExerciseBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

       findViewById(R.id.backToMain).setOnClickListener(v->{
            Intent intent = new Intent(this,LogInActivity.class);
            startActivity(intent);
        });
    }


}
