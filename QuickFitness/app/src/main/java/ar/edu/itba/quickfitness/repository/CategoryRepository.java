package ar.edu.itba.quickfitness.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.quickfitness.api.ApiResponse;
import ar.edu.itba.quickfitness.api.model.ApiCategoryService;
import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.CategoryOrSportCredentials;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.database.entity.CategoryEntity;
import ar.edu.itba.quickfitness.domain.CategoryDomain;
import ar.edu.itba.quickfitness.repository.AppExecutors;
import ar.edu.itba.quickfitness.repository.NetworkBoundResource;
import ar.edu.itba.quickfitness.repository.RateLimiter;
import ar.edu.itba.quickfitness.vo.AbsentLiveData;
import ar.edu.itba.quickfitness.vo.Resource;

import static java.util.stream.Collectors.toList;

public class CategoryRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiCategoryService service;
    private MyDataBase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public CategoryRepository(AppExecutors executors, ApiCategoryService service, MyDataBase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private CategoryDomain mapCategoryEntityToDomain(CategoryEntity entity) {
        return new CategoryDomain(entity.id, entity.name, entity.detail);
    }

    private CategoryEntity mapCategoryOrSportToEntity(CategoryOrSport model) {
        return new CategoryEntity(model.getId(), model.getName(), model.getDetail());
    }

    private CategoryDomain mapCategoryOrSportToDomain(CategoryOrSport model) {
        return new CategoryDomain(model.getId(), model.getName(), model.getDetail());
    }

    private CategoryOrSport mapCategoryDomainToModel(CategoryDomain domain) {
        CategoryOrSport model = new CategoryOrSport();
        model.setId(domain.getId());
        model.setName(domain.getName());
        model.setDetail(domain.getDetail());
        return model;
    }

    public LiveData<Resource<List<CategoryDomain>>> getCategories() {

        return new NetworkBoundResource<List<CategoryDomain>, List<CategoryEntity>, PagedList<CategoryOrSport>>(executors,
                entities -> {
                    return entities.stream()
                            .map(categoryEntity -> new CategoryDomain(categoryEntity.id, categoryEntity.name, categoryEntity.detail))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(categoryModel -> new CategoryEntity(categoryModel.getId(), categoryModel.getName(), categoryModel.getDetail()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(categoryModel -> new CategoryDomain(categoryModel.getId(), categoryModel.getName(), categoryModel.getDetail()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<CategoryEntity> entities) {
                database.categoryDao().deleteAll();
                database.categoryDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryEntity> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<CategoryOrSport> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryEntity>> loadFromDb() {
                return database.categoryDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CategoryOrSport>>> createCall() {
                return service.getCategories();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<CategoryDomain>>> getCategories(int page, int size) {

        return new NetworkBoundResource<List<CategoryDomain>, List<CategoryEntity>, PagedList<CategoryOrSport>>(executors,
                entities -> {
                    return entities.stream()
                            .map(categoryEntity -> new CategoryDomain(categoryEntity.id, categoryEntity.name, categoryEntity.detail))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(categoryModel -> new CategoryEntity(categoryModel.getId(), categoryModel.getName(), categoryModel.getDetail()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(categoryModel -> new CategoryDomain(categoryModel.getId(), categoryModel.getName(), categoryModel.getDetail()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<CategoryEntity> entities) {
                database.categoryDao().delete(size, page * size);
                database.categoryDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<CategoryOrSport> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryEntity>> loadFromDb() {
                return database.categoryDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CategoryOrSport>>> createCall() {
                return service.getCategories(page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CategoryDomain>> getCategory(int categoryId) {
        return new NetworkBoundResource<CategoryDomain, CategoryEntity, CategoryOrSport>(executors, this::mapCategoryEntityToDomain, this::mapCategoryOrSportToEntity, this::mapCategoryOrSportToDomain) {

            @Override
            protected void saveCallResult(@NonNull CategoryEntity entity) {
                database.categoryDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CategoryEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable CategoryOrSport model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CategoryEntity> loadFromDb() {
                return database.categoryDao().findById(categoryId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryOrSport>> createCall() {
                return service.getCategory(categoryId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CategoryDomain>> addCategory(String name, String detail) {

        return new NetworkBoundResource<CategoryDomain, CategoryEntity, CategoryOrSport>(executors, this::mapCategoryEntityToDomain, this::mapCategoryOrSportToEntity, this::mapCategoryOrSportToDomain) {
            int categoryId = 0;

            @Override
            protected void saveCallResult(@NonNull CategoryEntity entity) {
                categoryId = entity.id;
                database.categoryDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CategoryEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable CategoryOrSport model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CategoryEntity> loadFromDb() {
                if (categoryId == 0)
                    return AbsentLiveData.create();
                else
                    return database.categoryDao().findById(categoryId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryOrSport>> createCall() {
                return service.addCategory(new CategoryOrSportCredentials(name, detail));
            }
        }.asLiveData();
    }

    public LiveData<Resource<CategoryDomain>> modifyCategory(CategoryDomain category) {
        return new NetworkBoundResource<CategoryDomain, CategoryEntity, CategoryOrSport>(executors, this::mapCategoryEntityToDomain, this::mapCategoryOrSportToEntity, this::mapCategoryOrSportToDomain) {

            @Override
            protected void saveCallResult(@NonNull CategoryEntity entity) {
                database.categoryDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CategoryEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable CategoryOrSport model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CategoryEntity> loadFromDb() {
                return database.categoryDao().findById(category.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryOrSport>> createCall() {
                CategoryOrSport model = mapCategoryDomainToModel(category);
                return service.modifyCategory(model.getId(), model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteCategory(CategoryDomain category) {
        return new NetworkBoundResource<Void, CategoryEntity, Void>(executors,
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
            protected void saveCallResult(@NonNull CategoryEntity entity) {
                database.categoryDao().delete(category.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable CategoryEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CategoryEntity> loadFromDb() {
                return database.categoryDao().findById(category.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.deleteCategory(category.getId());
            }
        }.asLiveData();
    }
}
