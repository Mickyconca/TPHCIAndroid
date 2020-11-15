package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.api.model.User;
import ar.edu.itba.quickfitness.databinding.ActivityLoginBinding;

public class LogInActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        assert binding.loginButton != null;
        binding.loginButton.setOnClickListener(v->{
            Log.d("PRUEBA", "LLEGUEEEEEEEEEEEEe");
            ApiUserService userService = ApiClient.create(ApiUserService.class);
            userService.login(new LoginCredentials("patri1", "1234"))
                    .observe(this, r->{
                        if(r.getError() == null){
                            Log.d("UI", "Token --> " + r.getData().getToken());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
        });
    }
}
