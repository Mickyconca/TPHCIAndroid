package ar.edu.itba.quickfitness.api;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<ApiResponse<T>>> {

    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }


    //adapto el call que recibi a un LiveData<ApiResponse>
    @Override
    public LiveData<ApiResponse<T>> adapt(Call<T> call){
        return new LiveData<ApiResponse<T>>(){

            final AtomicBoolean started = new AtomicBoolean(false);

            //dispara la llamada de retrofit (hace la llamade del api)
            @Override
            protected void onActive(){  //Alguien esta observando este liveData
                super.onActive();

                if(started.compareAndSet(false, true)) //Si esta en falso lo pasa en true (deveulve true)
                    call.enqueue(new Callback<T>() {    //manejo la rta satisf y la de error

                        @Override
                        public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
                            postValue(new ApiResponse<>(t));
                        }
                    });
            }
        };
    }
}
