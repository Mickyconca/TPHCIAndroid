package ar.edu.itba.quickfitness.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.itba.quickfitness.database.entity.CategoryEntity;

@Dao
public abstract class CategoryDao {

    @Query("SELECT * FROM Category")
    public abstract LiveData<List<CategoryEntity>> findAll();

    @Query("SELECT * FROM Category LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<CategoryEntity>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CategoryEntity... sport);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<CategoryEntity> sports);

    @Update
    public abstract void update(CategoryEntity sport);

    @Delete
    public abstract void delete(CategoryEntity sport);

    @Query("DELETE FROM Category WHERE id = :id")
    public abstract void delete(int id);

    @Query("DELETE FROM Category WHERE id IN (SELECT id FROM Category LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Category")
    public abstract void deleteAll();

    @Query("SELECT * FROM Category WHERE id = :id")
    public abstract LiveData<CategoryEntity> findById(int id);
}