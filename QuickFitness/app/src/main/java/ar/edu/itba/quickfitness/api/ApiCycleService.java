package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.Cycle;
import ar.edu.itba.quickfitness.api.model.CycleInfo;
import ar.edu.itba.quickfitness.api.model.PagedList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCycleService {
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getAllCycles(@Path("routineId") int routineId, @Query("page") int page, @Query("size") int size);

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getAllCycles(@Path("routineId") int routineId);

    @POST("routines/{routineId}/cycles")
    LiveData<ApiResponse<Cycle>> createCycle(@Path("routineId") int routineId, @Body CycleInfo cycle);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Cycle>> getCycleById(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @PUT("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Cycle>> updateCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId, @Body CycleInfo cycle);

    @DELETE("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Void>> deleteCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);
}
