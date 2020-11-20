package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.ExerciseInfo;
import ar.edu.itba.quickfitness.api.model.ExerciseUpdated;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiExerciseService {

    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getAllExercises(@Path("routineId") int routineId,
                                                               @Path("cycleId") int cycleId);
    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getAllExercises(@Path("routineId") int routineId,
                                                               @Path("cycleId") int cycleId,
                                                               @Query("page") int page,
                                                               @Query("size") int size);

    @POST("routines/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<Exercise>> createExercise(@Path("routineId") int routineId,
                                                   @Path("cycleId") int cycleId,
                                                   @Body ExerciseDomain exercise);

    @GET("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> getExerciseById(@Path("routineId") int routineId,
                                                    @Path("cycleId") int cycleId,
                                                    @Path("exerciseId") int exerciseId);

    @PUT("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> updateExercise(@Path("routineId") int routineId,
                                                          @Path("cycleId") int cycleId,
                                                          @Path("exerciseId") int exerciseId,
                                                          @Body ExerciseDomain exercise);

    @DELETE("routines/{routineId}/cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Void>> deleteExercise(@Path("routineId") int routineId,
                                                   @Path("cycleId") int cycleId,
                                                   @Path("exerciseId") int exerciseId);
}
