package ar.edu.itba.quickfitness.api;

import ar.edu.itba.quickfitness.api.model.Cycle;
import ar.edu.itba.quickfitness.api.model.PagedList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCycleInterface {
    @GET("routines/{routineId}/cycles")
    Call<PagedList<Cycle>> getAllCycles(@Path("routineId") int routineId);

    @POST("routines/{routineId}/cycles")
    Call<Cycle> createCycle(@Path("routineId") int routineId, @Body Cycle cycle);

    @GET("routines/{routineId}/cycles/{cycleId}")
    Call<Cycle> getCycleById(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @PUT("routines/{routineId}/cycles/{cycleId}")
    Call<Cycle> updateCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId, @Body Cycle cycle);

    @DELETE("routines/{routineId}/cycles/{cycleId}")
    Call<Void> deleteCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);
}
