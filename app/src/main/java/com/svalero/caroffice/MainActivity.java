package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.svalero.caroffice.adapter.CarAdapter;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Car;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static List<Car> carList = new ArrayList<>();
    private CarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.office_list);
        //esto le dice que tenga un tamaño fijo y que ocupe el máximo espacio asiganado
        recyclerView.setHasFixedSize(true);
        //Esto le dice que lo va a gestionar un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //así se ciñe al Layout manager
        recyclerView.setLayoutManager(layoutManager);
        //hago mi adapter propio no utilizo el arrayadapter de android
        adapter = new CarAdapter(this, carList);
        recyclerView.setAdapter(adapter);


       /* Button continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);*/
    }

    /*public void continueButton(View view) {
        //Como Selecciono la tarea
        Task selectedTask = adapter.getSelectedTask();
        Toast.makeText(this, selectedTask.getFullName(), Toast.LENGTH_SHORT).show());
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        carList.clear();
        carList.addAll(db.taskDao().getAll());
        // con esto la lista siempre estára actualizada cuando vuelva de un segundo plano.
        adapter.notifyDataSetChanged();
    }

    //Métodos para controlar el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedPosition = menuInfo.position;
        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        if (item.getItemId() == R.id.deleteItem){
            carList.remove(selectedPosition);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
            return true;
        }else if (item.getItemId() == R.id.doneItem){
            return true;
        }
        return super.onContextItemSelected(item);
    }

 /*   //Este método me permite seleccionar un item de la recycler view y mostrarlo

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //int viene como i pero lo cambiamos por position para recordar que es la posición que he pulsado
        Intent intent = new Intent(this, ViewTaskActivity.class);
        //le pasamos datos extra a mayores para que sepa de donde vengo y que debe mostar.
        intent.putExtra("position", position);
        startActivity((intent));

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registerTask) {
            //Con Intent de digo donde estoy y a donde quiero ir
            Intent intent = new Intent(this, RegisterCarActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }




}