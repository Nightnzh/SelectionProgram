package com.brotek.selectionprogram;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.boardtek.appcenter.AppCenter;
import com.boardtek.appcenter.NetworkInformation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Repository repository;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //company aar
        NetworkInformation.init(this);
        AppCenter.init(this);
        innitHeader(navigationView);
        repository = new Repository(getApplication());
        //---------------------------------------
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
        if (v.getId() == R.id.fab) {
            View view = LayoutInflater.from(this).inflate(R.layout.selection_num, null);
            new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                    .setTitle("Selection ID")
                    .setIcon(R.drawable.ic_investigation_1)
                    .setView(view)
                    .setPositiveButton("OK", (dialog, which) -> {
                        EditText editText = view.findViewById(R.id.editText_selection_num);
                        task("http://192.168.50.98/system_mvc/controller.php?s=dev,007459,600,laminationProgram,pp_program&action=mobile_programData",editText.getText());
                    })
                    .show();
//            task("http://192.168.50.98/system_mvc/controller.php?s=dev,007459,600,laminationProgram,pp_program&action=mobile_programData");
        }

    }

    void innitHeader(NavigationView navigationView){
        View headerView = navigationView.getHeaderView(0);
        TextView textView_appName =  (TextView) headerView.findViewById(R.id.textView_app_name);
        TextView textView_user = headerView.findViewById(R.id.textView_user_name);
        TextView textView_mobile = headerView.findViewById(R.id.textView_moblie);
        TextView textView_NowTime = headerView.findViewById(R.id.textView_now_time);
        TextView textView_WiFi = headerView.findViewById(R.id.textView_Wifi);
        TextView textView_testState = headerView.findViewById(R.id.textView_test_state);
        textView_appName.setText(R.string.app_name);
        textView_user.setText(AppCenter.uName + ":" + AppCenter.uId);
        textView_mobile.setText("手機編號 :"+AppCenter.mobileSn);
        AppCenter.addTimerPerSecondListener(() -> {
            textView_NowTime.setText("系統時間:" + AppCenter.getSystemTime());
        });
        textView_WiFi.setText(NetworkInformation.getWifiRetek());
        textView_testState.setText(NetworkInformation.IP);
    }


    @SuppressLint("StaticFieldLeak")
    void task(String url, Editable num){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("programId",num.toString())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    Log.d("response",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

        }.execute();


    }
}

