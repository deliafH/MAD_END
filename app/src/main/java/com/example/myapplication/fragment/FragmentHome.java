package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddMedicineActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateDeleteMedicineActivity;
import com.example.myapplication.adapter.RecycleViewAdapter;
import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.MedicineListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMedicineActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {  // Check for the correct request code
            getActivity().recreate();
        }
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
}
