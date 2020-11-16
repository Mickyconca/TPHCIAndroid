package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.ExerciseInfo;
import ar.edu.itba.quickfitness.api.model.ExerciseUpdated;
import ar.edu.itba.quickfitness.api.model.PagedList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiExerciseService {

    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getAllExercises(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @POST("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<Exercise>> createExercise(@Path("routineId") int routineId,
                                  @Path("cycleId") int cycleId,
                                  @Body ExerciseInfo exercise);

    @GET("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> getExerciseById(@Path("routineId") int routineId,
                                   @Path("cycleId") int cycleId,
                                   @Path("exerciseId") int exerciseId);

    @PUT("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<ExerciseUpdated>> updateExercise(@Path("routineId") int routineId,
                                                          @Path("cycleId") int cycleId,
                                                          @Path("exerciseId") int exerciseId,
                                                          @Body ExerciseInfo exercise);

    @DELETE("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> deleteExercise(@Path("routineId") int routineId,
                                  @Path("cycleId") int cycleId,
                                  @Path("exerciseId") int exerciseId);
}
