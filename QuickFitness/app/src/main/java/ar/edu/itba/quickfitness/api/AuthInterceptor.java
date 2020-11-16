package ar.edu.itba.quickfitness.api;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private AppPreferences preferences;

    public AuthInterceptor(Context context) {
        this.preferences = new AppPreferences(context);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if(preferences.getToken() != null){
            request.addHeader("Authorization","Bearer " + preferences.getToken());
        }
        return chain.proceed(request.build());
    }
}
