package ar.edu.itba.quickfitness.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.itba.quickfitness.database.entity.ExerciseEntity;

@Dao
public abstract class ExerciseDao {

    @Query("SELECT * FROM Exercise")
    public abstract LiveData<List<ExerciseEntity>> findAll();

    @Query("SELECT * FROM Exercise LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<ExerciseEntity>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ExerciseEntity... user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ExerciseEntity> users);

    @Update
    public abstract void update(ExerciseEntity user);

    @Delete
    public abstract void delete(ExerciseEntity user);

    @Query("DELETE FROM Exercise WHERE id = :id")
    public abstract void delete(int id);

    @Query("DELETE FROM Exercise WHERE id IN (SELECT id FROM Exercise LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Exercise")
    public abstract void deleteAll();

    @Query("SELECT * FROM Exercise WHERE id = :id")
    public abstract LiveData<ExerciseEntity> findById(int id);
}
