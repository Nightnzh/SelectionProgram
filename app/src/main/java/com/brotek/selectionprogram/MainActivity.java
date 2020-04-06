package com.brotek.selectionprogram;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import com.boardtek.appcenter.AppCenter;
import com.boardtek.appcenter.NetworkInformation;
import com.brotek.selectionprogram.model.Selection;
import com.brotek.selectionprogram.model.SelectionProgram;
import com.brotek.selectionprogram.task.TaskManager;
import com.brotek.selectionprogram.ui.load.LoadView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.internal.$Gson$Types;

import androidx.annotation.RequiresApi;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Repository repository;
    private String TAG = MainActivity.class.getSimpleName();
    private String ip = Constant.IP;
    private NavController navController;
    private TaskManager taskManager;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loadingDialog
        LoadView.showLoadView(this);

        taskManager = new TaskManager(getApplication());

        setContentView(R.layout.activity_main);

        repository = new Repository(getApplication());
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        //floatingActionButton
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(this);
        //drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //TODO: fragment change
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_test)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //SharedPreferences setting
        getSharedPreferences(Constant.SETTING,MODE_PRIVATE)
                .edit()
                .putBoolean(Constant.IS_APP_FIRST_OPEN,false)
                .putBoolean(Constant.IS_TEST_MODE,false)
                .putBoolean(Constant.IS_ACTION_OPEN,false)
                .apply();

        //switch
        SwitchMaterial switchMaterialTest = (SwitchMaterial) navigationView.getMenu().findItem(R.id.menu_item_test_mode).getActionView();
//        switchMaterialTest.setChecked(true);
        //TestMode change
        switchMaterialTest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
//                getSharedPreferences(Constant.SETTING,MODE_PRIVATE)
//                        .edit()
//                        .putBoolean(Constant.IS_TEST_MODE,true)
//                        .apply();
                ip = Constant.TEST_IP;
            } else {
//                getSharedPreferences(Constant.SETTING,MODE_PRIVATE)
//                        .edit()
//                        .putBoolean(Constant.IS_TEST_MODE,false)
//                        .apply();
                ip = Constant.IP;
            }
            Log.d(TAG,"IP change to " + ip);
        });

        //TODO:
        navigationView.setNavigationItemSelectedListener(item -> {
            Log.d(TAG, item.toString());
            if(item.getItemId() == R.id.menu_item_test_mode){
                switchMaterialTest.setChecked(!switchMaterialTest.isChecked());
                return true;
            }
            if(navController.getCurrentDestination().getId() != item.getItemId())
                if(item.getItemId() == R.id.nav_test){
                    navController.navigate(R.id.action_nav_home_to_nav_test);
                    //drawer.closeDrawer(Gravity.START,true);
                    return true;
                } else if(item.getItemId() == R.id.nav_home) {
                    navController.navigate(R.id.action_nav_test_to_nav_home);
                    //drawer.closeDrawer(GravityCompat.START,false);
                    return true;
                }
            return true;
        });

        //company aar
        NetworkInformation.init(this);
        AppCenter.init(this);
        innitHeader(navigationView);
        //---------------------------------------

        LoadView.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

//        if (v.getId() == R.id.fab) {
//            View view = LayoutInflater.from(this).inflate(R.layout.selection_num, null);
//            new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
//                    .setTitle("Selection ID")
//                    .setIcon(R.drawable.ic_investigation_1)
//                    .setView(view)
//                    .setPositiveButton("OK", (dialog, which) -> {
//                        EditText editText = view.findViewById(R.id.editText_selection_num);
//                        task(Constant.HTTP+ip+Constant.URL_SINGLE_ITEM,editText.getText());
//                    })
//                    .show();
//        }

        if(v.getId() == R.id.menu_item_test_mode){
            Log.d(TAG, String.valueOf(v.getId()));
        }
    }

    void innitHeader(NavigationView navigationView){
        //navigationView headerView
        View headerView = navigationView.getHeaderView(0);
        TextView textView_appName =  (TextView) headerView.findViewById(R.id.textView_app_name);
        TextView textView_user = headerView.findViewById(R.id.textView_user_name);
        TextView textView_mobile = headerView.findViewById(R.id.textView_moblie);
        TextView textView_NowTime = headerView.findViewById(R.id.textView_now_time);
        TextView textView_WiFi = headerView.findViewById(R.id.textView_Wifi);
        TextView textView_testState = headerView.findViewById(R.id.textView_test_state);
        textView_appName.setText("壓機程式自動選用");
        textView_user.setText(AppCenter.uName + ":" + AppCenter.uId);
        textView_mobile.setText("手機編號 :"+AppCenter.mobileSn);
        AppCenter.addTimerPerSecondListener(() -> {
            textView_NowTime.setText("系統時間:" + AppCenter.getSystemTime());
        });
        textView_WiFi.setText(NetworkInformation.getWifiRetek());
        textView_testState.setText(NetworkInformation.IP);
        //navigationView contentView
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.update){
            taskManager.getAllData(Constant.HTTP+ip+Constant.URL_ALL_ITEM);
//            getAllData(Constant.HTTP+ip+Constant.URL_ALL_ITEM);
        }
        return false;
    }

//    @SuppressLint("StaticFieldLeak")
//    void task(String url, Editable num){
//        new AsyncTask<Void,Void,Void>(){
//            @Override
//            protected Void doInBackground(Void... voids) {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                FormBody formBody = new FormBody.Builder()
//                        .add(Constant.POST_PROGRAMID,num.toString())
//                        .build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(formBody)
//                        .build();
//                try {
//                    Log.d(TAG,"POST_URL:"+ url + Constant.POST_PROGRAMID+num.toString());
//                    Response response = okHttpClient.newCall(request).execute();
//                    assert response.body() != null;
//                    Log.d(TAG, response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                LoadView.showLoadView(getApplicationContext());
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                LoadView.dismiss();
//            }
//
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                super.onProgressUpdate(values);
//            }
//        }.execute();
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    void getAllData(String url){
//        new AsyncTask<Void,Void,Void>(){
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            protected Void doInBackground(Void... voids) {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .get()
//                        .build();
//                try {
//                    //reposeon
//                    Response response = okHttpClient.newCall(request).execute();
//                    //gson
//                    Gson gson = new Gson();
//                    Log.d(TAG,"GET_URL:"+ url);
//                    assert response.body() != null;
//                    String json = response.body().string();
//                    SelectionProgram selectionProgram = gson.fromJson(json, SelectionProgram.class);
//                    //insertData
//                    selectionProgram.forEach(selectionProgramItem -> {
//                        repository.insert(new Selection(
//                                selectionProgramItem.getProgramId(),
//                                selectionProgramItem.getHour(),
//                                selectionProgramItem.isAutoAddVersion(),
//                                selectionProgramItem.isPause(),
//                                selectionProgramItem.getMinute(),
//                                selectionProgramItem.getRemark(),
//                                selectionProgramItem.getSetDate(),
//                                selectionProgramItem.getSetName(),
//                                selectionProgramItem.getVendorTitle(),
//                                selectionProgramItem.getData_pp().toString(),
//                                selectionProgramItem.getData_content().toString(),
//                                AppCenter.getSystemTime()
//                        ));
//                    });
//
//                    Log.d(TAG,selectionProgram.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                LoadView.showLoadView(getApplicationContext(),"Get All Data...");
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                LoadView.dismiss();
//            }
//
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                super.onProgressUpdate(values);
//            }
//        }.execute();
//    }
}

