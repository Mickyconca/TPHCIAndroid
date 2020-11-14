package ar.edu.itba.quickfitness.api;

import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.api.model.Token;
import ar.edu.itba.quickfitness.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUserInterface {

    @POST("user")
    Call<User> getCredentials(@Body User credentials);

    @POST("user/verify_email")
    Call<Void> verifyEmail(@Body User credentials);

    @POST("user/login")
    Call<Token> login(@Body User credentials);

    @POST("user/logout")
    Call<Void> logout();

    @GET("user/current")
    Call<User> getCurrentUser();

    @PUT("user/current")
    Call<User> updateCurrentUser(@Body User credentials);

    @DELETE("user/current")
    Call<Void> deleteCurrentUser();

    @GET("user/current/routines")
    Call<PagedList<Routine>> getCurrentUserRoutines();

    @GET("user/{userId}/routines")
    Call<PagedList<Routine>> getUserRoutines(@Path("userId") int userId);

    @GET("user/current/routines/favourites")
    Call<PagedList<Routine>> getCurrentUserFavourites();

    @DELETE("user/current/routines/{routineId}/favourites")
    Call<Void> removeRoutineFromFavourites(@Path("routineId") int routineId);

    @GET("user/current/routines/executions")
    Call<PagedList<Routine>> getCurrentUserExecutions();










}
