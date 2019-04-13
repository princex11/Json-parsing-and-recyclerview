package com.example.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{


    Context context;
    ArrayList<DataModel> dataModels;

    public DataAdapter(Context context, ArrayList<DataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View result = LayoutInflater.from(context).inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(result);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
            holder.txtName.setText(""+ dataModels.get(position).getName());
            holder.txtColor.setText(""+ dataModels.get(position).getColor());
            holder.txtId.setText(""+ dataModels.get(position).getId());
            holder.txtYear.setText(""+ dataModels.get(position).getYear());
            holder.txtPantoneValue.setText(""+ dataModels.get(position).getPantoneValue());
    }

    @Override
    public int getItemCount() {
        return dataModels ==null?0: dataModels.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.txt_name)
        TextView txtName;

        @BindView(R.id.txt_id)
        TextView txtId;

        @BindView(R.id.txt_color)
        TextView txtColor;

        @BindView(R.id.txt_pantonevalue)
        TextView txtPantoneValue;

        @BindView(R.id.txt_year)
        TextView txtYear;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
