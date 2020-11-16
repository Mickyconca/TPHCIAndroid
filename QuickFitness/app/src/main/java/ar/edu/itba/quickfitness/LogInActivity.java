package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.AppPreferences;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.databinding.ActivityLoginBinding;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(v->{
            Log.d("PRUEBA", "LLEGUEEEEEEEEEEEEe");
            ApiUserService userService = ApiClient.create(getApplicationContext(),ApiUserService.class);
            userService.login(new LoginCredentials("johndoe", "1234567890"))
                    .observe(this, r->{
                        if(r.getError() == null){
                            Log.d("UI", "Token --> " + r.getData().getToken());
                            AppPreferences preferences = new AppPreferences(getApplicationContext());
                            preferences.setAuthToken(r.getData().getToken());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
        });
    }
}
