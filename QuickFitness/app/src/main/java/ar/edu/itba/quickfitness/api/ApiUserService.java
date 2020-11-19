package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.api.model.Token;
import ar.edu.itba.quickfitness.api.model.UpdateUserCredentials;
import ar.edu.itba.quickfitness.api.model.User;
import ar.edu.itba.quickfitness.api.model.UserCredentials;
import ar.edu.itba.quickfitness.api.model.VerifyEmailCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUserService {

    @POST("user")
    LiveData<ApiResponse<User>> createUser(@Body UserCredentials credentials);

    @POST("user/verify_email")
    LiveData<ApiResponse<Void>> verifyEmail(@Body VerifyEmailCredentials credentials);

    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body LoginCredentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @PUT("user/current")
    LiveData<ApiResponse<User>> updateCurrentUser(@Body UpdateUserCredentials credentials);

    @DELETE("user/current")
    LiveData<ApiResponse<Void>> deleteCurrentUser();

    @GET("user/{userId}/routines")
    LiveData<ApiResponse<PagedList<Routine>>> getUserRoutines(@Path("userId") int userId);

    @GET("user/current/routines/")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserRoutines();

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserFavourites();

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>>addRoutineToFavourites(@Path("routineId") int routineId);

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>>removeRoutineFromFavourites(@Path("routineId") int routineId);

    @GET("user/current/routines/executions")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserExecutions();










}
