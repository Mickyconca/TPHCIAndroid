package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.AppPreferences;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(v -> {

            EditText username = (EditText) findViewById(R.id.putUsername);
            EditText password = (EditText) findViewById(R.id.putPassword);

            if (username.getText().toString().length() <= 0)
                Toast.makeText(this, R.string.invalid_username, Toast.LENGTH_LONG).show();
            else if (password.getText().toString().length() <= 0)
                Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_LONG).show();
            else {
                ApiUserService userService = ApiClient.create(getApplicationContext(), ApiUserService.class);
                userService.login(new LoginCredentials(username.getText().toString(), password.getText().toString()))
                        .observe(this, r -> {
                            if (r.getError() == null) {
                                AppPreferences preferences = new AppPreferences(getApplicationContext());
                                preferences.setAuthToken(r.getData().getToken());
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, r.getError().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
