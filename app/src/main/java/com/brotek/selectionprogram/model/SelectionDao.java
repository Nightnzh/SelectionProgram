package com.brotek.selectionprogram.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SelectionDao {

    @Query("select * from selection")
    public LiveData<List<Selection>> getAllItem();

    @Query(value = "select * from selection where programId = :id")
    public LiveData<Selection> getItemFromId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItem(Selection selection);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllItem(List<Selection> list);

    @Query("delete from selection")
    void deleteAll();
}
