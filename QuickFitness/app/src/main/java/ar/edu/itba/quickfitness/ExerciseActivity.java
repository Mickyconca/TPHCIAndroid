package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.quickfitness.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends AppCompatActivity {

    //private ActivityExerciseBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable
//                = new ColorDrawable(Color.parseColor("#FF03DAC5"));
//        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Nombre de la Rutina");

        //binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

       findViewById(R.id.backToMain).setOnClickListener(v->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
