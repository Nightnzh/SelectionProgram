package com.brotek.selectionprogram.db;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.brotek.selectionprogram.model.Selection;
import com.brotek.selectionprogram.model.SelectionDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Selection.class} , version = 1,exportSchema = false)
public abstract class SelectionRoomDatabase extends RoomDatabase {

    //Dao
    public abstract SelectionDao selectionDao();

    private static volatile SelectionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("SelectionRoomDatabase:","OPEN");
        }
    };

    public static SelectionRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SelectionRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,SelectionRoomDatabase.class,"SelectionDatabase")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
