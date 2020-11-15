package ar.edu.itba.quickfitness.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.quickfitness.api.model.ApiError;
import retrofit2.Response;

public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public ApiResponse(Response<T> response){
        parseRespone(response);
    }

    public ApiResponse(Throwable t){
        parseError(t.getMessage());
    }

    private void parseRespone(Response<T> response){
        if(response.isSuccessful()){
            data = response.body();
            return;
        }

        if(response.errorBody() == null){
            parseError("Missing error body");
            return;
        }

        String message;
        try {
            message = response.errorBody().string();
        } catch (IOException e) {
            parseError(e.getMessage());
            return;
        }

        if(message != null && message.trim().length() > 0){
            Gson gson = new Gson();
           error = gson.fromJson(message, new TypeToken<ApiError>() {}.getType());
        }
    }

    private void parseError(String message){

        List<String> details = null;
        if(message != null) {
            details = new ArrayList<>();
            details.add(message);
        }
        this.error = new ApiError(ApiError.LOCAL_UNEXPECTED_ERROR, "Unexpected error", details);
    }



    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }
}
