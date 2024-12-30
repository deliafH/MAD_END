package com.example.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UpdateDeleteMedicineActivity;
import com.example.myapplication.adapter.RecycleViewAdapter;
import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment implements RecycleViewAdapter.MedicineListener, View.OnClickListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private Button btSearch;
    private SQLiteHelper db;
    private Spinner spType, spUse;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        db = SQLiteHelper.getInstance(getContext());
        List<Medicine> list = db.getMedicines();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setMedicineListener(this);

        spUse = view.findViewById(R.id.spUse);
        spType = view.findViewById(R.id.spType);
        btSearch = view.findViewById(R.id.btnSearch);
        List<String> types = new ArrayList<>();
        types.add("");
        for(int i = 0; i < getResources().getStringArray(R.array.medicine_type).length; i++){
            types.add(getResources().getStringArray(R.array.medicine_type)[i]);
        }
        spType.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item, R.id.tvName,
                types));
        List<Use> uses = db.getUses();
        List<String> useNames = new ArrayList<>();
        useNames.add("");
        for (Use use : uses) {
            useNames.add(use.getName());
        }
        spUse.setAdapter(new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, R.id.tvName, useNames));

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void Search(){
        String use = spUse.getSelectedItem().toString();
        String type = spType.getSelectedItem().toString();
        List<Medicine> medicines = db.searchMedicine(use, type);
        adapter.setList(medicines);
    }

    @Override
    public void OnMedicineClick(View view, int position) {
        Medicine medicine = adapter.getItem(position);

        Intent intent = new Intent(getActivity(), UpdateDeleteMedicineActivity.class);
        intent.putExtra("medicine", medicine);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Medicine> list = db.getMedicines();
        adapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        if(v == btSearch){
            String use = spUse.getSelectedItem().toString();
            String type = spType.getSelectedItem().toString();
            List<Medicine> medicines = db.searchMedicine(use, type);
            adapter.setList(medicines);
        }
    }
}
