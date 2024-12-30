package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class UseRecycleViewAdapter extends RecyclerView.Adapter<UseRecycleViewAdapter.UseViewHolder>{
    private List<Use> list;

    private UseRecycleViewAdapter.UseListener useListener;

    public UseRecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setUseListener(UseRecycleViewAdapter.UseListener useListener){
        this.useListener = useListener;
    }

    public void setList(List<Use> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Use getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public UseRecycleViewAdapter.UseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uses_item, parent, false);
        return new UseRecycleViewAdapter.UseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UseRecycleViewAdapter.UseViewHolder holder, int position) {
        Use use = list.get(position);
        holder.name.setText(use.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class UseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, type, price;

        public UseViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(useListener != null){
                useListener.OnUseClick(v, getAdapterPosition());
            }
        }
    }

    public interface UseListener{
        void OnUseClick(View view, int position);

    }
}
