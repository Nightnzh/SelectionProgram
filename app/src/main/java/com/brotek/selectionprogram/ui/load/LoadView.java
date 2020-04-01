package com.brotek.selectionprogram.ui.load;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brotek.selectionprogram.R;

public class LoadView {

    private static AlertDialog mAlertDialog;

    public static void showLoadView(Context context){
        if(mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context).create();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.progess_layout,null);
        mAlertDialog.setView(view);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    public static void showLoadView(Context context,String message){
        if(mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context).create();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.progess_layout,null);
        TextView textView_loading = view.findViewById(R.id.textView_load);
        textView_loading.setText(message);
        mAlertDialog.setView(view);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    public static void dismiss() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

}
