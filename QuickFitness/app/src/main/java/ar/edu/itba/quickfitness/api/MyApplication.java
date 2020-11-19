package ar.edu.itba.quickfitness.api;

import android.app.Application;

import androidx.room.Room;

import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.repository.AppExecutors;
import ar.edu.itba.quickfitness.repository.RoutineRepository;
import ar.edu.itba.quickfitness.repository.UserRepository;

public class MyApplication extends Application {

    AppExecutors appExecutors;
    AppPreferences preferences;
    UserRepository userRepository;
    RoutineRepository routineRepository;

    public AppPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        appExecutors = new AppExecutors();

        ApiUserService userService = ApiClient.create(this, ApiUserService.class);
        ApiRoutineService routineService = ApiClient.create(this, ApiRoutineService.class);

        MyDataBase database = Room.databaseBuilder(this, MyDataBase.class, Constants.DATABASE_NAME).build();

        userRepository = new UserRepository(appExecutors, userService, database);

        routineRepository = new RoutineRepository(appExecutors, routineService, database);
    }
}
