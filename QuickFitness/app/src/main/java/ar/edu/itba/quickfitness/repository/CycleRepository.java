package ar.edu.itba.quickfitness.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.quickfitness.api.ApiCycleService;
import ar.edu.itba.quickfitness.api.ApiResponse;
import ar.edu.itba.quickfitness.api.model.Cycle;
import ar.edu.itba.quickfitness.api.model.CycleInfo;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.database.entity.CycleEntity;
import ar.edu.itba.quickfitness.domain.CycleDomain;
import ar.edu.itba.quickfitness.vo.AbsentLiveData;
import ar.edu.itba.quickfitness.vo.Resource;

import static java.util.stream.Collectors.toList;

public class CycleRepository {


    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiCycleService service;
    private MyDataBase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public CycleRepository(AppExecutors executors, ApiCycleService service, MyDataBase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private CycleDomain mapCycleEntityToDomain(CycleEntity entity) {
        return new CycleDomain(entity.id, entity.name, entity.detail, entity.type, entity.order, entity.repetitions);
    }

    private CycleEntity mapCycleToEntity(Cycle model) {
        return new CycleEntity(model.getId(), model.getName(), model.getDetail(), model.getType(), model.getOrder(), model.getRepetitions());
    }

    private CycleDomain mapCycleToDomain(Cycle model) {
        return new CycleDomain(model.getId(), model.getName(), model.getDetail(), model.getType(), model.getOrder(), model.getRepetitions());
    }

    private Cycle mapSportDomainToModel(CycleDomain domain) {
        Cycle model = new Cycle();
        model.setId(domain.getId());
        model.setName(domain.getName());
        model.setDetail(domain.getDetail());
        model.setType(domain.getType());
        model.setOrder(domain.getOrder());
        model.setRepetitions(domain.getRepetitions());

        return model;
    }

    public LiveData<Resource<List<CycleDomain>>> getCycles(int routineId) {

        return new NetworkBoundResource<List<CycleDomain>, List<CycleEntity>, PagedList<Cycle>>(executors,
                entities -> {
                    return entities.stream()
                            .map(cycleEntity -> new CycleDomain(cycleEntity.id, cycleEntity.name, cycleEntity.detail,cycleEntity.type, cycleEntity.order, cycleEntity.repetitions))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycle -> new CycleEntity(cycle.getId(), cycle.getName(), cycle.getDetail(), cycle.getType(), cycle.getOrder(), cycle.getRepetitions()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycle -> new CycleDomain(cycle.getId(), cycle.getName(), cycle.getDetail(),cycle.getType(), cycle.getOrder(), cycle.getRepetitions()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<CycleEntity> entities) {
                database.cycleDao().deleteAll();
                database.cycleDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CycleEntity> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Cycle> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CycleEntity>> loadFromDb() {
                return database.cycleDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return service.getAllCycles(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<CycleDomain>>> getCycles(int routineId, int page, int size) {

        return new NetworkBoundResource<List<CycleDomain>, List<CycleEntity>, PagedList<Cycle>>(executors,
                entities -> {
                    return entities.stream()
                            .map(cycleEntity -> new CycleDomain(cycleEntity.id, cycleEntity.name, cycleEntity.detail,cycleEntity.type, cycleEntity.order, cycleEntity.repetitions))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycle -> new CycleEntity(cycle.getId(), cycle.getName(), cycle.getDetail(), cycle.getType(), cycle.getOrder(), cycle.getRepetitions()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(cycle -> new CycleDomain(cycle.getId(), cycle.getName(), cycle.getDetail(),cycle.getType(), cycle.getOrder(), cycle.getRepetitions()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<CycleEntity> entities) {
                database.cycleDao().delete(size, page * size);
                database.cycleDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CycleEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<Cycle> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CycleEntity>> loadFromDb() {
                return database.cycleDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return service.getAllCycles(routineId, page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CycleDomain>> getCycle(int routineId, int cycleId) {
        return new NetworkBoundResource<CycleDomain, CycleEntity, Cycle>(executors, this::mapCycleEntityToDomain, this::mapCycleToEntity, this::mapCycleToDomain) {

            @Override
            protected void saveCallResult(@NonNull CycleEntity entity) {
                database.cycleDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CycleEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Cycle model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CycleEntity> loadFromDb() {
                return database.cycleDao().findById(cycleId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Cycle>> createCall() {
                return service.getCycleById(routineId, cycleId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CycleDomain>> addCycle(int routineId, String name, String detail, String type, Integer order, Integer repetitions) {

        return new NetworkBoundResource<CycleDomain, CycleEntity, Cycle>(executors, this::mapCycleEntityToDomain, this::mapCycleToEntity, this::mapCycleToDomain) {
            int sportId = 0;

            @Override
            protected void saveCallResult(@NonNull CycleEntity entity) {
                sportId = entity.id;
                database.cycleDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CycleEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Cycle model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CycleEntity> loadFromDb() {
                if (sportId == 0)
                    return AbsentLiveData.create();
                else
                    return database.cycleDao().findById(sportId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Cycle>> createCall() {
                return service.createCycle(routineId, new CycleInfo(name, detail, type, order, repetitions));
            }
        }.asLiveData();
    }

    public LiveData<Resource<CycleDomain>> modifyCycle(int routineId, int cycleId, CycleInfo cycleInfo) {
        return new NetworkBoundResource<CycleDomain, CycleEntity, Cycle>(executors, this::mapCycleEntityToDomain, this::mapCycleToEntity, this::mapCycleToDomain) {

            @Override
            protected void saveCallResult(@NonNull CycleEntity entity) {
                database.cycleDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CycleEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable Cycle model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CycleEntity> loadFromDb() {
                return database.cycleDao().findById(cycleId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Cycle>> createCall() {
                return service.updateCycle(routineId, cycleId,cycleInfo);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteCycle(int routineId, int cycleId) {
        return new NetworkBoundResource<Void, CycleEntity, Void>(executors,
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
            protected void saveCallResult(@NonNull CycleEntity entity) {
                database.cycleDao().delete(cycleId);
            }

            @Override
            protected boolean shouldFetch(@Nullable CycleEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CycleEntity> loadFromDb() {
                return database.cycleDao().findById(cycleId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.deleteCycle(routineId,cycleId);
            }
        }.asLiveData();
    }
}
