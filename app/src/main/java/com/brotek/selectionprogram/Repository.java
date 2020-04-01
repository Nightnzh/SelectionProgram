package com.brotek.selectionprogram;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.brotek.selectionprogram.db.SelectionRoomDatabase;
import com.brotek.selectionprogram.model.Selection;
import com.brotek.selectionprogram.model.SelectionDao;

import java.util.List;

public class Repository {
    private SelectionDao selectionDao;
    private LiveData<List<Selection>> allSelectionLiveData;

    public Repository(Application application){
        SelectionRoomDatabase selectionRoomDatabase = SelectionRoomDatabase.getDatabase(application);
        selectionDao = selectionRoomDatabase.selectionDao();
        allSelectionLiveData = selectionDao.getAllItem();
    }

    public LiveData<List<Selection>> getAllSelection(){
        return allSelectionLiveData;
    }

    public void insert(Selection selection){
        SelectionRoomDatabase.databaseWriteExecutor.execute(() -> {
            selectionDao.insertItem(selection);
        });
    }

    public void insertAllItem(List<Selection> selectionList){
        SelectionRoomDatabase.databaseWriteExecutor.execute(() -> {
            selectionDao.insertAllItem(selectionList);
        });
    }
}
