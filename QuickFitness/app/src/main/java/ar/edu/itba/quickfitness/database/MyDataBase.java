package ar.edu.itba.quickfitness.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.database.dao.CategoryDao;
import ar.edu.itba.quickfitness.database.dao.CycleDao;
import ar.edu.itba.quickfitness.database.dao.ExerciseDao;
import ar.edu.itba.quickfitness.database.dao.RoutineDao;
import ar.edu.itba.quickfitness.database.dao.UserDao;
import ar.edu.itba.quickfitness.database.entity.CategoryEntity;
import ar.edu.itba.quickfitness.database.entity.CycleEntity;
import ar.edu.itba.quickfitness.database.entity.ExerciseEntity;
import ar.edu.itba.quickfitness.database.entity.RoutineEntity;
import ar.edu.itba.quickfitness.database.entity.UserEntity;

@Database(entities = {RoutineEntity.class, UserEntity.class,
        CycleEntity.class, CategoryEntity.class, ExerciseEntity.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class MyDataBase extends RoomDatabase {

    abstract public RoutineDao routineDao();
    abstract public UserDao userDao();
    abstract public CycleDao cycleDao();
    abstract public CategoryDao categoryDao();
    abstract public ExerciseDao exerciseDao();
}