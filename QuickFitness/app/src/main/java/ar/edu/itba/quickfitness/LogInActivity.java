package ar.edu.itba.quickfitness;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

import ar.edu.itba.quickfitness.api.ApiClient;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.AppPreferences;
import ar.edu.itba.quickfitness.api.MyApplication;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.repository.UserRepository;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyApplication application = (MyApplication) getApplication();
        UserRepository userRepository  = application.getUserRepository();

        getSupportActionBar().hide();

        EditText username = findViewById(R.id.putUsername);
        EditText password = findViewById(R.id.putPassword);
        ImageButton showPassword = findViewById(R.id.showPasswordButton);
        int covered = password.getInputType();

        AtomicInteger aux = new AtomicInteger();
        showPassword.setOnClickListener(v->{
            if(aux.get() %2 == 0) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            }
            else
                password.setInputType(covered);
            aux.getAndIncrement();
        });

        findViewById(R.id.loginButton).setOnClickListener(v -> {

            if (username.getText().toString().length() <= 0)
                Toast.makeText(this, R.string.invalid_username, Toast.LENGTH_LONG).show();
            else if (password.getText().toString().length() <= 0)
                Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_LONG).show();
            else {
                userRepository.login(username.getText().toString(), password.getText().toString()).observe(this, r->{
                    switch (r.status){
                        case LOADING:
                            Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show();
                            break;
                        case SUCCESS:
                            application.getPreferences().setAuthToken(r.data);
                            break;
                        case ERROR:
                            Toast.makeText(this, r.message, Toast.LENGTH_SHORT).show();
                    }
                });







//                ApiUserService userService = ApiClient.create(getApplicationContext(), ApiUserService.class);
//                userService.login(new LoginCredentials(username.getText().toString(), password.getText().toString()))
//                        .observe(this, r -> {
//                            if (r.getError() == null) {
//                                AppPreferences preferences = new AppPreferences(getApplicationContext());
//                                preferences.setAuthToken(r.getData().getToken());
//                                Intent intent = new Intent(this, MainActivity.class);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(this, r.getError().getDescription(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
            }
        });
    }
}
