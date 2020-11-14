package ar.edu.itba.quickfitness.api;

import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.PagedList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiExerciseInterface {

    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    Call<PagedList<Exercise>> getAllExercises(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @POST("routines/{routineId}/cycles/{cycleId}/exercises")
    Call<Exercise> createExercise(@Path("routineId") int routineId,
                                  @Path("cycleId") int cycleId,
                                  @Body Exercise exercise);

    @GET("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    Call<Exercise> getExerciseById(@Path("routineId") int routineId,
                                   @Path("cycleId") int cycleId,
                                   @Path("exerciseId") int exerciseId);

    @PUT("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    Call<Exercise> updateExercise(@Path("routineId") int routineId,
                               @Path("cycleId") int cycleId,
                               @Path("exerciseId") int exerciseId,
                               @Body Exercise exercise);

    @DELETE("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    Call<Exercise> deleteExercise(@Path("routineId") int routineId,
                                  @Path("cycleId") int cycleId,
                                  @Path("exerciseId") int exerciseId);
}
