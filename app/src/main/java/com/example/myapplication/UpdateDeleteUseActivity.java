package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.dal.SQLiteHelper;
import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteUseActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText etName, etDesciption;
    public Button btUpdate, btRemove, btBack;
    SQLiteHelper db = SQLiteHelper.getInstance(this);
    private Use use;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_use);

        etName = findViewById(R.id.etName);
        etDesciption = findViewById(R.id.etDescription);
        btUpdate = findViewById(R.id.btnUpdate);
        btRemove = findViewById(R.id.btnRemove);
        btBack = findViewById(R.id.btnBack);


        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        btBack.setOnClickListener(this);

        Intent intent = getIntent();
        use = (Use) intent.getSerializableExtra("use");

        etName.setText(use.getName());
        etDesciption .setText(use.getDescription());
    }

    @Override
    public void onClick(View v) {
        if(v == btUpdate){
            String name = etName.getText().toString();
            String description = etDesciption.getText().toString();
            if(!name.isEmpty() && !description.isEmpty()){
                Use use = new Use(this.use.getId(), name, description);
                db.editUse(use);
                finish();
            }


        }else if( v == btRemove){
            int id = use.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("REMOVE NOTIFICATION");
            builder.setMessage("Are you sure you want to delete " + etName.getText() +"?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteUse(id);
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
