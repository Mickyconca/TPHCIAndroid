package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?,?> get(@NotNull Type type, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit){
        if(getRawType(type) != LiveData.class)
            return null;

        Type observableType = getParameterUpperBound(0, (ParameterizedType) type);

        Class<?> rawObservableType = getRawType(observableType);
        if(rawObservableType != ApiResponse.class)
            throw new IllegalArgumentException("type must be an api response");
        if(!(observableType instanceof ParameterizedType))
            throw new IllegalArgumentException("api response must be parametrized");

        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);

    }
}
