package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spUse, spType;
    public EditText etName, etPrice;
    public Button btAdd, btCancel;
    SQLiteHelper db = SQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        spUse = findViewById(R.id.spUse);
        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        btAdd = findViewById(R.id.btnAdd);
        btCancel = findViewById(R.id.btnCancel);

        spType.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tvName,
                getResources().getStringArray(R.array.medicine_type)));
        List<Use> uses = db.getUses();
        List<String> useNames = new ArrayList<>();
        for (Use use : uses) {
            useNames.add(use.getName());
        }
        spUse.setAdapter(new ArrayAdapter<>(
                this, R.layout.spinner_item, R.id.tvName, useNames ));

        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btAdd){
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();
            Use use = db.getUsesByName(spUse.getSelectedItem().toString());
            String type = spType.getSelectedItem().toString();
            if(!name.isEmpty() && price.matches("\\d+(\\.\\d+)?")){
                Medicine medicine = new Medicine(name, Float.valueOf(price), use, type);
                db.addMedicine(medicine);
                finish();
            }


        }else if( v == btCancel){
            finish();
        }
    }
}
