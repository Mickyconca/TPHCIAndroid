package ar.edu.itba.quickfitness.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.database.dao.RoutineDao;
import ar.edu.itba.quickfitness.database.entity.RoutineEntity;

@Database(entities = {RoutineEntity.class }, exportSchema = false, version = 1)
public abstract class MyDataBase extends RoomDatabase {

    abstract public RoutineDao routineDao();
}