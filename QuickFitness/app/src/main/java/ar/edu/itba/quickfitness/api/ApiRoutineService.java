package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.api.model.Rating;
import ar.edu.itba.quickfitness.api.model.Routine;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getAllRoutines();

    @POST("routines")
    LiveData<ApiResponse<Routine>> createRoutine(@Body Routine routine);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutineById(@Path("routineId") int routineId);

    @PUT("routines/{routineId}")
    LiveData<ApiResponse<Routine>> updateRoutine(@Path("routineId") int routineId, Routine routine);

    @DELETE("routines/{routineId}")
    LiveData<ApiResponse<Void>> deleteRoutine(@Path("routineId") int routineId);

    @GET("routines/{routineId}/ratings")
    LiveData<ApiResponse<PagedList<Rating>>> getRoutineRating(@Path("routineId") int routineId);

    @POST("routines/{routineId}/ratings")
    LiveData<ApiResponse<PagedList<Routine>>> setRoutineRating(@Path("routineId") int routineId, Routine routine);
}