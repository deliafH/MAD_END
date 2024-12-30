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
import com.example.myapplication.AddUseActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateDeleteMedicineActivity;
import com.example.myapplication.UpdateDeleteUseActivity;
import com.example.myapplication.adapter.UseRecycleViewAdapter;
import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentUses extends Fragment implements UseRecycleViewAdapter.UseListener {
    private UseRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new UseRecycleViewAdapter();
        db = SQLiteHelper.getInstance(getContext());
        List<Use> list = db.getUses();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setUseListener(this);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUseActivity.class);
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
    public void OnUseClick(View view, int position) {
        Use use = adapter.getItem(position);

        Intent intent = new Intent(getActivity(), UpdateDeleteUseActivity.class);
        intent.putExtra("use", use);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Use> list = db.getUses();
        adapter.setList(list);
    }
}
