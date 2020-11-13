package ar.edu.itba.quickfitness.api;

import ar.edu.itba.quickfitness.api.model.UserCredentials;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiUserInterface {

    @POST("user/login")
    Call<UserCredentials> getCredentials();

}
