package com.example.mistareas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ControladorDB extends SQLiteOpenHelper {
    String consulta="CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT,TAREA TEXT NOT NULL,NOMBRE TEXT);";
    String consulta2="CREATE TABLE USUARIOS (NOMBRE TEXT PRIMARY KEY NOT NULL, CONTRASEÑA TEXT NOT NULL)";
    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.mistareas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(consulta);
        db.execSQL(consulta2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addTarea(String tarea, String user){
        ContentValues registro = new ContentValues();
        registro.put("TAREA", tarea);
        registro.put("NOMBRE",user);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("TAREAS", null, registro);
        db.close();
    }


    public String[] obtenerTareas(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM TAREAS WHERE NOMBRE=?",new String[]{user});
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

    public void borrarTarea(String tarea,String usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "TAREA=? AND NOMBRE=?",new String[]{tarea,usuario});
        db.close();
    }
    public void editarTarea(String tarea, String contenido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("TAREA", contenido);
        db.update("TAREAS",registro,"TAREA=?",new String[]{tarea});
        db.close();
    }
    // tabla usuarios
    public void addUser(String user, String pass){
        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", user);
        registro.put("CONTRASEÑA",pass);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USUARIOS", null, registro);
        db.close();
    }


    public boolean checkUserLogin(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM USUARIOS WHERE NOMBRE=? AND CONTRASEÑA=?",new String[]{String.valueOf(username),String.valueOf(password)});
        int regs = cursor.getCount();
        return regs <= 0;
    }
    public Boolean checkUserName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USUARIOS where NOMBRE = ?", new String[]{username});
        int num = cursor.getCount();
        return num > 0;
    }
    public void insertUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("NOMBRE", username);
        contentValues.put("CONTRASEÑA", password);
        db.insert("USUARIOS", null, contentValues);
        db.close();
    }
}
