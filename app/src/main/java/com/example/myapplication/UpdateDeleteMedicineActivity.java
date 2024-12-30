package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteMedicineActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spUse, spType;
    public EditText etName, etPrice;
    public Button btUpdate, btRemove, btBack;
    SQLiteHelper db = SQLiteHelper.getInstance(this);
    private Medicine medicine;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_medicine);

        spUse = findViewById(R.id.spUse);
        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        btUpdate = findViewById(R.id.btnUpdate);
        btRemove = findViewById(R.id.btnRemove);
        btBack = findViewById(R.id.btnBack);

        spType.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tvName,
                getResources().getStringArray(R.array.medicine_type)));
        List<Use> uses = db.getUses();
        List<String> useNames = new ArrayList<>();
        for (Use use : uses) {
            useNames.add(use.getName());
        }
        spUse.setAdapter(new ArrayAdapter<>(
                this, R.layout.spinner_item, R.id.tvName, useNames ));

        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        btBack.setOnClickListener(this);

        Intent intent = getIntent();
        medicine = (Medicine) intent.getSerializableExtra("medicine");

        etName.setText(medicine.getName());
        etPrice.setText(String.valueOf(medicine.getPrice()));
        for(int i = 0; i < spUse.getCount(); i++)
        {
            if(spUse.getItemAtPosition(i).equals(medicine.getUses().getName()))
            {
                spUse.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < spType.getCount(); i++)
        {
            if(spType.getItemAtPosition(i).equals(medicine.getUses().getName()))
            {
                spType.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btUpdate){
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();
            Use use = db.getUsesByName(spUse.getSelectedItem().toString());
            String type = spType.getSelectedItem().toString();
            if(!name.isEmpty() && price.matches("\\d+(\\.\\d+)?")){
                Medicine medicine = new Medicine(this.medicine.getId(), name, Float.valueOf(price), use, type);
                db.editMedicine(medicine);
                finish();
            }


        }else if( v == btRemove){
            int id = medicine.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("REMOVE NOTIFICATION");
            builder.setMessage("Are you sure you want to delete " + etName.getText() +"?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteMedicine(id);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(v == btBack){
            finish();
        }
    }
}
