package ar.edu.itba.quickfitness.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ar.edu.itba.quickfitness.R;
import ar.edu.itba.quickfitness.api.ApiResponse;
import ar.edu.itba.quickfitness.api.ApiRoutineService;
import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.api.model.RoutineCredentials;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.database.entity.RoutineEntity;
import ar.edu.itba.quickfitness.domain.RoutineDomain;
import ar.edu.itba.quickfitness.vo.AbsentLiveData;
import ar.edu.itba.quickfitness.vo.Resource;

import static java.util.stream.Collectors.toList;

public class RoutineRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoutineService service;
    private MyDataBase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, MyDataBase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private RoutineDomain mapRoutineEntityToDomain(RoutineEntity entity) {
        return new RoutineDomain(entity.id, entity.name, entity.detail, entity.dateCreated, entity.averageRating, entity.isPublic, entity.difficulty, entity.creator, entity.category);
    }

    private RoutineEntity mapRoutineToEntity(Routine model) {
        return new RoutineEntity(model.getId(), model.getName(), model.getDetail(), model.getDateCreated(), model.getAverageRating(), model.getIsPublic(), model.getDifficulty(), model.getCreator(), model.getCategory());
    }

    private RoutineDomain mapRoutineToDomain(Routine model) {
        return new RoutineDomain(model.getId(), model.getName(), model.getDetail(), model.getDateCreated(), model.getAverageRating(), model.getIsPublic(), model.getDifficulty(), model.getCreator(), model.getCategory());
    }

    private Routine mapRoutineDomainToModel(RoutineDomain domain) {
        Routine model = new Routine();
        model.setId(domain.getId());
        model.setName(domain.getName());
        model.setDetail(domain.getDetail());
        return model;
    }

    public LiveData<Resource<List<RoutineDomain>>> getRoutines() {

        return new NetworkBoundResource<List<RoutineDomain>, List<RoutineEntity>, PagedList<Routine>>(executors,
                entities -> {
                    return entities.stream()
                            .map(routineEntity -> new RoutineDomain(routineEntity.id, routineEntity.name, routineEntity.detail, routineEntity.dateCreated, routineEntity.averageRating, routineEntity.isPublic, routineEntity.difficulty, routineEntity.creator, routineEntity.category))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routine -> new RoutineEntity(routine.getId(), routine.getName(), routine.getDetail(), routine.getDateCreated(), routine.getAverageRating(), routine.getIsPublic(), routine.getDifficulty(), routine.getCreator(), routine.getCategory()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routine -> new RoutineDomain(routine.getId(), routine.getName(), routine.getDetail(), routine.getDateCreated(), routine.getAverageRating(), routine.getIsPublic(), routine.getDifficulty(), routine.getCreator(), routine.getCategory()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<RoutineEntity> entities) {
                database.routineDao().deleteAll();
                database.routineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineEntity> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Routine> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineEntity>> loadFromDb() {
                return database.routineDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return service.getAllRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<RoutineDomain>>> getRoutines(int page, int size) {

        return new NetworkBoundResource<List<RoutineDomain>, List<RoutineEntity>, PagedList<Routine>>(executors,
                entities -> {
                    return entities.stream()
                            .map(routineEntity -> new RoutineDomain(routineEntity.id, routineEntity.name, routineEntity.detail, routineEntity.dateCreated, routineEntity.averageRating, routineEntity.isPublic, routineEntity.difficulty, routineEntity.creator, routineEntity.category))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routine -> new RoutineEntity(routine.getId(), routine.getName(), routine.getDetail(), routine.getDateCreated(), routine.getAverageRating(), routine.getIsPublic(), routine.getDifficulty(), routine.getCreator(), routine.getCategory()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routine -> new RoutineDomain(routine.getId(), routine.getName(), routine.getDetail(), routine.getDateCreated(), routine.getAverageRating(), routine.getIsPublic(), routine.getDifficulty(), routine.getCreator(), routine.getCategory()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<RoutineEntity> entities) {
                database.routineDao().delete(size, page * size);
                database.routineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Routine> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineEntity>> loadFromDb() {
                return database.routineDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return service.getAllRoutines(page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineDomain>> getRoutine(int routineId) {
        return new NetworkBoundResource<RoutineDomain, RoutineEntity, Routine>(executors, this::mapRoutineEntityToDomain, this::mapRoutineToEntity, this::mapRoutineToDomain) {

            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Routine model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return service.getRoutineById(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineDomain>> addRoutine(String name, String detail, Boolean isPublic,String difficulty, CategoryOrSport category) {

        return new NetworkBoundResource<RoutineDomain, RoutineEntity, Routine>(executors, this::mapRoutineEntityToDomain, this::mapRoutineToEntity, this::mapRoutineToDomain) {
            int routineId = 0;

            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                routineId = entity.id;
                database.routineDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Routine model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                if (routineId == 0)
                    return AbsentLiveData.create();
                else
                    return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return service.createRoutine(new RoutineCredentials(name,detail,isPublic,difficulty,category));
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineDomain>> modifyRoutine(RoutineDomain routine) {
        return new NetworkBoundResource<RoutineDomain, RoutineEntity, Routine>(executors, this::mapRoutineEntityToDomain, this::mapRoutineToEntity, this::mapRoutineToDomain) {

            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Routine model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().findById(routine.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                Routine model = mapRoutineDomainToModel(routine);
                return service.updateRoutine(model.getId(), model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteRoutine(RoutineDomain routine) {
        return new NetworkBoundResource<Void, RoutineEntity, Void>(executors,
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
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().delete(routine.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().findById(routine.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.deleteRoutine(routine.getId());
            }
        }.asLiveData();
    }
}
