package ar.edu.itba.quickfitness.api;

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

public interface ApiRoutineInterface {

    @GET("routines")
    Call<PagedList<Routine>> getAllRoutines();

    @POST("routines")
    Call<Routine> createRoutine(@Body Routine routine);

    @GET("routines/{routineId}")
    Call<Routine> getRoutineById(@Path("routineId") int routineId);

    @PUT("routines/{routineId}")
    Call<Routine> updateRoutine(@Path("routineId") int routineId, Routine routine);

    @DELETE("routines/{routineId}")
    Call<Void> deleteRoutine(@Path("routineId") int routineId);

    @GET("routines/{routineId}/ratings")
    Call<PagedList<Rating>> getRoutineRating(@Path("routineId") int routineId);

    @POST("routines/{routineId}/ratings")
    Call<PagedList<Routine>> setRoutineRating(@Path("routineId") int routineId, Routine routine);
}
