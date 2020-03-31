package com.brotek.selectionprogram.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brotek.selectionprogram.Repository;
import com.brotek.selectionprogram.model.Selection;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Selection>> allSelectionLiveData;

    private MutableLiveData<String> mText;

    public HomeViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allSelectionLiveData = repository.getAllSelection();
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<List<Selection>> getAllSelectionLiveData() {
        return allSelectionLiveData;
    }

    public void insert(Selection selection){
        repository.insert(selection);
    }

    public LiveData<String> getText() {
        return mText;
    }
}