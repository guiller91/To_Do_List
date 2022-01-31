package com.example.mistareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistareas.db.ControladorDB;

public class MainActivity extends AppCompatActivity {

    ControladorDB controladorDB;
    private ArrayAdapter<String> adapter;
    ListView listViewTareas;
    String usuario;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        controladorDB = new ControladorDB(this);
        actualizarUI();
    }
    //añadir menu a la view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.newTask:
                EditText cajaTexto = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva tarea")
                        .setMessage("Que quieres hacer a continuación?")
                        .setView(cajaTexto)
                        .setPositiveButton("Añadir",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Añadir datos a bbdd
                                String tarea = cajaTexto.getText().toString();
                                controladorDB.addTarea(tarea,usuario);
                                actualizarUI();
                            }
                        })
                        .setNegativeButton("Cancelar",null)
                        .create();
                dialog.show();
                break;
            case android.R.id.home:
                back();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editarTarea(View view){
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_tittle);
        String tareaEditable = tareaTextView.getText().toString();
        EditText cajaTexto = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Editar tarea")
                .setMessage("Que quieres hacer a continuación?")
                .setView(cajaTexto)
                .setPositiveButton("Editar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Añadir datos a bbdd
                        String tarea = cajaTexto.getText().toString();
                        controladorDB.editarTarea(tareaEditable,tarea);
                        actualizarUI();
                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();

    }

    private void back() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void actualizarUI(){
        try {
            //SI EL NUMERO DE REGISTROS DE LA BBDD ES 0
            if (controladorDB.numeroRegistros() == 0) {
                listViewTareas.setAdapter(null);
            } else {
                adapter = new ArrayAdapter<>(this,R.layout.item_tarea,R.id.task_tittle,controladorDB.obtenerTareas(usuario));
                listViewTareas.setAdapter(adapter);
            }
        } catch (NullPointerException e) {

            listViewTareas.setAdapter(null);
        }
    }

    public void borrarTarea(View view){
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_tittle);
        String tarea = tareaTextView.getText().toString();
        controladorDB.borrarTarea(tarea);
        actualizarUI();
    }

    public void init(){

        bundle = this.getIntent().getExtras();
        if(bundle !=null){
            usuario = bundle.getString("usuario");
        }
           listViewTareas =(ListView) findViewById(R.id.listaTareas);
    }



}