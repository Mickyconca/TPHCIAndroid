package ar.edu.itba.quickfitness.api.model;


import androidx.lifecycle.LiveData;

import ar.edu.itba.quickfitness.api.ApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCategoryService {
    @GET("categories")
    LiveData<ApiResponse<PagedList<CategoryOrSport>>> getCategories();

    @GET("categories")
    LiveData<ApiResponse<PagedList<CategoryOrSport>>> getCategories(@Query("page") int page, @Query("size") int size);

    @POST("categories")
    LiveData<ApiResponse<CategoryOrSport>> addCategory(@Body CategoryOrSportCredentials category);

    @GET("categories/{categoryId}")
    LiveData<ApiResponse<CategoryOrSport>> getCategory(@Path("categoryId") int categoryId);

    @PUT("categories/{categoryId}")
    LiveData<ApiResponse<CategoryOrSport>> modifyCategory(@Path("categoryId") int categoryId, @Body CategoryOrSport category);

    @DELETE("categories/{categoryId}")
    LiveData<ApiResponse<Void>> deleteCategory(@Path("categoryId") int categoryId);

}
