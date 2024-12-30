package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Medicine;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MedicineViewHolder>{

    private List<Medicine> list;

    private MedicineListener medicineListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setMedicineListener(MedicineListener medicineListener){
        this.medicineListener = medicineListener;
    }

    public void setList(List<Medicine> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Medicine getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = list.get(position);
        holder.name.setText(medicine.getName());
        holder.type.setText(medicine.getType());
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        String formattedPrice = formatter.format(medicine.getPrice()) + " đ";
        holder.price.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MedicineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, type, price;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            type = itemView.findViewById(R.id.tvType);
            price = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(medicineListener != null){
                medicineListener.OnMedicineClick(v, getAdapterPosition());
            }
        }
    }

    public interface MedicineListener{
        void OnMedicineClick(View view, int position);

    }
}
