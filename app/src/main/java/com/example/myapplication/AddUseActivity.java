package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Use;


public class AddUseActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText etName, etDescription;
    public Button btAdd, btCancel;
    SQLiteHelper db = SQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_use);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        btAdd = findViewById(R.id.btnAdd);
        btCancel = findViewById(R.id.btnCancel);

        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btAdd){
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            if(!name.isEmpty() && !description.isEmpty()){
                Use use = new Use(name, description);
                db.addUse(use);
                finish();
            }


        }else if( v == btCancel){
            finish();
        }
    }
}
