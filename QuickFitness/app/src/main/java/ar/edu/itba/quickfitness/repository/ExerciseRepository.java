package ar.edu.itba.quickfitness.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;


import ar.edu.itba.quickfitness.api.ApiExerciseService;
import ar.edu.itba.quickfitness.api.ApiResponse;
import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.api.model.ExerciseInfo;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.database.entity.ExerciseEntity;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;
import ar.edu.itba.quickfitness.vo.AbsentLiveData;
import ar.edu.itba.quickfitness.vo.Resource;

import static java.util.stream.Collectors.toList;

public class ExerciseRepository {


    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiExerciseService service;
    private MyDataBase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public ExerciseRepository(AppExecutors executors, ApiExerciseService service, MyDataBase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private ExerciseDomain mapExerciseEntityToDomain(ExerciseEntity entity) {
        return new ExerciseDomain(entity.id, entity.name, entity.detail, entity.type, entity.order, entity.repetitions, entity.order);
    }

    private ExerciseEntity mapExerciseToEntity(Exercise model) {
        return new ExerciseEntity(model.getId(), model.getName(), model.getDetail(), model.getType(), model.getOrder(), model.getRepetitions(), model.getOrder());
    }

    private ExerciseDomain mapExerciseToDomain(Exercise model) {
        return new ExerciseDomain(model.getId(), model.getName(), model.getDetail(), model.getType(), model.getOrder(), model.getRepetitions(), model.getOrder());
    }

    private Exercise mapExerciseDomainToModel(ExerciseDomain domain) {
        Exercise model = new Exercise();
        model.setId(domain.getId());
        model.setName(domain.getName());
        model.setDetail(domain.getDetail());
        model.setType(domain.getType());
        model.setOrder(domain.getOrder());
        model.setRepetitions(domain.getRepetitions());
        model.setOrder(domain.getOrder());

        return model;
    }

    public LiveData<Resource<List<ExerciseDomain>>> getExercises(int routineId, int cycleId) {

        return new NetworkBoundResource<List<ExerciseDomain>, List<ExerciseEntity>, PagedList<Exercise>>(executors,
                entities -> {
                    return entities.stream()
                            .map(exerciseEntity -> new ExerciseDomain(exerciseEntity.id, exerciseEntity.name, exerciseEntity.detail, exerciseEntity.type, exerciseEntity.duration, exerciseEntity.repetitions, exerciseEntity.order))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exercise -> new ExerciseEntity(exercise.getId(), exercise.getName(), exercise.getDetail(), exercise.getType(), exercise.getDuration(), exercise.getRepetitions(), exercise.getOrder()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exercise -> new ExerciseDomain(exercise.getId(), exercise.getName(), exercise.getDetail(), exercise.getType(), exercise.getDuration(), exercise.getRepetitions(), exercise.getOrder()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<ExerciseEntity> entities) {
                database.exerciseDao().deleteAll();
                database.exerciseDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ExerciseEntity> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Exercise> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ExerciseEntity>> loadFromDb() {
                return database.exerciseDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return service.getAllExercises(routineId, cycleId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<ExerciseDomain>>> getExercises(int routineId, int cycleId, int page, int size) {

        return new NetworkBoundResource<List<ExerciseDomain>, List<ExerciseEntity>, PagedList<Exercise>>(executors,
                entities -> {
                    return entities.stream()
                            .map(exerciseEntity -> new ExerciseDomain(exerciseEntity.id, exerciseEntity.name, exerciseEntity.detail, exerciseEntity.type, exerciseEntity.duration, exerciseEntity.repetitions, exerciseEntity.order))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exercise -> new ExerciseEntity(exercise.getId(), exercise.getName(), exercise.getDetail(), exercise.getType(), exercise.getDuration(), exercise.getRepetitions(), exercise.getOrder()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(exercise -> new ExerciseDomain(exercise.getId(), exercise.getName(), exercise.getDetail(), exercise.getType(), exercise.getDuration(), exercise.getRepetitions(), exercise.getOrder()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<ExerciseEntity> entities) {
                database.exerciseDao().delete(size, page * size);
                database.exerciseDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ExerciseEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Exercise> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ExerciseEntity>> loadFromDb() {
                return database.exerciseDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return service.getAllExercises(routineId, cycleId, page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExerciseDomain>> getExercise(int routineId, int cycleId, int exerciseId) {
        return new NetworkBoundResource<ExerciseDomain, ExerciseEntity, Exercise>(executors, this::mapExerciseEntityToDomain, this::mapExerciseToEntity, this::mapExerciseToDomain) {

            @Override
            protected void saveCallResult(@NonNull ExerciseEntity entity) {
                database.exerciseDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable ExerciseEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Exercise model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ExerciseEntity> loadFromDb() {
                return database.exerciseDao().findById(exerciseId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return service.getExerciseById(routineId, cycleId, exerciseId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExerciseDomain>> addExercise(int routineId, int cycleId, String name, String detail, String type, Integer order, String duration,Integer repetitions) {

        return new NetworkBoundResource<ExerciseDomain, ExerciseEntity, Exercise>(executors, this::mapExerciseEntityToDomain, this::mapExerciseToEntity, this::mapExerciseToDomain) {
            int sportId = 0;

            @Override
            protected void saveCallResult(@NonNull ExerciseEntity entity) {
                sportId = entity.id;
                database.exerciseDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable ExerciseEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Exercise model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ExerciseEntity> loadFromDb() {
                if (sportId == 0)
                    return AbsentLiveData.create();
                else
                    return database.exerciseDao().findById(sportId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return service.createExercise(routineId, cycleId, new ExerciseDomain(name, detail, type, order, repetitions, order));
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExerciseDomain>> modifyExercise(int routineId, int cycleId, int exerciseId, ExerciseDomain exerciseInfo) {
        return new NetworkBoundResource<ExerciseDomain, ExerciseEntity, Exercise>(executors, this::mapExerciseEntityToDomain, this::mapExerciseToEntity, this::mapExerciseToDomain) {

            @Override
            protected void saveCallResult(@NonNull ExerciseEntity entity) {
                database.exerciseDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable ExerciseEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Exercise model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ExerciseEntity> loadFromDb() {
                return database.exerciseDao().findById(exerciseId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return service.updateExercise(routineId, cycleId, exerciseId,exerciseInfo);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteExercise(int routineId, int cycleId, int exerciseId) {
        return new NetworkBoundResource<Void, ExerciseEntity, Void>(executors,
                entity -> {
                    return null;
                },
                model -> {
                    return null;
                },
                model -> {
                    return null;
                }) {

            @Override
            protected void saveCallResult(@NonNull ExerciseEntity entity) {
                database.exerciseDao().delete(exerciseId);
            }

            @Override
            protected boolean shouldFetch(@Nullable ExerciseEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ExerciseEntity> loadFromDb() {
                return database.exerciseDao().findById(exerciseId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.deleteExercise(routineId,cycleId,exerciseId);
            }
        }.asLiveData();
    }
}
