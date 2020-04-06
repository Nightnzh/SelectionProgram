package com.brotek.selectionprogram.task;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.boardtek.appcenter.AppCenter;
import com.brotek.selectionprogram.Constant;
import com.brotek.selectionprogram.Repository;
import com.brotek.selectionprogram.model.Selection;
import com.brotek.selectionprogram.model.SelectionProgram;
import com.brotek.selectionprogram.ui.load.LoadView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskManager {

    private Context context;
    private String TAG = TaskManager.class.getSimpleName();
    private Repository repository;

    public TaskManager(Application application){
        this.context = application;
        repository = new Repository(application);
    }

    @SuppressLint("StaticFieldLeak")
    public void task(String url, Editable num){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add(Constant.POST_PROGRAMID,num.toString())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                try {
                    Log.d(TAG,"POST_URL:"+ url + Constant.POST_PROGRAMID+num.toString());
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    Log.d(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                LoadView.showLoadView(context);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                LoadView.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void getAllData(String url){
        new AsyncTask<Void,Void,Void>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try {
                    //reposeon
                    Response response = okHttpClient.newCall(request).execute();
                    //gson
                    Gson gson = new Gson();
                    Log.d(TAG,"GET_URL:"+ url);
                    assert response.body() != null;
                    String json = response.body().string();
                    SelectionProgram selectionProgram = gson.fromJson(json, SelectionProgram.class);
                    //insertData
                    selectionProgram.forEach(selectionProgramItem -> {
                        repository.insert(new Selection(
                                selectionProgramItem.getProgramId(),
                                selectionProgramItem.getHour(),
                                selectionProgramItem.isAutoAddVersion(),
                                selectionProgramItem.isPause(),
                                selectionProgramItem.getMinute(),
                                selectionProgramItem.getRemark(),
                                selectionProgramItem.getSetDate(),
                                selectionProgramItem.getSetName(),
                                selectionProgramItem.getVendorTitle(),
                                selectionProgramItem.getData_pp().toString(),
                                selectionProgramItem.getData_content().toString(),
                                AppCenter.getSystemTime()
                        ));
                    });

                    Log.d(TAG,selectionProgram.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                LoadView.showLoadView(context,"Get All Data...");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                LoadView.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }
}
