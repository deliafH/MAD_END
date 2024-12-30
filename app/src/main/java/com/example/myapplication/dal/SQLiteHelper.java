package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Medicine;
import com.example.myapplication.model.Use;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static SQLiteHelper instance;
    private static final String DATABASE_NAME ="Medicine.db";
    private static int DATABASE_VERSION = 1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsesTable = "CREATE TABLE USES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "DESCRIPTION TEXT)";

        String createMedicineTable = "CREATE TABLE MEDICINE (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRICE FLOAT," +
                "ID_USES INTEGER," +
                "TYPE TEXT," +
                "FOREIGN KEY(ID_USES) REFERENCES USES(ID))";

        db.execSQL(createUsesTable);
        db.execSQL(createMedicineTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Medicine> getMedicines() {
        List<Medicine> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM MEDICINE", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int priceIndex = cursor.getColumnIndex("PRICE");
                int idUsesIndex = cursor.getColumnIndex("ID_USES");
                int typeIndex = cursor.getColumnIndex("TYPE");

                if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 && idUsesIndex != -1 && typeIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    float price = cursor.getFloat(priceIndex);
                    int idUses = cursor.getInt(idUsesIndex);
                    String type = cursor.getString(typeIndex);
                    Use use = getUsesById(idUses);

                    Medicine medicine = new Medicine(id, name, price, use, type);
                    medicineList.add(medicine);
                } else {
                    Log.e("DatabaseHelper", "Một trong các cột không tồn tại trong bảng MEDICINE.");
                }
            }
            cursor.close();
        }

        return medicineList;
    }

    public List<Use> getUses() {
        List<Use> useList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USES", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int descriptionIndex = cursor.getColumnIndex("DESCRIPTION");

                if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String description = cursor.getString(descriptionIndex);

                    Use use = new Use(id, name, description);
                    useList.add(use);
                } else {
                    Log.e("DatabaseHelper", "Một trong các cột không tồn tại trong bảng USES.");
                }
            }
            cursor.close();
        }

        return useList;
    }

    public Use getUsesById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Use use = null;

        Cursor cursor = db.rawQuery("SELECT * FROM USES WHERE ID = ?", new String[]{String.valueOf(id)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int descriptionIndex = cursor.getColumnIndex("DESCRIPTION");

                if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1) {
                    int useId = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String description = cursor.getString(descriptionIndex);

                    use = new Use(useId, name, description);
                } else {
                    Log.e("DatabaseHelper", "Một trong các cột không tồn tại trong bảng USES.");
                }
            }
            cursor.close();
        }

        return use;
    }

    public Use getUsesByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Use use = null;

        Cursor cursor = db.rawQuery("SELECT * FROM USES WHERE NAME = ?", new String[]{name});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int descriptionIndex = cursor.getColumnIndex("DESCRIPTION");

                if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String useName = cursor.getString(nameIndex);
                    String description = cursor.getString(descriptionIndex);

                    use = new Use(id, useName, description);
                } else {
                    Log.e("DatabaseHelper", "Một trong các cột không tồn tại trong bảng USES.");
                }
            }
            cursor.close();
        }

        return use;
    }

    public Medicine getMedicineById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Medicine medicine = null;

        Cursor cursor = db.rawQuery("SELECT * FROM MEDICINE WHERE ID = ?", new String[]{String.valueOf(id)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int priceIndex = cursor.getColumnIndex("PRICE");
                int idUsesIndex = cursor.getColumnIndex("ID_USES");
                int typeIndex = cursor.getColumnIndex("TYPE");

                if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 && idUsesIndex != -1 && typeIndex != -1) {
                    int medicineId = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    float price = cursor.getFloat(priceIndex);
                    int idUses = cursor.getInt(idUsesIndex);
                    String type = cursor.getString(typeIndex);

                    Use use = getUsesById(idUses);
                    medicine = new Medicine(medicineId, name, price, use, type);
                } else {
                    Log.e("DatabaseHelper", "Một trong các cột không tồn tại trong bảng MEDICINE.");
                }
            }
            cursor.close();
        }

        return medicine;
    }

    public List<Medicine> searchMedicine(String uses, String type) {
        List<Medicine> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("SELECT * FROM MEDICINE WHERE 1=1");
        List<String> args = new ArrayList<>();
        if (uses != "") {
            query.append(" AND ID_USES = ?");
            args.add(String.valueOf(getUsesByName(uses).getId()));
        }

        if (type != "") {
            query.append(" AND TYPE = ?");
            args.add(type);
        }

        String[] selectionArgs = args.toArray(new String[0]);

        Cursor cursor = db.rawQuery(query.toString(), selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nameIndex = cursor.getColumnIndex("NAME");
                int priceIndex = cursor.getColumnIndex("PRICE");
                int idUsesIndex = cursor.getColumnIndex("ID_USES");
                int typeIndex = cursor.getColumnIndex("TYPE");

                if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 && idUsesIndex != -1 && typeIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    float price = cursor.getFloat(priceIndex);
                    int idUses = cursor.getInt(idUsesIndex);
                    String medicineType = cursor.getString(typeIndex);

                    Use useObj = getUsesById(idUses);

                    Medicine medicine = new Medicine(id, name, price, useObj, medicineType);
                    medicineList.add(medicine);
                }
            }
            cursor.close();
        }

        return medicineList;
    }

    public long addUse(Use use) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("NAME", use.getName());
        values.put("DESCRIPTION", use.getDescription());

        return db.insert("USES", null, values);
    }

    public long addMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("NAME", medicine.getName());
        values.put("PRICE", medicine.getPrice());
        values.put("ID_USES", medicine.getUses().getId());
        values.put("TYPE", medicine.getType());

        return db.insert("MEDICINE", null, values);
    }

    public void editUse(Use use) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", use.getName());
        values.put("DESCRIPTION", use.getDescription());

        db.update("USES", values, "ID = ?", new String[]{String.valueOf(use.getId())});
        db.close();
    }

    public void editMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", medicine.getName());
        values.put("PRICE", medicine.getPrice());
        values.put("ID_USES", medicine.getUses().getId());
        values.put("TYPE", medicine.getType());

        db.update("MEDICINE", values, "ID = ?", new String[]{String.valueOf(medicine.getId())});
        db.close();
    }

    public void deleteUse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("USES", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteMedicine(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("MEDICINE", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
