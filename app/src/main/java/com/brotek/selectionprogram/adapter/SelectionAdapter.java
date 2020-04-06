package com.brotek.selectionprogram.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brotek.selectionprogram.R;
import com.brotek.selectionprogram.model.Selection;

import java.util.List;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionViewHolder> {

    private List<Selection> list;

    public SelectionAdapter(List<Selection> list){
        this.list = list;
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,false);
        return new SelectionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position) {
        holder.bindTo(list.get(position));
    }
}

class SelectionViewHolder extends RecyclerView.ViewHolder {

    private TableLayout tableLayout;
    private TextView textViewProgramId;
    private TextView textViewIsPause;
    private TextView textViewVendorTitle;
    private TextView textViewSetDate;
    private TextView textViewSetName;
    private TextView textViewRemark;
    private TextView textViewIsAutoVersion;
    private TextView textViewDataPP;
    private TextView textViewDataContent;
    private TextView textViewUpdateDate;

    public SelectionViewHolder(@NonNull View itemView) {
        super(itemView);
        tableLayout = itemView.findViewById(R.id.tableLayout);
        textViewProgramId = tableLayout.findViewById(R.id.textView_Program_id);
        textViewIsPause = tableLayout.findViewById(R.id.textView_isPaused);
        textViewVendorTitle = tableLayout.findViewById(R.id.textView_vendor_tittle);
        textViewSetDate = tableLayout.findViewById(R.id.textView_set_date);
        textViewSetName = tableLayout.findViewById(R.id.textView_set_name);
        textViewRemark = tableLayout.findViewById(R.id.textView_remark);
        textViewIsAutoVersion = tableLayout.findViewById(R.id.textView_is_auto_version);
        textViewUpdateDate = tableLayout.findViewById(R.id.textView_update_date);
        textViewDataPP = tableLayout.findViewById(R.id.textView_data_pp);
        textViewDataContent = tableLayout.findViewById(R.id.textView_data_content);
    }

    @SuppressLint("SetTextI18n")
    public void bindTo(Selection selection){
        textViewProgramId.setText(R.string.ProgramID);
        textViewProgramId.append(selection.getProgramId());
        textViewIsPause.setText(R.string.ispause);
        textViewIsPause.append(selection.isPause());
        textViewVendorTitle.setText(R.string.vendorTitle);
        textViewVendorTitle.append(selection.getVendorTitle());
        textViewSetName.setText(R.string.setname);
        textViewSetName.append(selection.getSetName());
        textViewSetDate.setText(R.string.setdate);
        textViewSetDate.append(selection.getSetDate());
        textViewRemark.setText(R.string.remark);
        textViewRemark.append(selection.getRemark());
        textViewIsAutoVersion.setText(R.string.isautoaddversion);
        textViewIsAutoVersion.append(selection.isAutoAddVersion());
        textViewUpdateDate.setText(R.string.updatedate);
        textViewUpdateDate.append(selection.getUpdate_date());
        textViewDataPP.setText(R.string.datapp);
        textViewDataPP.append(selection.getData_pp());
        textViewDataContent.setText(R.string.datacontent);
        textViewDataContent.append(selection.getData_content());
    }
}