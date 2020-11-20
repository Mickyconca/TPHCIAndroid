package ar.edu.itba.quickfitness.api;

import android.app.Application;

import androidx.room.Room;

import ar.edu.itba.quickfitness.api.model.ApiCategoryService;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.repository.AppExecutors;
import ar.edu.itba.quickfitness.repository.CategoryRepository;
import ar.edu.itba.quickfitness.repository.CycleRepository;
import ar.edu.itba.quickfitness.repository.RoutineRepository;
import ar.edu.itba.quickfitness.repository.UserRepository;

public class MyApplication extends Application {

    AppExecutors appExecutors;
    AppPreferences preferences;
    UserRepository userRepository;
    RoutineRepository routineRepository;
    CycleRepository cycleRepository;
    CategoryRepository categoryRepository;

    public AppPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    public CycleRepository getCycleRepository() {
        return cycleRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        appExecutors = new AppExecutors();

        ApiUserService userService = ApiClient.create(this, ApiUserService.class);
        ApiRoutineService routineService = ApiClient.create(this, ApiRoutineService.class);
        ApiCycleService cycleService = ApiClient.create(this,ApiCycleService.class);
        ApiCategoryService categoryService = ApiClient.create(this,ApiCategoryService.class);

        MyDataBase database = Room.databaseBuilder(this, MyDataBase.class, Constants.DATABASE_NAME).build();

        userRepository = new UserRepository(appExecutors, userService, database);

        routineRepository = new RoutineRepository(appExecutors, routineService, database);

        cycleRepository = new CycleRepository(appExecutors, cycleService, database);

        categoryRepository = new CategoryRepository(appExecutors, categoryService, database);
    }
}
