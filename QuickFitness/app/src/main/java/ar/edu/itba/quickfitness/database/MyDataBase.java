package ar.edu.itba.quickfitness.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import ar.edu.itba.quickfitness.api.model.Routine;
import ar.edu.itba.quickfitness.database.dao.RoutineDao;

@Database(entities = {Routine.class }, exportSchema = false, version = 1)
public abstract class MyDataBase extends RoomDatabase {

    abstract public RoutineDao routineDao();
}