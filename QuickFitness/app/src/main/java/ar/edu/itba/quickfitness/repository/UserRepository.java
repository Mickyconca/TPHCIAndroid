package ar.edu.itba.quickfitness.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.quickfitness.api.ApiResponse;
import ar.edu.itba.quickfitness.api.ApiUserService;
import ar.edu.itba.quickfitness.api.model.LoginCredentials;
import ar.edu.itba.quickfitness.api.model.PagedList;
import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.api.model.Token;
import ar.edu.itba.quickfitness.api.model.UpdateUserCredentials;
import ar.edu.itba.quickfitness.api.model.User;
import ar.edu.itba.quickfitness.api.model.UserCredentials;
import ar.edu.itba.quickfitness.api.model.VerifyEmailCredentials;
import ar.edu.itba.quickfitness.database.MyDataBase;
import ar.edu.itba.quickfitness.database.entity.RoutineEntity;
import ar.edu.itba.quickfitness.database.entity.UserEntity;
import ar.edu.itba.quickfitness.domain.RoutineDomain;
import ar.edu.itba.quickfitness.domain.UserDomain;
import ar.edu.itba.quickfitness.vo.AbsentLiveData;
import ar.edu.itba.quickfitness.vo.Resource;

import static java.util.stream.Collectors.toList;

public class UserRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiUserService service;
    private MyDataBase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public UserRepository(AppExecutors executors, ApiUserService service, MyDataBase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private UserDomain mapUserEntityToDomain(UserEntity entity) {
        return new UserDomain(entity.id, entity.username, entity.fullName, entity.gender, entity.birthdate, entity.email, entity.phone, entity.avatarUrl, entity.dateCreated, entity.dateLastActive, entity.deleted, entity.verified);
    }

    private UserEntity mapUserToEntity(User model) {
        return new UserEntity(model.getId(), model.getUsername(), model.getFullName(), model.getGender(), model.getBirthdate(), model.getEmail(), model.getPhone(), model.getAvatarUrl(), model.getDateCreated(), model.getDateLastActive(), model.getDeleted(), model.getVerified());
    }

    private UserDomain mapUserToDomain(User model) {
        return new UserDomain(model.getId(), model.getUsername(), model.getFullName(), model.getGender(), model.getBirthdate(), model.getEmail(), model.getPhone(), model.getAvatarUrl(), model.getDateCreated(), model.getDateLastActive(), model.getDeleted(), model.getVerified());
    }

    private User mapUserDomainToModel(UserDomain domain) {
        User model = new User();
        model.setId(domain.getId());
        model.setUsername(domain.getUsername());
        model.setFullName(domain.getFullName());
        model.setGender(domain.getGender());
        model.setBirthdate(domain.getBirthdate());
        model.setEmail(domain.getEmail());
        model.setPhone(domain.getPhone());
        model.setAvatarUrl(domain.getAvatarUrl());
        model.setDateCreated(domain.getDateCreated());
        model.setDateLastActive(domain.getDateLastActive());
        model.setDeleted(domain.getDeleted());
        model.setVerified(domain.getVerified());
        return model;
    }

    public LiveData<Resource<String>> login(String username, String password) {

        return new NetworkBoundResource<String, Void, Token>(executors, null, null, model -> model.getToken()) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Token model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return service.login(new LoginCredentials(username, password));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> verifyEmail(String email, String code) {

        return new NetworkBoundResource<Void, Void, Void>
                (executors, null, null, null) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.verifyEmail(new VerifyEmailCredentials(email, code));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {

        return new NetworkBoundResource<Void, Void, Void>
                (executors, null, null, model -> null) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserDomain>> createUser(String username, String fullName, String password, String email) {
        return new NetworkBoundResource<UserDomain, UserEntity, User>(executors, this::mapUserEntityToDomain, this::mapUserToEntity, this::mapUserToDomain) {
            int userId = 0;

            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                userId = entity.id;
                database.userDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable User model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                if (userId == 0)
                    return AbsentLiveData.create();
                else
                    return database.userDao().findById(userId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return service.createUser(new UserCredentials(username, password, fullName, email));
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserDomain>> modifyUser(String fullName, String phone, String avatarUrl) {
        return new NetworkBoundResource<UserDomain, UserEntity, User>(executors, this::mapUserEntityToDomain, this::mapUserToEntity, this::mapUserToDomain) {

            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                database.userDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable User model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return service.updateCurrentUser(new UpdateUserCredentials(fullName, phone, avatarUrl));
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserDomain>> getCurrentUser() {
        return new NetworkBoundResource<UserDomain, UserEntity, User>(executors, this::mapUserEntityToDomain, this::mapUserToEntity, this::mapUserToDomain) {

            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                database.userDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable User model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return service.getCurrentUser();
            }
        }.asLiveData();
    }


    public LiveData<Resource<List<RoutineDomain>>> getCurrentUserRoutines() {

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
                return service.getCurrentUserRoutines();
            }
        }.asLiveData();
    }
}
