package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.PagedList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSportService {

    @GET("sports")
    LiveData<ApiResponse<PagedList<CategoryOrSport>>> getSports();

    @POST("sports")
    LiveData<ApiResponse<CategoryOrSport>>addSport(@Body CategoryOrSport sport);

    @GET("sports/{sportId}")
    LiveData<ApiResponse<CategoryOrSport>> getSport(@Path("sportId") int sportId);

    @PUT("sports/{sportId}")
    LiveData<ApiResponse<CategoryOrSport>> modifySport(@Path("sportId") int sportId, @Body CategoryOrSport sport);

    @DELETE("sports/{sportId}")
    LiveData<ApiResponse<Void>> deleteSport(@Path("sportId") int sportId);

}
