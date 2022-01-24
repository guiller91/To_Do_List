package com.example.mistareas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ControladorDB extends SQLiteOpenHelper {
    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.mistareas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addTarea(String tarea){

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", tarea);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("TAREAS", null, registro);

        db.close();
    }
    public String[] obtenerTareas(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM TAREAS",null);
        int regs = cursor.getCount();
        if(regs ==0){
            db.close();
            return null;
        }else{
           String[] tareas = new String[regs];
            cursor.moveToFirst();
            for(int i=0;i<regs;i++){
                tareas[i] = cursor.getString(1);
                cursor.moveToNext();
            }
            db.close();
            return tareas;
        }
    }

    public int numeroRegistros(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM TAREAS",null);
        return cursor.getCount();
    }

    public void borrarTarea(String tarea){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "NOMBRE=?",new String[]{tarea});
        db.close();
    }
}
