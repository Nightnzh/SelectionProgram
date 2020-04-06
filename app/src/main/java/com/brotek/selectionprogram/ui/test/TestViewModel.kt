package com.brotek.selectionprogram.ui.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brotek.selectionprogram.db.SelectionRoomDatabase
import com.brotek.selectionprogram.model.Selection

class TestViewModel(application: Application) : AndroidViewModel(application) {

    val  allSelections : LiveData<List<Selection>>

    init {
        val selectionRoomDatabase = SelectionRoomDatabase.getDatabase(application);
        val selectionDao = selectionRoomDatabase.selectionDao();
        allSelections = selectionDao.allItem
    }
}
